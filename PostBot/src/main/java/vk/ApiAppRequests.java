package vk;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.queries.messages.MessagesGetLongPollHistoryQuery;
import models.JsonResponse;
import utilities.ApiUtilities;
import utilities.Config;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class ApiAppRequests {
    private static final String OWNER_ID = Config.get("owner_id");
    private static final String ACCESS_TOKEN = ApiAppRequests.getAccessToken("51797027");
    private static final String API_VERSION = Config.get("api_version");


    public static String postMessageToWall(String text) {
        String request = String.format("%s?owner_id=%s&message=%&access_token=%s&v=%s", "wall.post", OWNER_ID, text, ACCESS_TOKEN, API_VERSION);
        JsonResponse jsonResponse = ApiUtilities.post(request);
        return jsonResponse.getBody().toPrettyString();
    }

    public String getOwnerId(String OWNER_ID){
        return ApiAppRequests.OWNER_ID;
    }

    private static String getAccessToken(String CLIENT_ID){
        String request = String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=https://oauth.vk.com/blank.html&group_ids=%s&scope=messages&response_type=code&v=5.131", CLIENT_ID, OWNER_ID);
        JsonResponse jsonResponse = ApiUtilities.post(request);
        return jsonResponse.getBody().toPrettyString();
    }
}
