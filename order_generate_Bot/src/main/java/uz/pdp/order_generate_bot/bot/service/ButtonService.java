package uz.pdp.order_generate_bot.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.*;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.order_generate_bot.bot.entity.State;
import uz.pdp.order_generate_bot.bot.entity.TgUser;
import uz.pdp.order_generate_bot.bot.entity.Total;
import uz.pdp.order_generate_bot.bot.entity.enums.UserState;
import uz.pdp.order_generate_bot.bot.repo.TgUserRepo;
import uz.pdp.order_generate_bot.entity.Category;
import uz.pdp.order_generate_bot.entity.Product;
import uz.pdp.order_generate_bot.repo.CategoryRepo;
import uz.pdp.order_generate_bot.repo.ProductRepo;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ButtonService {

    private final TelegramBot telegramBot;
    private final CategoryRepo categoryRepo;
    private final ProductRepo productRepo;
    private final TgUserRepo tgUserRepo;


    public void sendContact(TgUser tgUser, String fullName) {

            String msg = """
                Assalomu alaykum %s
                Botimizga hush kelibsiz 😊
                Botdan to'liq foydalanish uchun
                Contact yuborish tugmasini bosing !
                """.formatted(fullName);

            SendMessage message = new SendMessage(tgUser.getChatId(), msg);
            KeyboardButton contactButton = new KeyboardButton("Contact yuborish");
            contactButton.requestContact(true);
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                    contactButton
            );


            keyboardMarkup.oneTimeKeyboard(true);
            keyboardMarkup.resizeKeyboard(true);

            message.replyMarkup(keyboardMarkup);

        State state = State.builder()
                .userState(UserState.SEND_CONTACT)
                .build();

        tgUser.setState(state);
        tgUserRepo.save(tgUser);

        telegramBot.execute(message);


    }


    public void sendAllProduct(TgUser tgUser, UUID categoryId) {
        SendMessage message ;
        Long chatId = tgUser.getChatId();
        List<InlineKeyboardButton> buttons = new ArrayList<>();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        List<Product> products;
        if (categoryId != null) {
            Optional<Category> byId = categoryRepo.findById(categoryId);
            Category category = byId.get();
            message = new SendMessage(chatId, category.getName());
            products = productRepo.findAllByCategoryId(categoryId);

        } else {
            message = new SendMessage(chatId, "All Products");
            products = productRepo.findAll();
        }

        System.out.println(products);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        for (Product product : products) {
            buttons.add(new InlineKeyboardButton(product.getName()).callbackData(String.valueOf(product.getId())));
        }
        rows.add(buttons);
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j <= i; j++) {
                inlineKeyboardMarkup.addRow(rows.getFirst().toArray(new InlineKeyboardButton[0]));
            }
        }


        message.replyMarkup(inlineKeyboardMarkup.addRow());

        State state = State.builder()
                .userState(UserState.SEND_PRODUCT)
                .build();
        tgUser.setState(state);
        tgUserRepo.save(tgUser);

        telegramBot.execute(message);
    }



    public void akceptContuctSendLocation(TgUser tgUser, Message message) {
        if (tgUser.getState().getUserState().equals(UserState.SEND_CONTACT)){
            System.out.println("Send location");
            Contact contact = message.contact();
            tgUser.setPhone(contact.phoneNumber());
            SendMessage sendMessage = new SendMessage(
                    message.chat().id(),
                    """
                            Buyurtmalarni yetqazib berishimiz uchun
                            Joylashuvingizni yuboring !
                            """
            );

            sendMessage.replyMarkup(new ReplyKeyboardMarkup(
                    new KeyboardButton("Joylashuvni yuborish")
                            .requestLocation(true)).oneTimeKeyboard(true).resizeKeyboard(true));
            State state = State.builder()
                    .userState(UserState.SEND_LOCATION)
                    .build();
            tgUser.setState(state);
            tgUserRepo.save(tgUser);

            telegramBot.execute(sendMessage);
        }

    }


    public void akceptLocationSendAllCategory(TgUser tgUser, Message message) {
        if (tgUser.getState().getUserState().equals(UserState.SEND_LOCATION)) {
            System.out.println("Send All Category");
            tgUser.setLat(message.location().latitude());
            tgUser.setLng(message.location().longitude());

            SendMessage messages = new SendMessage(message.chat().id(), "All Categories");

            List<InlineKeyboardButton> buttons = new ArrayList<>();
            List<List<InlineKeyboardButton>> rows = new ArrayList<>();
            List<Category> categories = categoryRepo.findAll();
            System.out.println(categories);

            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

            for (Category category : categories) {
                buttons.add(new InlineKeyboardButton(category.getName()).callbackData(String.valueOf(category.getId())));
            }
            rows.add(buttons);

            for (int i = 0; i < rows.size(); i++) {
                for (int j = 0; j <= i; j++) {
                    inlineKeyboardMarkup.addRow(rows.getFirst().toArray(new InlineKeyboardButton[0]));
                }
            }

            messages.replyMarkup(inlineKeyboardMarkup.addRow());
            State state = State.builder()
                    .userState(UserState.SEND_CATEGORY)
                    .build();
            tgUser.setState(state);
            tgUserRepo.save(tgUser);
            telegramBot.execute(messages);
        }

    }


    /*public void sendProductById(TgUser tgUser, String data, Total total) {
        Optional<Product> byId = productRepo.findById(UUID.fromString(data));
        Product product = null;
        if (byId.isPresent()) {
            product = byId.get();
        }
        System.out.println(product);


        int total1 = total.getTotal();

        InlineKeyboardButton pilus = new InlineKeyboardButton("  +  ").callbackData("+");
        InlineKeyboardButton minus = new InlineKeyboardButton("  -  ").callbackData("-");
        InlineKeyboardButton savat = new InlineKeyboardButton(" savatga qo'shish ").callbackData("savat");

        InlineKeyboardButton totalSavat = new InlineKeyboardButton(String.valueOf(total1));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.addRow(pilus, totalSavat , minus );
        inlineKeyboardMarkup.addRow(savat);

        SendPhoto sendPhoto = new SendPhoto(tgUser.getChatId(), product.getAttachment().getContent());
        sendPhoto.caption("     " + product.getName() + ": " + product.getPrice());
        sendPhoto.replyMarkup(inlineKeyboardMarkup);
        sendPhoto.allowSendingWithoutReply(true);
        sendPhoto.isMultipart();


        State state = State.builder()
                .userState(UserState.SEND_BASKED)
                .build();
        tgUser.setState(state);

        tgUserRepo.save(tgUser);

        telegramBot.execute(sendPhoto);

    }*/

    public void sendProductById(TgUser tgUser, String data, Total total) {
        try {

            Optional<Product> byId = productRepo.findById(UUID.fromString(data));
            if (!byId.isPresent()) {
                System.out.println("Product not found for ID: " + data);
                return;
            }
            Product product = byId.get();
            System.out.println("Product found: " + product);


            InlineKeyboardButton plusButton = new InlineKeyboardButton("  +  ").callbackData("+");
            InlineKeyboardButton minusButton = new InlineKeyboardButton("  -  ").callbackData("-");
            InlineKeyboardButton addToCartButton = new InlineKeyboardButton(" savatga qo'shish ").callbackData("savat");
            InlineKeyboardButton totalButton = new InlineKeyboardButton(String.valueOf(total.getTotal()));


            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.addRow(plusButton, totalButton, minusButton);
            inlineKeyboardMarkup.addRow(addToCartButton);


            SendPhoto sendPhoto = new SendPhoto(tgUser.getChatId(), product.getAttachment().getContent());
            sendPhoto.caption(" " + product.getName() + ": " + product.getPrice());
            sendPhoto.replyMarkup(inlineKeyboardMarkup);
            sendPhoto.allowSendingWithoutReply(true);



            State state = State.builder()
                    .userState(UserState.SEND_BASKED)
                    .build();
            tgUser.setState(state);
            tgUserRepo.save(tgUser);

            // Send photo message
            telegramBot.execute(sendPhoto);
            System.out.println("Photo sent successfully.");

            /*SendMessage message = new SendMessage(tgUser.getChatId(), "Qancha xarid qilasz");
            message.replyMarkup(inlineKeyboardMarkup);
            telegramBot.execute(message);*/


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
