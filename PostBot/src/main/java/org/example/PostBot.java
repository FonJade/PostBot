package org.example;

import database.SQLiteDB;
import models.Keyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import static discord.MessageApp.startBotWithMessage;
import static vk.ApiAppRequests.postMessageToWall;

public class PostBot extends TelegramLongPollingBot
{

    public PostBot(String token) {
        super(token);
    }

    Message msg = new Message();
    @Override
    public void onUpdateReceived(Update update) {
        boolean finished = false;
        if (update.hasMessage() && update.getMessage().hasText()) {
            var nextMsg = update.getMessage();
            var user = nextMsg.getFrom();
            var id = user.getId();
            System.out.println(id);
            if (nextMsg.isCommand()) {
                if (nextMsg.getText().equals("/send"))
                    sendMenu(id, "<b>Menu 1</b>", Keyboard.getMainMenu());
                if (nextMsg.getText().equals("/start"))
                    SQLiteDB.insertUser(id);
                if (msg != null && nextMsg.getText().equals("/setTg")) {
                    System.out.println(Long.parseLong(msg.getText()));
                    SQLiteDB.insertTg(id, Long.parseLong(msg.getText()));
                    sendText(id,"Telegram set complete");
                }
                if (msg != null && nextMsg.getText().equals("/setVk")) {
                    System.out.println(Long.parseLong(msg.getText()));
                    SQLiteDB.insertVk(id);
                }
                finished = true;
            } else {
                msg = nextMsg;
                sendText(id,"Message copied");
                System.out.println(user.getFirstName() + " wrote " + msg.getText());
            }

        }
        if (!finished) {
            var callbackQuery = update.getCallbackQuery();
            if (callbackQuery != null) {
                var data = callbackQuery.getData();
                System.out.println(msg.getText());
                buttonTap(data, msg);
                sendText(msg.getFrom().getId(),"Message send");
            }
        }
    }

    private void buttonTap(String data, Message msg) {
        if (data.equals("vk")) {
            postMessageToWall(msg.getText());

        }
        if (data.equals("telega")) {
            Long tgId = SQLiteDB.getTgId(msg.getFrom().getId());
            sendText(tgId, msg.getText());
        }
        if (data.equals("dis")) {
            startBotWithMessage(msg.getText());
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
