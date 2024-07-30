package uz.pdp.order_generate_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.entity.Category;

import java.util.UUID;

public interface CategoryRepo extends JpaRepository<Category, UUID> {
}
