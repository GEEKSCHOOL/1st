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

        map.keySet().forEach(key -> messageList.addAll(map.getOrDefault(key, new ArrayList<>())));

        Collections.sort(messageList, (o1, o2) -> o1.getTime() < o2.getTime() ? 1 : -1);

        return messageList;
    }

    public static List<Message> getAllMessageList(final String userId) {
        List<Message> messageList = map.getOrDefault(userId, new ArrayList<>());

        Collections.sort(messageList, (o1, o2) -> o1.getTime() < o2.getTime() ? 1 : -1);

        return messageList;
    }

    public static void addMessage(final Message message) {
        String userId = message.getUserId();

        List<Message> messageList = map.get(userId);
        if (messageList == null) {
            messageList = new ArrayList<>();
            map.put(userId, messageList);
        }
        messageList.add(message);
    }

    public static void removeMessage(final String userId, final String messageId) {
        List<Message> messageList = map.getOrDefault(userId, new ArrayList<>());

        Message removeMessage = messageList.stream()
                .filter(message -> message.getId().equals(messageId))
                .findFirst()
                .orElse(null);

        if (removeMessage != null) {
            messageList.remove(removeMessage);
        }
    }

    public static void removeAllMessage(final String userId) {
        map.remove(userId);
    }

}

