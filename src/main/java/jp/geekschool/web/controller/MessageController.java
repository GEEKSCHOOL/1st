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

        // TODO B1.画面に名前とプロフィール画像を表示
        // Hint model.addAttribute("name", "atWare");
        // Hint 同じく url にプロフィール画像のURLを設定する。
        // Hint 画面を表示するHTMLの名前をこのメソッドの戻り値とする。


        // TODO C3.チャットのメッセージ表示
        // Hint model.addAttribute("messageList", 全ユーザの全メッセージ);

        return "???";
    }

    // TODO C1. メッセージをブラウザから受け取る
    @RequestMapping(value = "/???", method = RequestMethod.POST)
    public String post(@RequestParam("???") final String text) throws FacebookException {
        Facebook facebook = AuthenticationHolder.getAuthentication().getFacebook();

        messageService.createMessage(facebook, text);

        return createRedirect("???");
    }

    private String createRedirect(final String requestUrl) {
        return "redirect:" + requestUrl;
    }

    @RequestMapping(value = "/remove-all-by-user-id", method = RequestMethod.POST)
    public String removeAllMessage() throws FacebookException {
        // TODO 特定ユーザの全メッセージを削除する

        return createRedirect("???");
    }

    @RequestMapping(value = "/remove-by-message-id", method = RequestMethod.POST)
    public String removeMessage(@RequestParam("???") final String messageId) throws FacebookException {
        // TODO 特定ユーザの特定のメッセージを削除する

        return createRedirect("???");
    }

}

