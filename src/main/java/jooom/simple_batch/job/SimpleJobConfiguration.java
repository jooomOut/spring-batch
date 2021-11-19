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

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job moveUserData() {
        return jobBuilderFactory.get("moveUserData")
                .start(getData(null))
                .next(modifyData(null))
                .build();

    }

    @Bean
    @JobScope
    public Step getData(@Value("#{jobParameters[requestDate]}") String requestData){
        return stepBuilderFactory.get("getData")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> START getData");
                    log.info(">>>> requestDate = {}", requestData);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
    @Bean
    @JobScope
    public Step modifyData(@Value("#{jobParameters[requestDate]}") String requestData){
        return stepBuilderFactory.get("modifyData")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> START modifyData");
                    log.info(">>>> requestDate = {}", requestData);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
