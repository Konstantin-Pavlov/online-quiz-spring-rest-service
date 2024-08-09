package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.ResultDto;
import kg.attractor.online_quiz_platform.model.QuizAnswer;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping
    public ResponseEntity<List<QuizDto>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @GetMapping("{quizId}/results")
    public ResponseEntity<ResultDto> getQuizResults(@RequestParam long userId, @PathVariable long quizId) {
        return ResponseEntity.ok(quizService.getQuizResults(userId, quizId));
    }

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody @Valid QuizDto quiz) {
        quizService.saveQuiz(quiz);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Quiz %s added successfully", quiz.getTitle()));
    }

    @PostMapping("{quizId}/solve")
    public ResponseEntity<String> solveQuiz(@RequestBody @Valid QuizAnswerDto quizAnswerDto, @PathVariable long quizId) {
        quizAnswerDto.setQuizId(quizId);
        quizService.saveQuizAnswer(quizAnswerDto);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("answer for quiz with id %d added successfully", quizAnswerDto.getQuizId()));
    }
}
