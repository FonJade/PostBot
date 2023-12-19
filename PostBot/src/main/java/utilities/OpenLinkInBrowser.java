package utilities;

import java.awt.Desktop;
import java.net.URI;

public class OpenLinkInBrowser {
    public static void openDiscordLink() {
        try {
            Desktop desktop = java.awt.Desktop.getDesktop();
            URI uri = new URI("https://discord.com/oauth2/authorize?client_id=1186215556106436618&scope=bot&state=15773059ghq9183habn&permission=8");
            desktop.browse(uri);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}