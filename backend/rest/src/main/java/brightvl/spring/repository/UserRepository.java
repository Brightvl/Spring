package brightvl.spring.repository;


import brightvl.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  // для поиска юзера в базе
  Optional<User> findByLogin(String login);

}
