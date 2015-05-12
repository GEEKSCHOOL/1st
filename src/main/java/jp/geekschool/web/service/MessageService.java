package jp.geekschool.web.service;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import jp.geekschool.web.database.MessageDatabase;
import jp.geekschool.web.model.Message;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class MessageService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

    public List<Message> getAllMessages() {
        return MessageDatabase.getAllMessageList();
    }

    public List<Message> getAllMessages(final String userId) {
        return MessageDatabase.getAllMessageList(userId);
    }

    public String createMessage(final Facebook facebook, final String text) throws FacebookException {
        LocalDateTime dateTime = LocalDateTime.now();

        Message message = new Message();
        message.setId(DATE_TIME_FORMATTER.format(dateTime) + "_" + facebook.getId());
        message.setUserId(facebook.getId());
        message.setText(text);
        message.setProfilePictureUrl(facebook.getPictureURL().toString());
        message.setLocalDateTime(dateTime);

        MessageDatabase.addMessage(message);

        return null;
    }

    public void removeMessage(final String userId, final String messageId) {
        MessageDatabase.removeMessage(userId, messageId);
    }

    public void removeMessageByUserId(final String userId) {
        MessageDatabase.removeAllMessage(userId);
    }

}
