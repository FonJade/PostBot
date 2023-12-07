package org.example;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;
public class Keyboard {
    public static InlineKeyboardMarkup getMainMenu() {
        var dis = InlineKeyboardButton.builder()
                .text("Discord").callbackData("dis")
                .build();

        var vk = InlineKeyboardButton.builder()
                .text("Vk").callbackData("vk")
                .build();

        var telega = InlineKeyboardButton.builder()
                .text("Telegram").callbackData("telega")
                .build();

        return InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(vk))
                .keyboardRow(List.of(dis))
                .keyboardRow(List.of(telega))
                .build();
    }
}
