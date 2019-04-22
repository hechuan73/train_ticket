package user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import user.entity.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String userName);
}
