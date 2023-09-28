package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class PostBot extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update)
    {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        copyMessage(id, msg.getMessageId());
        System.out.println(user.getFirstName() + " wrote " + msg.getText());

    }

    @Override
    public String getBotUsername()
    {
        return "PostBot";
    }

    @Override
    public String getBotToken()
    {
        return "6474374849:AAEOOTTDQaLDvgs6iXt8WYhldgYeDm5A6Xg";
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
}
