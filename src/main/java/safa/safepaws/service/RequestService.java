package safa.safepaws.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.request.RequestCreateDTO;
import safa.safepaws.dto.request.RequestEditDTO;
import safa.safepaws.mapper.RequestMapper;
import safa.safepaws.model.Request;
import safa.safepaws.model.RequestAnswer;
import safa.safepaws.model.User;
import safa.safepaws.repository.RequestAnswerRepository;
import safa.safepaws.repository.RequestRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {


    private RequestRepository requestRepository;
    private RequestMapper  requestMapper;
    private  User authenticatedUser;
    private final RequestAnswerRepository requestAnswerRepository;


    /**
     * Safe a new request
     *
     * @param requestdto
     * @return
     */
    public Request guardar(RequestCreateDTO requestdto) {
        return requestRepository.save(requestMapper.toEntity(requestdto));
    }

    /**
     * Edit a request
     *
     * @param requestdto
     * @return
     */
    public Request modificar(RequestEditDTO requestdto){
        if (requestdto.getId().equals(authenticatedUser.getClient().getId())) {
            Request request = requestMapper.toEntity(requestdto);
            return requestRepository.save(request);
        }else{
            throw new RuntimeException("El usuario no tiene permisos para editar");
        }
    }

    /**
     * Delete a request
     *
     * @param id
     * @return
     */
    public String eliminar (Integer id){
        Request request = requestRepository.findById(id).orElse(null);
        if (Objects.requireNonNull(request).getId().equals(authenticatedUser.getClient().getId())) {
            request.setDeleted(true);
        }
        return "Request eliminada";
    }


    public List<RequestAnswer> getAnswersForRequest(Integer requestId) {
        return requestAnswerRepository.findByRequestId(requestId);
    }


    public List<Request> getAdoption(Integer id){
        Request request = requestRepository.findById(id).orElse(null);
        if (Objects.requireNonNull(request).getClient().getId().equals(authenticatedUser.getClient().getId())) {
               return requestRepository.findAll();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    public Optional<Request> getAdoptionPorOwner(Integer id){
        Request request = requestRepository.findById(id).orElse(null);
        if (Objects.requireNonNull(request).getClient().getId().equals(authenticatedUser.getClient().getId())) {
            return requestRepository.findAllById(id);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }








}
