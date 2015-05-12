package jp.geekschool.web.filter;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import jp.geekschool.web.security.Authentication;
import jp.geekschool.web.security.AuthenticationHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
        try {
            Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
            if (facebook == null) {
                redirectOAuthFacebook(request, response);
                return;
            }

            Authentication authentication = new Authentication();
            authentication.setFacebook(facebook);
            AuthenticationHolder.setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } finally {
            AuthenticationHolder.clearAuthentication();
        }
    }

    private void redirectOAuthFacebook(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        StringBuffer callbackURL = request.getRequestURL();
        callbackURL.replace(callbackURL.lastIndexOf("/"), callbackURL.length(), "").append("/facebook/callback");

        Facebook facebook = new FacebookFactory().getInstance();
        request.getSession().setAttribute("facebook", facebook);

        response.sendRedirect(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
    }

}

