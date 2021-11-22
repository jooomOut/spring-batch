package jooom.simple_batch.job;

import jooom.simple_batch.TestBatchConfig;
import jooom.simple_batch.User.User;
import jooom.simple_batch.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.JobRepositoryTestUtils;

import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBatchTest
@SpringBootTest(classes={JpaPagingItemReaderJobConfiguration.class, TestBatchConfig.class})
class JpaPagingItemReaderJobConfigurationTest {

    @Autowired // 배치 테스트에 필요한 유틸리티 기능
    private JobLauncherTestUtils jobLauncherTestUtils; // (2)
    @Autowired // 배치 메타 데이터 테이블 관리 기능
    private JobRepositoryTestUtils jobRepositoryTestUtils;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void readUsers() throws Exception {
        //given

        List<User> userList = userRepository.findAll();
        //when

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("createTime", System.currentTimeMillis())
                .toJobParameters();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters); // (3)

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        List<User> afterUserList = userRepository.findAll();

        for (int i = 0 ; i < userList.size() ; i ++){
            assertThat(userList.get(i).getCount()).isEqualTo(afterUserList.get(i).getCount()-1);
        }
    }

}