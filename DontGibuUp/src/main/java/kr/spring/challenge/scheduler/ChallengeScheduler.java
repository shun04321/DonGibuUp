package kr.spring.challenge.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import kr.spring.challenge.service.ChallengeService;

@Component
public class ChallengeScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ChallengeScheduler.class);

    @Autowired
    private ChallengeService challengeService;

    //매일 23:59:59에 당일 종료된 챌린지를 처리하는 스케줄러
    @Scheduled(cron = "59 59 23 * * *")
    public void processTodayExpiredChallenges() {
        try {
            challengeService.processTodayExpiredChallenges();
            logger.info("오늘 종료된 챌린지를 성공적으로 처리했습니다.");
        } catch (Exception e) {
            logger.error("오늘 종료된 챌린지 처리 중 오류 발생: {}", e.getMessage(), e);
        }
    }
}