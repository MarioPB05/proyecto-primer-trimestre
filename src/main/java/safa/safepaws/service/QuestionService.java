package safa.safepaws.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import safa.safepaws.dto.question.QuestionCreateRequest;
import safa.safepaws.dto.question.QuestionResponse;
import safa.safepaws.mapper.QuestionMapper;
import safa.safepaws.model.Question;
import safa.safepaws.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper = QuestionMapper.INSTANCE;

    public Question createQuestion(QuestionCreateRequest questionCreateRequest) {
        Question question = questionMapper.toEntity(questionCreateRequest);
        return questionRepository.save(question);
    }

    public void markQuestionAsDeleted(Integer questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        question.setDeleted(true);
        questionRepository.save(question);
    }

    public List<QuestionResponse> findUndeletedQuestion() {
        List<Question> questions = questionRepository.findUndeletedQuestion();
        List<QuestionResponse> questionResponses = new ArrayList<>();
        for (Question question : questions) {
            QuestionResponse questionResponse = new QuestionResponse();
        }
        return questionResponses;

    }

}