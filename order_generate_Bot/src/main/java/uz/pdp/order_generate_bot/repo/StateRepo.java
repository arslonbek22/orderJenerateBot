package uz.pdp.order_generate_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.bot.entity.State;

import java.util.UUID;

public interface StateRepo extends JpaRepository<State, UUID> {
}
