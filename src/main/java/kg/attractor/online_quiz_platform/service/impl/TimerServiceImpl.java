package kg.attractor.online_quiz_platform.service.impl;

import kg.attractor.online_quiz_platform.dto.MiniQuizDto;
import kg.attractor.online_quiz_platform.service.TimerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TimerServiceImpl implements TimerService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ConcurrentHashMap<Long, ScheduledFuture<?>> quizTimers = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Long> quizStartTimes = new ConcurrentHashMap<>();

    @Override
    public void startTimer(long durationSeconds, MiniQuizDto miniQuizDto) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + durationSeconds * 1000;
        log.info("Starting timer for {} seconds for quiz {}", durationSeconds, miniQuizDto.getTitle());

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            long currentTime = System.currentTimeMillis();
            long remainingTime = (endTime - currentTime) / 1000;

            if (remainingTime <= 0) {
                log.error(String.format(TIME_UP_MESSAGE, miniQuizDto.getTitle()));
                stopTimer(miniQuizDto);
            } else if (remainingTime <= 10) {
                log.warn(String.format(LAST_SECONDS_WARNING_MESSAGE, remainingTime, miniQuizDto.getTitle()));
            } else if (remainingTime % 5 == 0) {
                log.info(String.format(REMAINING_TIME_MESSAGE, remainingTime, miniQuizDto.getTitle()));
            }
        }, 0, 1, TimeUnit.SECONDS);

        quizTimers.put(miniQuizDto.getId(), future);
        quizStartTimes.put(miniQuizDto.getId(), startTime);
    }

    @Override
    public void stopTimer(MiniQuizDto miniQuizDto) {
        ScheduledFuture<?> future = quizTimers.remove(miniQuizDto.getId());
        if (future != null) {
            future.cancel(true);
            log.info("Timer stopped for {}", miniQuizDto.getTitle());
        }
        quizStartTimes.remove(miniQuizDto.getId());
    }

    @Override
    public boolean isSubmissionInTime(long quizId, long timeLimitSeconds) {
        Long startTime = quizStartTimes.get(quizId);
        if (startTime == null) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        long elapsedTime = (currentTime - startTime) / 1000;
        return elapsedTime <= timeLimitSeconds;
    }
}