package uz.pdp.order_generate_bot.bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.bot.entity.TgUser;

import java.util.UUID;

public interface TgUserRepo extends JpaRepository<TgUser, UUID> {

    TgUser findByChatId(Long chatId);
}
