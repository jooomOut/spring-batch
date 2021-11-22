package jooom.simple_batch.secondary.repository;

import jooom.simple_batch.secondary.model.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
}
