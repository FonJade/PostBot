package org.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class PostBot extends TelegramLongPollingBot
{
    private final String name;
    public PostBot(String token, String name) {
        super(token);
        this.name = name;
    }

    Message msg = new Message();
    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage() && update.getMessage().hasText()){
            var nextmsg = update.getMessage();
            var user = nextmsg.getFrom();
            var id = user.getId();
            System.out.println(id);
            if(nextmsg.isCommand()){
                if (nextmsg.getText().equals("/menu"))
                    sendMenu(id, "<b>Menu 1</b>", Keyboard.getMainMenu());
                if (nextmsg.getText().equals("/start"))
                    SQLiteDB.insertUser(id);
                if (msg != null && nextmsg.getText().equals("/setTg")) {
                    System.out.println(Long.parseLong(msg.getText()));
                    SQLiteDB.insertTg(id, Long.parseLong(msg.getText()));
                }
                return;
            }
            msg = nextmsg;

            copyMessage(id, msg.getMessageId());
            sendMenu(id, "<b>Menu 1</b>", Keyboard.getMainMenu());
            System.out.println(user.getFirstName() + " wrote " + msg.getText());

        }
        var callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            var data = callbackQuery.getData();
            System.out.println(msg.getText());
            buttonTap(data, msg);
        }
    }

    private void buttonTap(String data, Message msg)
    {
        if (data.equals("vk")) {
            VkApiClient vk = new VkApiClient(HttpTransportClient.getInstance());

            String currentDir = System.getProperty("user.dir");
            String filepath = currentDir + "\\..\\vk.key";

            List<String> vkdata = null;
            try {
                vkdata = Files.readAllLines(new File(filepath).toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int userId = Integer.parseInt(vkdata.get(0));
            var accessToken = vkdata.get(1);
            int groupId = Integer.parseInt(vkdata.get(2));
            System.out.println(userId);

            UserActor actor = new UserActor(userId, accessToken);


            VkMessageSender VkMessegeSender = new VkMessageSender(vk, actor, groupId);
            VkMessegeSender.sendMessage("Hello, VK!");
        }
        if(data.equals("telega")){
            Long tgId = SQLiteDB.getTgId(msg.getFrom().getId());
            sendText(tgId,msg.getText());
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
