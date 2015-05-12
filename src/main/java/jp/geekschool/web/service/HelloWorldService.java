package jp.geekschool.web.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String getHelloMessage() {
        return "HelloWorld";
    }

}
