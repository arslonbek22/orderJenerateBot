package uz.pdp.order_generate_bot.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.KickChatMember;
import com.pengrad.telegrambot.request.SendMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.pdp.order_generate_bot.bot.entity.TgUser;
import uz.pdp.order_generate_bot.bot.entity.Total;
import uz.pdp.order_generate_bot.bot.entity.enums.UserState;
import uz.pdp.order_generate_bot.bot.repo.TotalRepo;

import java.util.UUID;




@Service
@RequiredArgsConstructor
public class Handle {

    private final ButtonService buttonService;
    private final TgUserService tgUserService;
    private final TotalRepo totalRepo;

    @Async
    public void updateHandle(Update update) {
        if (update.message() != null) {
            System.out.println(tgUserService.loadUser(update.message()));
            Message message = update.message();

            String fullName = message.from().firstName() + " " + message.chat().lastName();

            TgUser tgUser = tgUserService.loadUser(message);

            if (message.text() != null) {
                String text = message.text();
                if (text.equals("/start")){
                   buttonService.sendContact(tgUser, fullName);
                }


            }else if (message.contact() != null){
                buttonService.akceptContuctSendLocation(tgUser, message);


            }else if(message.photo()!=null){



            }else if (message.location()!=null){
                buttonService.akceptLocationSendAllCategory(tgUser, message);
            }
        }else if (update.callbackQuery()!=null){

            TgUser tgUser = tgUserService.loadUserByCollback(update.callbackQuery().from().id());

            if (tgUser.getState().getUserState().equals(UserState.SEND_CATEGORY)){

                buttonService.sendAllProduct(tgUser, UUID.fromString(update.callbackQuery().data()));

            }else if (tgUser.getState().getUserState().equals(UserState.SEND_PRODUCT)){

                Total total = totalRepo.findByProductId(UUID.fromString(update.callbackQuery().data()));
                if (total == null){
                    total = new Total(1, 0, UUID.fromString(update.callbackQuery().data()));
                }

                buttonService.sendProductById(tgUser, update.callbackQuery().data(), total);
            }else if (tgUser.getState().getUserState().equals(UserState.SEND_BASKED)){

            }
            System.out.println("CallbackQuery: " + update.callbackQuery().from().id());
        }

    }
}
