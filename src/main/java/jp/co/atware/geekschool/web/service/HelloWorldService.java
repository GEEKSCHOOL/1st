package jp.co.atware.geekschool.web.service;

import org.springframework.stereotype.Component;

@Component
public class HelloWorldService {

    public String getHelloMessage() {
        return "HelloWorld";
    }

}
