package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class PostBot extends TelegramLongPollingBot
{
    private final String name;
    private InlineKeyboardMarkup keyboardM1;

    public PostBot(String token, String name) {
        super(token);
        this.name = name;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        copyMessage(id, msg.getMessageId());
        System.out.println(user.getFirstName() + " wrote " + msg.getText());

        var dis = InlineKeyboardButton.builder()
                .text("Discord").callbackData("dis")
                .build();

        var vk = InlineKeyboardButton.builder()
                .text("Vk").callbackData("vk")
                .build();

        var telega = InlineKeyboardButton.builder()
                .text("Telegram").callbackData("telega")
                .build();

        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(vk))
                .keyboardRow(List.of(dis))
                .keyboardRow(List.of(telega))
                .build();

        if(msg.isCommand()){
            if (msg.getText().equals("/menu"))
                sendMenu(id, "<b>Menu 1</b>", keyboardM1);
            return;                                     //We don't want to echo commands, so we exit
        }
    }

    @Override
    public String getBotUsername()
    {
        return "PostBot";
    }


    public void sendText(Long who, String what)
    {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what)
                .build();
        try
        {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyMessage(Long who, Integer msgId)
    {
        CopyMessage cm = CopyMessage.builder()
                .fromChatId(who.toString())
                .chatId(who.toString())
                .messageId(msgId)
                .build();

        try
        {
            execute(cm);
        } catch (TelegramApiException e)
        {
            throw new RuntimeException(e);
        }
    }
    public void sendMenu(Long who, String txt, InlineKeyboardMarkup kb){
        SendMessage sm = SendMessage.builder().chatId(who.toString())
                .parseMode("HTML").text(txt)
                .replyMarkup(kb).build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
