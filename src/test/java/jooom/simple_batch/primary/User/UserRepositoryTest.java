package jooom.simple_batch.primary.User;

import jooom.simple_batch.primary.model.User;
import jooom.simple_batch.primary.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // 테스트 시 내장DB로 바꾸는 설정 해제
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    User userForAdd = User.builder()
            .username("백동진")
            .email("dj@gmail.com")
            .major("CS")
            .build();

    @Test
    @DisplayName("사용자 추가")
    void addUser(){
        User result = userRepository.save(userForAdd);
        assertThat(result)
                .isEqualTo(userForAdd);

    }

}