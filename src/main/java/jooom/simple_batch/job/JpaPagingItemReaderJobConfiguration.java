package jooom.simple_batch.job;

import jooom.simple_batch.User.Item;
import jooom.simple_batch.User.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import javax.persistence.EntityManagerFactory;

/**
 * Step에서 Read, Write를 사용하고
 * Database Cursor 기반의 Reader를 사용하는 예제
 * */

@Slf4j
@RequiredArgsConstructor
@Configuration
public class JpaPagingItemReaderJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    private static final int CHUNK_SIZE = 10;

    @Bean
    public Job jpaPagingItemReaderJob() {
        return jobBuilderFactory.get("jpaPagingItemReaderJob")
                .start(jpaPagingItemReaderStep())
                .incrementer(new RunIdIncrementer()) // 파라미터 변경 없이 재실행할 수 있는 옵션
                .build();
    }

    @Bean
    @JobScope
    public Step jpaPagingItemReaderStep() {
        return stepBuilderFactory.get("jpaPagingItemReaderStep")
                .<User, User>chunk(CHUNK_SIZE) // <Reader의 반환 타입, Writer의 파라미터 타입>
                .reader(jpaPagingItemReader())
                .processor(jpaItemProcessor())
                .writer(jpaPagingItemWriter())
                .build();
    }

    @Bean
    public JpaPagingItemReader<User> jpaPagingItemReader() {
        return new JpaPagingItemReaderBuilder<User>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(CHUNK_SIZE)
                .queryString("SELECT u FROM User u")
                .build();
    }

    @Bean
    public ItemProcessor<User, User> jpaItemProcessor() {
        return user -> {
            user.setCount(user.getCount() == null ? 1 : user.getCount() + 1);
            /*user.addItem(Item.builder()
                            .name(user.getUsername() + "_iphone")
                    .build());
            user.addItem(Item.builder()
                    .name(user.getUsername() + "_ipad")
                    .build());*/
            log.info("Processed User item: {}", user.getItems().toString());
            return user;
        };
    }

    @Bean
    public JpaItemWriter<User> jpaPagingItemWriter() {
        JpaItemWriter<User> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
