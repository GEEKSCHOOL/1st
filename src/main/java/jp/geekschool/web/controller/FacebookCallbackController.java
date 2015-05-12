package jp.geekschool.web.controller;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class FacebookCallbackController {

    @RequestMapping("/facebook/callback")
    public void callback(final HttpServletRequest request, final HttpServletResponse response, @RequestParam("code") final String oauthCode) throws IOException, FacebookException {
        HttpSession session = request.getSession();
        Facebook facebook = (Facebook) session.getAttribute("facebook");

        try {
            facebook.getOAuthAccessToken(oauthCode);
            response.sendRedirect(request.getContextPath() + "/");
        } catch (FacebookException | IOException e) {
            session.removeAttribute("facebook");
            throw e;
        }
    }
}
