package jp.geekschool.web.controller;

import jp.geekschool.web.service.HelloWorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Autowired
    private HelloWorldService helloWorldService;

    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        return helloWorldService.getHelloMessage();
    }

}
