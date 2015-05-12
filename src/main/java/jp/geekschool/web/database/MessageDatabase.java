package jp.geekschool.web.database;

import jp.geekschool.web.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageDatabase {

    private static Map<String, List<Message>> map = new HashMap<>();

    public static List<Message> getAllMessageList() {
        List<Message> messageList = new ArrayList<>();

        map.keySet().forEach(key -> messageList.addAll(map.get(key)));

        Collections.sort(messageList, (o1, o2) -> o1.getTime() < o2.getTime() ? 1 : -1);

        return messageList;
    }

    public static void addMessage(final Message model) {
        String userId = model.getUserId();

        List<Message> messageModelList = map.get(userId);
        if (messageModelList == null) {
            messageModelList = new ArrayList<>();
            map.put(userId, messageModelList);
        }
        messageModelList.add(model);
    }

    public static void removeMessage(final String userId, final String messageId) {
        List<Message> messageModelList = map.get(userId);
        if (messageModelList == null) {
            return;
        }

        Message removeModel = messageModelList.stream()
                .filter(model -> model.getId().equals(messageId))
                .findFirst()
                .orElse(null);

        if (removeModel != null) {
            messageModelList.remove(removeModel);
        }
    }

    public static void removeAllMessage(final String userId) {
        map.remove(userId);
    }
}

