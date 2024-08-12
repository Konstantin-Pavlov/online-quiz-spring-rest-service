package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizRateDto;
import kg.attractor.online_quiz_platform.dto.QuizResultDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody @Valid QuizDto quiz) {
        quizService.saveQuiz(quiz);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Quiz %s added successfully", quiz.getTitle()));
    }

    @GetMapping
    public ResponseEntity<List<QuizDto>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizDto> getQuizById(@PathVariable long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping("{quizId}/solve")
    public ResponseEntity<String> solveQuiz(@RequestBody @Valid QuizAnswerDto quizAnswerDto, @PathVariable long quizId) {
        quizAnswerDto.setQuizId(quizId);
        quizService.saveQuizAnswer(quizAnswerDto);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("answer for quiz with id %d added successfully", quizAnswerDto.getQuizId()));
    }

    @GetMapping("{quizId}/results")
    public ResponseEntity<List<QuizResultDto>> getQuizResults(@PathVariable long quizId) {
        return ResponseEntity.ok(quizService.getQuizResults(quizId));
    }

    @GetMapping("rates")
    public ResponseEntity<List<QuizRateDto>> getQuizzesRates() {
        return ResponseEntity.ok(quizService.getQuizzesRates());
    }

    @PostMapping("{quizId}/rate")
    public ResponseEntity<String> rateQuiz(@RequestBody @Valid QuizRateDto quizRateDto, @PathVariable long quizId) {
        quizRateDto.setQuizId(quizId);
        quizService.rateQuiz(quizRateDto);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("quiz with id %d rated successfully", quizRateDto.getQuizId()));
    }

}
