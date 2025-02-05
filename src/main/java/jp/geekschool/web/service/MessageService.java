package jp.geekschool.web.service;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import jp.geekschool.web.database.MessageDatabase;
import jp.geekschool.web.model.Message;
import org.springframework.stereotype.Service;

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

    // TODO C2.受け取ったメッセージをアプリに保存
    public void createMessage(final Facebook facebook, final String text) throws FacebookException {
        Message message = new Message();
        // Hint message.setText("今日はいい日ですね");

        // Hint メッセージをアプリに保存する

    }

    public void removeMessage(final String userId, final String messageId) {
        // TODO 特定ユーザの特定のメッセージを削除する
    }

    public void removeMessageByUserId(final String userId) {
        // TODO 特定ユーザの全メッセージを削除する
    }

}
