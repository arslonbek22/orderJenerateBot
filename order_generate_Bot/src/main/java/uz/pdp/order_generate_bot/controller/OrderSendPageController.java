package uz.pdp.order_generate_bot.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderSendPageController {


    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping
    public String orderSendPage() {
        messagingTemplate.convertAndSend("/topic/order-generate", "Nima diysan san ");
        return "Vazifani qilmasang ulasan Arslon ";
    }
}
