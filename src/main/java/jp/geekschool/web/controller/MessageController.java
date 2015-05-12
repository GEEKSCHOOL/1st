package jp.geekschool.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jp.geekschool.web.security.AuthenticationHolder;
import jp.geekschool.web.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

@Controller
public class MessageController {

    // TODO 発展課題
    // TODO 1. 空文字メッセージ禁止
    // TODO 2. 1分以内の同じメッセージの投稿禁止
    // TODO 3. 自分でFacebook Developerに登録し、自分のアプリとしてデプロイする
    // TODO 4. Twitterなど他の認証機構との接続

    @Autowired
    protected MessageService messageService;

    @RequestMapping("/")
    public String index(final Model model) throws TwitterException, JsonProcessingException {
        Twitter twitter = AuthenticationHolder.getAuthentication().getTwitter();

        User user = twitter.verifyCredentials();

        model.addAttribute("name", user.getName());
        model.addAttribute("url", user.getMiniProfileImageURL());
        model.addAttribute("messageList", messageService.getAllMessages());
        model.addAttribute("id", String.valueOf(user.getId()));
        model.addAttribute("existMyMessageList", !messageService.getAllMessages(String.valueOf(user.getId())).isEmpty());

        return "index";
    }

    @RequestMapping(value = "/post-message", method = RequestMethod.POST)
    public String post(@RequestParam("text") final String text) throws TwitterException {
        Twitter twitter = AuthenticationHolder.getAuthentication().getTwitter();

        messageService.createMessage(twitter, text);

        return createRedirect("/");
    }

    private String createRedirect(final String requestUrl) {
        return "redirect:" + requestUrl;
    }

    @RequestMapping(value = "/remove-all-by-user-id", method = RequestMethod.POST)
    public String removeAllMessage() throws TwitterException {
        Twitter twitter = AuthenticationHolder.getAuthentication().getTwitter();

        User user = twitter.verifyCredentials();

        messageService.removeMessageByUserId(String.valueOf(user.getId()));

        return createRedirect("/");
    }

    @RequestMapping(value = "/remove-by-message-id", method = RequestMethod.POST)
    public String removeMessage(@RequestParam("messageId") final String messageId) throws TwitterException {
        Twitter twitter = AuthenticationHolder.getAuthentication().getTwitter();

        User user = twitter.verifyCredentials();

        messageService.removeMessage(String.valueOf(user.getId()), messageId);

        return createRedirect("/");
    }

}

