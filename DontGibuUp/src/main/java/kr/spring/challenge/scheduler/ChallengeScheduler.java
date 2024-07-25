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

    //매일 자정에 챌린지 상태를 업데이트하는 스케줄러
    @Scheduled(cron = "0 0 0 * * *")
    public void updateChallengeStatus() {
        logger.info("챌린지 상태 업데이트 스케줄러 실행");
        //challengeService.checkAndProcessExpiredChallenges();
    }
}