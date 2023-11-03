package com.satrt.springweb.loginusermsg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Nanzhou
 * @version: v0.0.1
 * @date: 2023 2023/10/27 20:37
 */
@Controller
public class LoginUserMsgController {
    @RequestMapping("/LoginUserMsg")
    public String info(){
        return "backgrounder/info/info";
    }

}
