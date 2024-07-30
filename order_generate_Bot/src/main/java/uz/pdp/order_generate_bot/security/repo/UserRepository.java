package uz.pdp.order_generate_bot.security.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.projection.UserProjection;
import uz.pdp.order_generate_bot.security.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

     Optional<User> findByUsername(String username);

    /* List<UserProjection> findAllByIdNot(Integer id);*/


}