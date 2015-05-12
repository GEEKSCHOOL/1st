package jp.geekschool.web.security;

import facebook4j.Facebook;

public class Authentication {

    private Facebook facebook;

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(final Facebook facebook) {
        this.facebook = facebook;
    }
}
