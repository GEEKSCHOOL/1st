package jp.geekschool.web.service;

import jp.geekschool.web.database.MessageDatabase;
import jp.geekschool.web.model.Message;
import org.springframework.stereotype.Service;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

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

    public String createMessage(final Twitter twitter, final String text) throws TwitterException {
        LocalDateTime dateTime = LocalDateTime.now();

        User user = twitter.verifyCredentials();

        Message message = new Message();
        message.setId(DATE_TIME_FORMATTER.format(dateTime) + "_" + twitter.getId());
        message.setUserId(String.valueOf(user.getId()));
        message.setText(text);
        message.setProfilePictureUrl(user.getMiniProfileImageURL());
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
