package jooom.simple_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class StepJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job stepTestJob() {
        return jobBuilderFactory.get("moveUserData")
                .start(step1())
                .next(step2())
                .build();

    }

    @Bean
    @JobScope
    public Step step1(){
        return stepBuilderFactory.get("getData")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> STEP 1");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    @JobScope
    public Step step2(){
        return stepBuilderFactory.get("modifyData")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> STEP 2");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
