package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dao.QuestionDao;
import kg.attractor.online_quiz_platform.dao.QuestionFullDao;
import kg.attractor.online_quiz_platform.dto.QuestionDto;
import kg.attractor.online_quiz_platform.dto.QuestionFullDto;
import kg.attractor.online_quiz_platform.model.Question;
import kg.attractor.online_quiz_platform.model.QuestionFull;
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
    private final QuestionFullDao questionFullDao;
    private final OptionService optionService;

    @Override
    public List<QuestionDto> getQuestions() {
        List<Question> questions = questionDao.getQuestions();
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
    public List<QuestionFullDto> getFUllQuestionsByQuizId(long id) {
        List<QuestionFull> fullQuestions = questionFullDao.getFullQuestionsByQuizId(id);
        List<QuestionFullDto> dtos = new ArrayList<>();
        fullQuestions.forEach(questionFull ->
                dtos.add(QuestionFullDto.builder()
                        .id(questionFull.getId())
                        .quizId(questionFull.getQuizId())
                        .questionText(questionFull.getQuestionText())
                        .options(optionService.getOptionsByQuestionId(questionFull.getId()))
                        .build()
                )
        );
        return dtos;
    }

    @Override
    public void createQuestion(QuestionDto question) {
        questionDao.createQuestion(question);
        log.info("added question with quiz id " + question.getQuizId());
    }
}
