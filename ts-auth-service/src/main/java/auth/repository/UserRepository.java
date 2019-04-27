package auth.repository;

import auth.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    void deleteByUserId(UUID userId);
}
