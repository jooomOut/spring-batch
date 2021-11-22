package jooom.simple_batch.primary.User;

import jooom.simple_batch.primary.model.User;
import jooom.simple_batch.primary.repository.UserRepository;
import jooom.simple_batch.primary.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("유저를 추가하는 테스트 - 성공")
    void insertUser() {
        User userForAdd = User.builder()
                .username("백동진")
                .email("dj@gmail.com")
                .major("CS")
                .build();
        when(userRepository.save(userForAdd)).thenReturn(userForAdd);

        User result = userService.addUser(userForAdd);

        assertThat(result).isEqualTo(userForAdd);
    }
}