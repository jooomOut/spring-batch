package jooom.simple_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

/**
 * StepNextConfitionalJob 에서의 문제
 * 1. Step이 맡는 역할이 많아짐
 * 2. 다양한 분기 처리가 어려워짐
 * 을 해결하기 위한 Step의 Flow 분기법 샘플 코드
 * Step과 Decider는 역할과 책임이 분리되어야 함!
 * */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class DeciderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(startStep())
                .next(decider()) // decider를 먼저 실행해봅니다.
                .from(decider()) // 실행한 뒤 decider의 상태가
                    .on("ODD") // ODD라면
                    .to(oddStep()) // oddStep로 간다.
                .from(decider()) // decider의 상태가
                    .on("EVEN") // ODD라면
                    .to(evenStep()) // evenStep로 간다.
                .end() // builder 종료
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> Start!");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step evenStep() {
        return stepBuilderFactory.get("evenStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> 짝수입니다.");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step oddStep() {
        return stepBuilderFactory.get("oddStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>> 홀수입니다.");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    @Bean
    public JobExecutionDecider decider() {
        return new OddDecider();
    }

    /*
    * 분기 처리를 위한 로직을 담는 Decider
    * */
    public static class OddDecider implements JobExecutionDecider {

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            Random rand = new Random();

            int num = rand.nextInt(50) + 1;
            log.info("랜덤한 숫자는 : {}", num);

            if (num % 2 == 0){
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
}
