package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizFullDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

//    @GetMapping
//    public ResponseEntity<List<QuizDto>> getQuizzes() {
//        return ResponseEntity.ok(quizService.getQuizzes());
//    }

    @GetMapping
    public ResponseEntity<List<QuizFullDto>> getQuizzes() {
        return ResponseEntity.ok(quizService.getFullQuizzes());
    }

    @PostMapping("add")
    public ResponseEntity<String> createQuiz(@RequestBody @Valid QuizDto quiz) {
        quizService.createQuiz(quiz);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Quiz %s added successfully", quiz.getTitle()));
    }

    @PostMapping("add-full")
    public ResponseEntity<String> createFullQuiz(@RequestBody @Valid QuizFullDto quiz) {
        quizService.createQuizFull(quiz);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Quiz %s added successfully", quiz.getTitle()));
    }
}
