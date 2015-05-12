package jp.geekschool.web.security;

import twitter4j.Twitter;

public class Authentication {

    private Twitter twitter;

    public Twitter getTwitter() {
        return twitter;
    }

    public void setTwitter(final Twitter twitter) {
        this.twitter = twitter;
    }
}
