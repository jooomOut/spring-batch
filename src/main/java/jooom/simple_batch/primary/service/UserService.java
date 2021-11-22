package jooom.simple_batch.primary.service;

import jooom.simple_batch.primary.model.User;
import jooom.simple_batch.primary.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }
}
