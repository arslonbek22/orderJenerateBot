package uz.pdp.order_generate_bot.bot.service;


import com.pengrad.telegrambot.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.order_generate_bot.bot.entity.State;
import uz.pdp.order_generate_bot.bot.entity.TgUser;
import uz.pdp.order_generate_bot.bot.entity.enums.UserState;
import uz.pdp.order_generate_bot.bot.repo.TgUserRepo;


@Service
@RequiredArgsConstructor
public class TgUserService {

    private final TgUserRepo tgUserRepo;

    public TgUser loadUser(Message message) {

        TgUser byChatId = tgUserRepo.findByChatId(message.from().id());
        if (byChatId == null) {
            TgUser tgUser = TgUser.builder()
                    .chatId(message.from().id())
                    .state(State.builder().userState(UserState.SEND_START).build())
                    .firstName(message.chat().firstName())
                    .lastName(message.chat().lastName())
                    .username(message.chat().username())
                    .lat(1).lng(2).build();
            return tgUserRepo.save(tgUser);
        }

        return byChatId;


    }

    public TgUser loadUserByCollback(Long id) {
        return tgUserRepo.findByChatId(id);
    }
}
