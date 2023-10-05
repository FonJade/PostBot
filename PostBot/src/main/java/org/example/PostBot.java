package org.example;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

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

    @Override
    public void onUpdateReceived(Update update)
    {
        var msg = update.getMessage();
        var user = msg.getFrom();
        var id = user.getId();

        copyMessage(id, msg.getMessageId());
        System.out.println(user.getFirstName() + " wrote " + msg.getText());

        if(msg.isCommand()){
            if (msg.getText().equals("/menu"))
                sendMenu(id, "<b>Menu 1</b>", Keyboard.getMainMenu());
            return;
        }
        var callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            var data = callbackQuery.getData();
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
                UserActor actor = new UserActor(userId, accessToken);


                VkMessageSender VkMessegeSender = new VkMessageSender(vk, actor, groupId);
                VkMessegeSender.sendMessage("Hello, VK!");
            }
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
