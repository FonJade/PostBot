package discord;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Event extends ListenerAdapter {
    private final String message;

    public Event(String message) {
        this.message = message;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equalsIgnoreCase("!hello")) {
            event.getChannel().sendMessage(message).queue();
        }
    }
}
