package uz.pdp.order_generate_bot.security.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.security.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}