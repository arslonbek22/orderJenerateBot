package uz.pdp.order_generate_bot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.order_generate_bot.entity.Attachment;

public interface AttachmentRepo extends JpaRepository<Attachment, Integer> {
}
