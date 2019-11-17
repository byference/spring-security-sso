package com.github.byference.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * IndexController
 *
 * @author byference
 * @since 2019-11-17
 */
@Controller
public class Index2Controller {

    @GetMapping({"", "index"})
    public String index() {
        return "index.html";
    }

    @GetMapping("user")
    @ResponseBody
    public Object user() {

        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @GetMapping("admin")
    @ResponseBody
    public Object admin() {

        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
