package discord;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class MessageApp {
//https://discord.com/developers/applications/1186215556106436618/bot
    private static final String TOKEN = "MTE4NjIxNTU1NjEwNjQzNjYxOA.GoeQ5D.3ejFzKduaf5lUiILlz29ZJs0qqPr5Gq3hRXhGg";

    public static void startBotWithMessage(String message) {

        JDABuilder bot = JDABuilder.createDefault(TOKEN);

        bot.setStatus(OnlineStatus.ONLINE);
        bot.addEventListeners(new Event(message));
        
        bot.build();
    }
}
