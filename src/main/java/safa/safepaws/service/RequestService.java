package safa.safepaws.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import safa.safepaws.dto.request.GetAdoptionsResponse;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.requestAnswer.CreateRequestAnswerRequest;
import safa.safepaws.enums.RequestStatus;
import safa.safepaws.mapper.RequestMapper;
import safa.safepaws.model.Post;
import safa.safepaws.model.Request;
import safa.safepaws.model.RequestAnswer;
import safa.safepaws.model.User;
import safa.safepaws.repository.RequestAnswerRepository;
import safa.safepaws.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.List;
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

    public String delete (Integer id){
        Request request = requestRepository.findById(id).orElse(null);
        if (Objects.requireNonNull(request).getId().equals(authenticatedUser.getClient().getId())) {
            request.setDeleted(true);
        }
        return "Request eliminada";
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
    public Integer createRequest(RequestCreateDTO requestCreateDTO){
        Request request = new Request();
        Post post = postService.findPost(requestCreateDTO.getPostId());

        request.setMessage(requestCreateDTO.getMessage());
        request.setCreationDate(LocalDateTime.now());
        request.setStatus(RequestStatus.PENDING);
        request.setDeleted(false);
        request.setClient(authenticatedUser.getClient());
        request.setPost(post);

        request = requestRepository.save(request);

        for (CreateRequestAnswerRequest requestAnswer : requestCreateDTO.getAnswers()) {
            requestAnswerService.createRequestAnswer(requestAnswer, request);
        }

        return request.getId();
    }
}
