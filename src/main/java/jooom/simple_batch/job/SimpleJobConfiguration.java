package jooom.simple_batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SimpleJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job moveUserData() {
        return jobBuilderFactory.get("moveUserData")
                .start(getData())
                .build();

    }

    @Bean
    public Step getData(){
        return stepBuilderFactory.get("getData")
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>> START getData");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

}
