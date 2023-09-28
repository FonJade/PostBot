package org.example;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class PostBot extends TelegramLongPollingBot
{
    @Override
    public void onUpdateReceived(Update update) {

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
