package uz.pdp.order_generate_bot;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@EnableAsync
@SpringBootApplication
public class OrderGenerateBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderGenerateBotApplication.class, args);
    }


    @Bean
    public TelegramBot telegramBot() {
        return new TelegramBot("7366894207:AAE3zEHMJCP2Af7HmyM7riq_jPd9DlG7Ovg");
    }

}
