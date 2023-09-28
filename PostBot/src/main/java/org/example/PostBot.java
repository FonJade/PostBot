package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PostBot extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update)
    {
        System.out.println(update);
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
}
