package jp.geekschool.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.RequestToken;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class TwitterCallbackController {

    @RequestMapping("/twitter/callback")
    public void callback(final HttpServletRequest request, final HttpServletResponse response, @RequestParam("oauth_verifier") final String verifier) throws TwitterException, IOException {
        HttpSession session = request.getSession();
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");

        try {
            RequestToken requestToken = (RequestToken) session.getAttribute("requestToken");
            twitter.getOAuthAccessToken(requestToken, verifier);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (TwitterException | IOException e) {
            session.removeAttribute("twitter");
            throw e;
        } finally {
            request.getSession().removeAttribute("requestToken");
        }

    }
}
