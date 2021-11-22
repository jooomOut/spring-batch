package jooom.simple_batch.job;

import jooom.simple_batch.job.JpaPagingItemReaderJobConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobScheduler {

    private final JpaPagingItemReaderJobConfiguration jpaPagingItemReaderJobConfiguration;
    private final JobLauncher jobLauncher;

    @Scheduled( cron="*/30 * * * * *" ) // 30초 마다 실행됩니다.
    public void runJob() {
        log.info("RUN Scheduler");

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("createTime", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(jpaPagingItemReaderJobConfiguration.jpaPagingItemReaderJob(), jobParameters);

        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}
