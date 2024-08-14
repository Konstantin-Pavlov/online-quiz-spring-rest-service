package kg.attractor.online_quiz_platform.controller;

import jakarta.validation.Valid;
import kg.attractor.online_quiz_platform.dto.MicroQuizDto;
import kg.attractor.online_quiz_platform.dto.MiniQuizDto;
import kg.attractor.online_quiz_platform.dto.QuizAnswerDto;
import kg.attractor.online_quiz_platform.dto.QuizDto;
import kg.attractor.online_quiz_platform.dto.QuizRateDto;
import kg.attractor.online_quiz_platform.dto.QuizResultDto;
import kg.attractor.online_quiz_platform.service.QuizService;
import kg.attractor.online_quiz_platform.service.TimerService;
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
    private final TimerService timerService;
    private static final long TIME_LIMIT_SECONDS = 20; // Define the time limit in seconds (1 minute)

    @PostMapping
    public ResponseEntity<String> createQuiz(@RequestBody @Valid QuizDto quiz) {
        quizService.saveQuiz(quiz);
        return ResponseEntity.status(HttpStatus.OK).body(String.format("Quiz %s added successfully", quiz.getTitle()));
    }

    @GetMapping
    public ResponseEntity<List<MicroQuizDto>> getQuizzes() {
        return ResponseEntity.ok(quizService.getQuizzes());
    }

    @GetMapping("{quizId}")
    public ResponseEntity<MiniQuizDto> getQuizById(@PathVariable long quizId) {
        MiniQuizDto miniQuizDto = quizService.getMiniQuizById(quizId);
        timerService.startTimer(TIME_LIMIT_SECONDS, miniQuizDto); // Start the timer
        return ResponseEntity.ok(miniQuizDto);
    }

    @PostMapping("{quizId}/solve")
    public ResponseEntity<String> solveQuiz(@RequestBody @Valid QuizAnswerDto quizAnswerDto, @PathVariable long quizId) {
        quizAnswerDto.setQuizId(quizId);
        if (timerService.isSubmissionInTime(quizId, TIME_LIMIT_SECONDS)) {
            quizService.saveQuizAnswer(quizAnswerDto);
            MiniQuizDto miniQuizDto = quizService.getMiniQuizById(quizId); // Fetch the MiniQuizDto to stop the timer
            timerService.stopTimer(miniQuizDto); // Stop the timer
            return ResponseEntity.status(HttpStatus.OK).body(String.format("Answer for quiz with id %d added successfully", quizAnswerDto.getQuizId()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Time is up! Cannot submit the answer.");
        }
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

    @GetMapping("{quizId}/leaderboard")
    public ResponseEntity<List<QuizResultDto>> getQuizzesLeaderboard(@PathVariable long quizId) {
        return ResponseEntity.ok(quizService.getSortedQuizResults(quizId));
    }
}
