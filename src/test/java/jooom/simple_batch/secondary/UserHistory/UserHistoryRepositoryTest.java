package jooom.simple_batch.secondary.UserHistory;

import jooom.simple_batch.secondary.model.UserHistory;
import jooom.simple_batch.secondary.repository.UserHistoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트 시 내장DB로 바꾸는 설정 해제
public class UserHistoryRepositoryTest {
    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    void test(){
        UserHistory history = UserHistory.builder()
                .username("테스트")
                .email("test@email.com")
                .major("kn")
                .itemCount(2)
                .build();

        UserHistory result = userHistoryRepository.save(history);

        assertThat(result).isEqualTo(history);
    }
}
