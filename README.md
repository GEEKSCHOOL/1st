# JavaによるWeb開発の最前線

## 目標
* いまどきのJavaを使ったWEB開発を知ってもらう
* 簡単にすぐにWEB開発ができるようになる
* Facebookを使ったWEBアプリを作成できるようになる

## やっていくこと
* A. 外部サービス(Facebook)を使った認証
* B. 画面に名前とプロフィール画像表示
* C. メッセージの投稿
* D. 自分が投稿した全てのメッセージを削除
* E. 自分が投稿した特定のメッセージを削除

---

## A. 外部サービスを使った認証
全リクエストに対して認証をする必要がある。


AuthFilterを用意してあるので、SessionからFacebookの認証情報を取得し判断する。

    Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
    if (facebook == null) {
        // Session情報がnullの場合、認証する
        redirectOAuthFacebook(request, response);
        return;
    }

## B. 画面に名前とプロフィール画像表示
画面表示に[Thymeleaf](http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf_ja.html)を使用しているので、
Controllerでテンプレートとテンプレートに渡すデータを渡す

SampleController.java

    @RequestMapping("/")
    public String index(final Model model) {
        model.addAttribute("name", "atWare Tarou");
        model.addAttribute("age", 10);

        return "index";
    }

index.html

    <!DOCTYPE html>
    <html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
    <head lang="en">
        <meta charset="UTF-8"/>
        <title>Index HTML</title>
    </head>
    <body>
    <h1 th:text="|Welcome ${name} ($age}歳|"></h1>
    </body>
    </html>

## C. メッセージ投稿
### ブラウザからメッセージを受け取る
index.htmlの **formのaction** とMessageControllerの **@RequestMapping** に対応している。

index.htmlのform内の **inputのname** とMessageControllerの **@RequestParam** に対応している

index.html

    <form action="save" method="post">
        <input type="text" name="msg"/>
        <input type="submit" value="送信"/>
    </form>

MessageController

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@RequestParam("msg") final String text) throws FacebookException {
        // 省略
    }

### メッセージをアプリに保存する
MessageServiceで受け取った文字列をデータベースに保存する

MessageService#createMessage

    LocalDateTime dateTime = LocalDateTime.now();

    Message message = new Message();
    message.setId(DATE_TIME_FORMATTER.format(dateTime) + "_" + facebook.getId());
    message.setUserId(facebook.getId());
    message.setText(text);
    message.setProfilePictureUrl(facebook.getPictureURL().toString());
    message.setLocalDateTime(dateTime);

    MessageDatabase.addMessage(message);

### アプリ保存されているメッセージ一覧を表示
メッセージ一覧を取得して、テンプレートに渡す必要がある

MessageController#index

    model.addAttribute("messageList", messageService.getAllMessages());
    
## D.E. メッセージの削除
 * MessageController
 * MessageService
 * MessageDatabase
 * index.html
 
これらを使って実装してください。

## 発展課題
 1. 空文字メッセージの投稿禁止
 2. １分以内の同じメッセージの投稿禁止
 3. 自分でFacebook Developerに登録し、自分のアプリとしてデプロイを行う
 4. Twitterなど他の認証機構との接続

# 使用技術
 * [Facebook4J](http://facebook4j.org/ja/)
 * [Spring Boot](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
 * [Spring MVC](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html)
 * [Thymeleaf](http://www.thymeleaf.org/doc/tutorials/2.1/usingthymeleaf_ja.html)
