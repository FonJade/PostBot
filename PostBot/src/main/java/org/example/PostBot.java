package org.example;

import database.SQLiteDB;
import models.Keyboard;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaVideo;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.ArrayList;
import java.util.List;

import static discord.MessageApp.startBotWithMessage;
import static utilities.OpenLinkInBrowser.openDiscordLink;
import static vk.ApiAppRequests.postMessageToWall;

public class PostBot extends TelegramLongPollingBot
{

    public PostBot(String token) {
        super(token);
    }

    List<InputMedia> media = new ArrayList<>();
    Message msg = new Message();
    @Override
    public void onUpdateReceived(Update update) {
        boolean finished = false;
        if (update.hasMessage()) {
            var nextMsg = update.getMessage();
            var user = nextMsg.getFrom();
            var id = user.getId();
            System.out.println(id);
            if (nextMsg.isCommand()) {
                if (nextMsg.getText().equals("/send"))
                    sendMenu(id, "<b>Menu</b>", Keyboard.getMainMenu());
                if (nextMsg.getText().equals("/clear"))
                    media.clear();
                if (nextMsg.getText().equals("/start"))
                    SQLiteDB.insertUser(id);
                if (msg != null && nextMsg.getText().equals("/setTg")) {
                    System.out.println(Long.parseLong(msg.getText()));
                    SQLiteDB.insertTg(id, Long.parseLong(msg.getText()));
                    sendText(id,"Telegram set completed");
                }
                if (msg != null && nextMsg.getText().equals("/setVk")) {
                    System.out.println(Long.parseLong(msg.getText()));
                    SQLiteDB.insertVk(id);
                }
                finished = true;
            } else {
                msg = nextMsg;
                if(msg.hasPhoto()){
                    var photo = msg.getPhoto();
                    var cap = msg.getCaption();
                    InputMediaPhoto p = new InputMediaPhoto(photo.get(0).getFileId(), cap,null,
                            null,false,null,null,null,
                            msg.getHasMediaSpoiler());
                    media.add(p);
                }
                if(msg.hasVideo()){
                    var video = msg.getVideo();
                    var cap = msg.getCaption();
                    InputMediaVideo v = new InputMediaVideo(video.getFileId(),cap,null,
                            null,false,null,null, null,
                            video.getWidth(),video.getHeight(),video.getDuration(), null,
                            new InputFile(video.getThumbnail().getFileId()),msg.getHasMediaSpoiler());
                    media.add(v);
                }
                sendText(id,"Message copied");
                System.out.println(user.getFirstName() + " wrote " + msg.getText());
            }

        }
        if (!finished) {
            var callbackQuery = update.getCallbackQuery();
            if (callbackQuery != null) {
                var data = callbackQuery.getData();
                System.out.println(msg);
                buttonTap(data, msg);
                sendText(msg.getFrom().getId(),"Message sent");
            }
        }
    }

    private void buttonTap(String data, Message msg) {
        if (data.equals("vk")) {
            postMessageToWall(msg.getText());

        }
        if (data.equals("telega")) {
            Long tgId = SQLiteDB.getTgId(msg.getFrom().getId());
            if(media.size() > 1) {
                if(media.size() > 9){
                    sendText(msg.getChatId(),"Too much medias!");
                    media.clear();
                    return;
                }
                sendMedia(tgId,media);
                media.clear();
                return;
            }
            if(msg.hasPhoto()) {
                sendPhoto(tgId, msg.getCaption(), new InputFile(msg.getPhoto().get(0).getFileId()));
                return;
            }
            if(msg.hasVideo()) {
                sendVideo(tgId, msg.getCaption(), new InputFile(msg.getVideo().getFileId()));
                return;
            }
            sendText(tgId,msg.getText());
        }
        if (data.equals("dis")) {
            openDiscordLink();
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
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMedia(Long where, List<InputMedia> mediaInput){
        SendMediaGroup smg = SendMediaGroup.builder()
                .chatId(where)
                .medias(mediaInput)
                .build();
        try {
            execute(smg);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendPhoto(Long who, String what, InputFile photo){
        SendPhoto sp = SendPhoto.builder()
                .chatId(who.toString())
                .caption(what)
                .photo(photo)
                .build();
        try {
            execute(sp);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendVideo(Long where, String what, InputFile video){
        SendVideo sv = SendVideo.builder()
                .chatId(where)
                .caption(what)
                .video(video)
                .build();
        try {
            execute(sv);
        } catch (TelegramApiException e) {
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
