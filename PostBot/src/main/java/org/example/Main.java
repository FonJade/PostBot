package org.example;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main
{

    public static void main(String[] args) throws IOException, TelegramApiException
    {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "\\..\\tele.key";
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        var data = Files.readAllLines(new File(filePath).toPath());
        var token = data.get(0);
        System.out.println(token);
        var name = data.get(1);
        telegramBotsApi.registerBot(new PostBot(token, name));
    }

}