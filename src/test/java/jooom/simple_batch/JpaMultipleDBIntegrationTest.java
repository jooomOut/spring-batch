package jooom.simple_batch;

import jooom.simple_batch.primary.model.User;
import jooom.simple_batch.primary.repository.UserRepository;
import jooom.simple_batch.secondary.model.UserHistory;
import jooom.simple_batch.secondary.repository.UserHistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EnableTransactionManagement
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    @Transactional("userTransactionManager")
    public void whenCreatingUser_thenCreated() {
        User user = User.builder()
                .username("LMU")
                .count(3)
                .email("dark@gmail.com")
                .build();
        user = userRepository.save(user);

        assertThat(userRepository.findByEmail(user.getEmail()).isPresent()).isTrue();
    }

    @Test
    @Transactional("productTransactionManager")
    public void whenCreatingProduct_thenCreated() {
        UserHistory history = UserHistory.builder()
                        .itemCount(1)
                        .major("1CS")
                        .username("LMU")
                        .build();
        history = userHistoryRepository.save(history);
        assertThat(userHistoryRepository.findById(history.getId())).isNotNull();
    }
}