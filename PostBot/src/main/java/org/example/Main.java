package org.example;


import database.SQLiteDB;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Main
{

    public static void main(String[] args) throws IOException, TelegramApiException
    {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "\\..\\tele.key";
        SQLiteDB.createNewTable();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        var data = Files.readAllLines(new File(filePath).toPath());
        var token = data.get(0);
        var name = data.get(1);


        PostBot bot = new PostBot(token, name);
        telegramBotsApi.registerBot(bot);
    }

}