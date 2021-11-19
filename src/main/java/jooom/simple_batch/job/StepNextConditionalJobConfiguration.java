package jooom.simple_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Step 성공/실패 여부에 따라 다른 분기를 가지게 하는
 * 조건 별 Step
 * */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepNextConditionalJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepConditionalJob() {
        return jobBuilderFactory.get("stepConditionalJob")
                .start(stepCondition1())
                    .on("FAILED") // if step1 is failed
                    .to(stepCondition3()) // move to step 3
                    .on("*") // and in all conditions
                    .end()// flow end
                .from(stepCondition1())
                    .on("*")// except for failed (step1)
                    .to(stepCondition2()) // move to step2
                    .on("*") // and in all conditions
                    .end() // flow end
                .end()
                .build();

    }

    @Bean
    public Step stepCondition1(){
        return stepBuilderFactory.get("stepCondition1")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> stepCondition 1");
                    contribution.setExitStatus(ExitStatus.FAILED); // 실패 세팅!
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    public Step stepCondition2(){
        return stepBuilderFactory.get("stepCondition2")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> stepCondition 2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step stepCondition3(){
        return stepBuilderFactory.get("stepCondition3")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> stepCondition 3");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
