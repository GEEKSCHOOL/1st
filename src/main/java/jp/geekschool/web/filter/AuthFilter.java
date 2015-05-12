package jp.geekschool.web.filter;

import jp.geekschool.web.security.Authentication;
import jp.geekschool.web.security.AuthenticationHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
            if (twitter == null) {
                redirectOAuthTwitter(request, response);
                return;
            }

            Authentication authentication = new Authentication();
            authentication.setTwitter(twitter);
            AuthenticationHolder.setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (TwitterException e) {
            throw new ServletException(e);
        } finally {
            AuthenticationHolder.clearAuthentication();
        }
    }

    private void redirectOAuthTwitter(final HttpServletRequest request, final HttpServletResponse response) throws IOException, TwitterException {
        Twitter twitter = new TwitterFactory().getInstance();
        request.getSession().setAttribute("twitter", twitter);

        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/twitter/callback");

        RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());

        request.getSession().setAttribute("requestToken", requestToken);

        response.sendRedirect(requestToken.getAuthorizationURL());
    }

}

