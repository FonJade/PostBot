package vk;

import models.JsonResponse;
import utilities.ApiUtilities;
import utilities.Config;

public class ApiAppRequests {
    private static String OWNER_ID = Config.get("owner_id");
    private static final String ACCESS_TOKEN = Config.get("access_token");
    private static final String API_VERSION = Config.get("api_version");

    public static String postMessageToWall(String text) {
        String request = String.format("%s?owner_id=%s&message=%&access_token=%s&v=%s", "wall.post", OWNER_ID, text, ACCESS_TOKEN, API_VERSION);
        JsonResponse jsonResponse = ApiUtilities.post(request);
        return jsonResponse.getBody().toPrettyString();
    }

    public String getOwnerId(String OWNER_ID){
        return ApiAppRequests.OWNER_ID;
    }
}
