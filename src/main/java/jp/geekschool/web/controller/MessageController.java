package jp.geekschool.web.controller;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import jp.geekschool.web.security.AuthenticationHolder;
import jp.geekschool.web.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String index(final Model model) throws FacebookException {
        Facebook facebook = AuthenticationHolder.getAuthentication().getFacebook();

        model.addAttribute("name", facebook.getMe().getName());
        model.addAttribute("url", facebook.getPictureURL().toString());
        model.addAttribute("messageList", messageService.getAllMessages());
        model.addAttribute("id", facebook.getId());
        model.addAttribute("existMyMessageList", !messageService.getAllMessages(facebook.getId()).isEmpty());

        return "index";
    }

    @RequestMapping(value = "/post-message", method = RequestMethod.POST)
    public String post(@RequestParam("text") final String text) throws FacebookException {
        Facebook facebook = AuthenticationHolder.getAuthentication().getFacebook();

        messageService.createMessage(facebook, text);

        return createRedirect("/");
    }

    private String createRedirect(final String requestUrl) {
        return "redirect:" + requestUrl;
    }

    @RequestMapping(value = "/remove-all-by-user-id", method = RequestMethod.POST)
    public String removeAllMessage() throws FacebookException {
        Facebook facebook = AuthenticationHolder.getAuthentication().getFacebook();

        messageService.removeMessageByUserId(facebook.getId());

        return createRedirect("/");
    }

    @RequestMapping(value = "/remove-by-message-id", method = RequestMethod.POST)
    public String removeMessage(@RequestParam("messageId") final String messageId) throws FacebookException {
        Facebook facebook = AuthenticationHolder.getAuthentication().getFacebook();

        messageService.removeMessage(facebook.getId(), messageId);

        return createRedirect("/");
    }

}

