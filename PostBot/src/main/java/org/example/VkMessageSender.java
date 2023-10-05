package org.example;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.responses.PostResponse;

public class VkMessageSender {
    private final VkApiClient vk;
    private final UserActor actor;
    private final int groupId;

    public VkMessageSender(VkApiClient vk, UserActor actor, int groupId) {
        this.vk = vk;
        this.actor = actor;
        this.groupId = groupId;
    }

    public void sendMessage(String message) {
        try {
            PostResponse response = vk.wall().post(actor)
                    .ownerId(-groupId)
                    .message(message)
                    .execute();
            System.out.println("Message sent to VK group: " + response.getPostId());
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
    }
}