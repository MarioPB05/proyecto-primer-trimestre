package safa.safepaws.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.requestAnswer.CreateRequestAnswerRequest;
import safa.safepaws.dto.requestAnswer.GetAnswersResponse;
import safa.safepaws.mapper.RequestAnswerMapper;
import safa.safepaws.model.Question;
import safa.safepaws.model.Request;
import safa.safepaws.model.RequestAnswer;
import safa.safepaws.repository.QuestionRepository;
import safa.safepaws.repository.RequestAnswerRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RequestAnswerService {
    private final QuestionRepository questionRepository;
    private final RequestAnswerRepository requestAnswerRepository;
    private RequestAnswerMapper requestAnswerMapper;

    /**
     * Create a new request answer
     *
     * @param createRequestAnswerRequestDTO CreateRequestAnswer
     * @param request Request
     * @return RequestAnswer
     */
    @Transactional
    public RequestAnswer createRequestAnswer(CreateRequestAnswerRequest createRequestAnswerRequestDTO, Request request){
        RequestAnswer requestAnswer = new RequestAnswer();
        Question question = questionRepository.findById(createRequestAnswerRequestDTO.getQuestionId()).orElse(null);

        if (question == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        requestAnswer.setAnswer(createRequestAnswerRequestDTO.getAnswer());
        requestAnswer.setRequest(request);
        requestAnswer.setQuestion(question);

        requestAnswerRepository.save(requestAnswer);

        return requestAnswer;
    }

    public List<GetAnswersResponse> getRequestAnswers(Integer requestId){
        List<RequestAnswer> requestAnswers = requestAnswerRepository.findAllByRequestId(requestId);

        return requestAnswerMapper.toDTO(requestAnswers);
    }
}
