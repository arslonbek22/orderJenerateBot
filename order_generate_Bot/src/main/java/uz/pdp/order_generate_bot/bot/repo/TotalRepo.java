package uz.pdp.order_generate_bot.bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.bot.entity.Total;

import java.util.UUID;

public interface TotalRepo extends JpaRepository<Total, Integer> {

    Total findByProductId(UUID productId);
}
