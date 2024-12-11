package safa.safepaws.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import safa.safepaws.dto.question.QuestionResponse;
import safa.safepaws.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/")
    public List<QuestionResponse> getQuestions(){
        return questionService.findUndeletedQuestion();
    }
}
