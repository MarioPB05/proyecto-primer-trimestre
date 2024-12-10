package safa.safepaws.service;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.thymeleaf.context.Context;
import safa.safepaws.dto.post.CheckPostResponse;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.request.RequestStatusResponse;
import safa.safepaws.dto.requestAnswer.CreateRequestAnswerRequest;
import safa.safepaws.enums.RequestStatus;
import safa.safepaws.mapper.RequestMapper;
import safa.safepaws.model.*;
import safa.safepaws.repository.AdoptionContractRepository;
import safa.safepaws.repository.RequestAnswerRepository;
import safa.safepaws.repository.RequestRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RequestService {
    private RequestRepository requestRepository;
    private RequestMapper requestMapper;
    private final User authenticatedUser;
    private final RequestAnswerRepository requestAnswerRepository;
    private final PostService postService;
    private final RequestAnswerService requestAnswerService;
    private final PdfService pdfService;
    private final ChatRoomService chatRoomService;
    private final AdoptionContractRepository adoptionContractRepository;

    public String delete (Integer id){
        Request request = requestRepository.findById(id).orElse(null);
        if (Objects.requireNonNull(request).getId().equals(authenticatedUser.getClient().getId())) {
            request.setDeleted(true);
        }
        return "Request eliminada";
    }

    public Request findRequestByClientAndPost(Integer clientId, Integer postId){
        return requestRepository.findByClientIdAndPostIdAndDeletedIsFalse(clientId, postId).orElse(null);
    }

    public List<RequestAnswer> getAnswersForRequest(Integer requestId) {
        return requestAnswerRepository.findByRequestId(requestId);
    }

    public List<GetAdoptionsResponse> getSentAdoptionsResponses(){
        List<Request> requestsList = requestRepository.findAllByClientIdSent(authenticatedUser.getClient().getId());
        return requestMapper.toAdoptionsResponseDTO(requestsList);
    }

    public List<GetAdoptionsResponse> getReceivedAdoptionsResponses(){
        List<Request> requestsList = requestRepository.findAllByClientIdReceived(authenticatedUser.getClient().getId());
        return requestMapper.toAdoptionsResponseDTO(requestsList);
    }

    @Transactional
    public String createRequest(RequestCreateDTO requestCreateDTO){
        Request request = new Request();
        Post post = postService.findPost(requestCreateDTO.getPostId());

        request.setMessage(requestCreateDTO.getMessage());
        request.setCreationDate(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);
        request.setDeleted(false);
        request.setClient(authenticatedUser.getClient());
        request.setPost(post);
        request.setCode(NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, NanoIdUtils.DEFAULT_ALPHABET, 10).toUpperCase());

        request = requestRepository.save(request);

        for (CreateRequestAnswerRequest requestAnswer : requestCreateDTO.getAnswers()) {
            requestAnswerService.createRequestAnswer(requestAnswer, request);
        }

        return request.getCode();
    }

    public void generateRequestPdf(String requestCode, HttpServletResponse response) throws Exception {
        Request request = requestRepository.findByCode(requestCode).orElse(null);

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        Context context = new Context();

        LocalDate requestDate = request.getCreationDate().toLocalDate();
        String month = requestDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("es","ES"));
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        String formattedDate = month + " " + requestDate.getDayOfMonth() + ", " + requestDate.getYear();

        context.setVariable("date", formattedDate);

        List<RequestAnswer> answers = getAnswersForRequest(request.getId());
        context.setVariable("answers", answers);

        context.setVariable("request", request);
        context.setVariable("post", request.getPost());
        context.setVariable("client", request.getClient());

        byte[] pdfBytes = pdfService.generatePdf("adoption-request", context);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte.pdf");
        response.getOutputStream().write(pdfBytes);
    }

    public CheckPostResponse checkRequest(Integer postId) {
        Request request = this.findRequestByClientAndPost(authenticatedUser.getClient().getId(), postId);
        String code = null;

        if (request != null) {
            code = request.getCode();
        }

        return new CheckPostResponse(request == null, code);
    }

    public RequestStatusResponse getRequestStatus(String requestCode) {
        Request request = requestRepository.findByCode(requestCode).orElse(null);

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        return new RequestStatusResponse(request.getStatus().getId(), chatRoomService.getChatRoomCode(request.getPost().getId()));
    }

    private Request getRequestFromCode(String requestCode) {
        Request request = requestRepository.findByCode(requestCode).orElse(null);

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        if (!Objects.equals(authenticatedUser.getClient().getId(), request.getPost().getClient().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the post");
        }

        if (request.getStatus() != RequestStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request is not pending");
        }

        return request;
    }

    @Transactional
    public String acceptRequest(String requestCode) throws Exception {
        Request request = getRequestFromCode(requestCode);

        request.setStatus(RequestStatus.ACCEPTED);
        requestRepository.save(request);

        List<Request> requests = requestRepository.findAllByPostIdAndStatusNotIn(request.getPost().getId(), List.of(RequestStatus.ACCEPTED, RequestStatus.REJECTED));

        for (Request r : requests) {
            r.setStatus(RequestStatus.ADOPTED);
            requestRepository.save(r);
        }

        postService.generateAdoptionContract(request.getPost(), request);

        return chatRoomService.createRoom(request.getPost().getClient().getId(), request.getClient().getId(), request.getPost());
    }

    public String rejectRequest(String requestCode) {
        Request request = getRequestFromCode(requestCode);

        request.setStatus(RequestStatus.REJECTED);
        requestRepository.save(request);

        return "Request rejected";
    }

    public void downloadAdoptionContractPdf(String requestCode, HttpServletResponse response) throws Exception {
        Request request = requestRepository.findByCode(requestCode).orElse(null);

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        AdoptionContract adoptionContract = adoptionContractRepository.findByPostId(request.getPost().getId()).orElse(null);

        if (adoptionContract == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Adoption contract not found");
        }

        if (adoptionContract.getDocumentSha256() != null) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=contract-sha.pdf");
            response.getOutputStream().write(pdfService.decodeBase64ToPdf(adoptionContract.getDocument()));
            return;
        }

        byte[] pdfBytes = pdfService.generatePdf("adoption-contract", postService.getContext(request.getPost(), request));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=contract.pdf");
        response.getOutputStream().write(pdfBytes);
    }

    public Boolean signContract(String requestCode, String signature, Boolean isOwner) {
        Request request = requestRepository.findByCode(requestCode).orElse(null);

        if (request == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Request not found");
        }

        if (isOwner && !Objects.equals(request.getPost().getClient().getId(), authenticatedUser.getClient().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the owner of the post");
        }

        if (!isOwner && !Objects.equals(request.getClient().getId(), authenticatedUser.getClient().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the adopter of the post");
        }

        postService.saveSignature(request.getPost(), request, signature, isOwner);

        if (isOwner) {
            // El dueño es el primero que genera el contrato, y por ende el primero que firma
            request.setStatus(RequestStatus.PENDING_SIGNATURE);
        } else {
            // Cuando el adoptante firma el contrato, se cambia el estado de la solicitud a finalizado.
            request.setStatus(RequestStatus.FINISHED);
        }

        requestRepository.save(request);

        return true;
    }
}
