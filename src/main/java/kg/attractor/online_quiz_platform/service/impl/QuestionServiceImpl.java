package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuestionDao;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.service.OptionService;
import kg.attractor.online_quiz_platform.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final OptionService optionService;

    @Override
    public List<QuestionDto> getQuestions() {
        List<kg.attractor.online_quiz_platform.model.Question> questions = questionDao.getQuestions();
        List<QuestionDto> dtos = new ArrayList<>();
        questions.forEach(question ->
                dtos.add(QuestionDto.builder()
                        .id(question.getId())
                        .quizId(question.getQuizId())
                        .questionText(question.getQuestionText())
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public List<QuestionDto> getQuestionsByQuizId(long id) {
        List<Question> fullQuestions = questionDao.getQuestionsByQuizId(id);
        List<QuestionDto> dtos = new ArrayList<>();
        fullQuestions.forEach(questionWithOptions ->
                dtos.add(QuestionDto.builder()
                        .id(questionWithOptions.getId())
                        .quizId(questionWithOptions.getQuizId())
                        .questionText(questionWithOptions.getQuestionText())
                        .options(optionService.getOptionsByQuestionId(questionWithOptions.getId()))
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public void createQuestion(QuestionDto questionDto) {
        questionDao.createQuestion(questionDto);
        log.info("added question with quiz id {}", questionDto.getQuizId());
    }
}
