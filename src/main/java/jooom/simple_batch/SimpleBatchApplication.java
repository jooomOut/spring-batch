package jooom.simple_batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
public class SimpleBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBatchApplication.class, args);
    }

}
