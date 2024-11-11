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

    public QuestionResponse findUndeletedQuestion() {
        Question question = questionRepository.findUndeletedQuestion()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No undeleted question found"));
        return new QuestionResponse(
                question.getQuestion(),
                question.getType(),
                question.getRequired(),
                question.getRequiredQuestion() == null ? null : Integer.valueOf(question.getRequiredQuestion().getId())
        );
    }

}