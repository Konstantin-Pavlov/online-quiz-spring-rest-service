package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.Question;
import kg.attractor.online_quiz_platform.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Question>> getQuestions() {
        return ResponseEntity.ok(questionService.getQuestions());
    }

    @PostMapping("add")
    public ResponseEntity<String> createQuestion(@RequestBody @Valid Question question) {
        questionService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Question '%s' added successfully", question.getQuestionText()));
    }

}
