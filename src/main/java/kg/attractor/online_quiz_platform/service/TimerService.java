package kg.attractor.online_quiz_platform.service;

import kg.attractor.online_quiz_platform.Util.ConsoleColors;
import kg.attractor.online_quiz_platform.dto.MiniQuizDto;

public interface TimerService {
    String TIME_UP_MESSAGE = ConsoleColors.ANSI_RED_BACKGROUND + "Time is up! Quiz <%s> not solved" + ConsoleColors.RESET;
    String REMAINING_TIME_MESSAGE = ConsoleColors.BLUE_BOLD + "Remaining time: %d seconds for solving quiz <%s>" + ConsoleColors.RESET;
    String LAST_SECONDS_WARNING_MESSAGE = ConsoleColors.YELLOW_BACKGROUND + "Only %d seconds remaining for solving quiz <%s>!" + ConsoleColors.RESET;


    void startTimer(long durationSeconds, MiniQuizDto miniQuizDto);

    void stopTimer(MiniQuizDto miniQuizDto);

    boolean isSubmissionInTime(long quizId, long timeLimitSeconds);
}
