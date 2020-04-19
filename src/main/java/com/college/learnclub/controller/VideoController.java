package com.college.learnclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoController {

    @RequestMapping("/display")
    public String display() {
        return "video/display";
    }

    @RequestMapping("/comment")
    public String comment() {
        return "video/comment";
    }

}
