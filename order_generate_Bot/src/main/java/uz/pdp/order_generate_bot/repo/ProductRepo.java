package uz.pdp.order_generate_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductRepo extends JpaRepository<Product, UUID> {

    List<Product> findAllByCategoryId(UUID categoryId);
}
