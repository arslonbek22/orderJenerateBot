package uz.pdp.order_generate_bot.bot.service;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService implements CommandLineRunner {

    private final TelegramBot telegramBot;
    private final Handle handle;

    @Override
    public void run(String... args) throws Exception {
        telegramBot.setUpdatesListener(updates->{
            for (Update update : updates) {

                handle.updateHandle(update);

            }
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        });
    }


}
