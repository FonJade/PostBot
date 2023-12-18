package vk;

import models.JsonResponse;
import utilities.ApiUtilities;
import utilities.Config;

public class ApiAppRequests {
    private static final String OWNER_ID = Config.get("owner_id");
    private static final String ACCESS_TOKEN = ApiAppRequests.getAccessToken();
    private static final String API_VERSION = Config.get("api_version");


    public static String postMessageToWall(String text) {
        String request = String.format("%s?owner_id=%s&message=%&access_token=%s&v=%s", "wall.post", OWNER_ID, text, ACCESS_TOKEN, API_VERSION);
        JsonResponse jsonResponse = ApiUtilities.post(request);
        return jsonResponse.getBody().toPrettyString();
    }

    public String getOwnerId(){
        return ApiAppRequests.OWNER_ID;
    }

    private static String getAccessToken(){
        String request = String.format("https://oauth.vk.com/authorize?client_id=%s&display=page&redirect_uri=https://oauth.vk.com/blank.html&group_ids=%s&scope=messages&response_type=code&v=5.131", "51797027", ApiAppRequests.OWNER_ID);
        JsonResponse jsonResponse = ApiUtilities.post(request);
        return jsonResponse.getBody().toPrettyString();
    }
}
