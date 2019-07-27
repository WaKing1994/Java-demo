package com.example.demo.action;

import com.example.demo.entity.User;
import com.example.demo.manager.UserMng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserAct {

    @RequestMapping(value = {"/list"})
    public String list(ModelMap model, HttpServletRequest request) {
        List<User> users = userMng.findAll();
        model.addAttribute("users", users);
        return "/list";
    }

    @RequestMapping(value = {"/map"})
    public String map(ModelMap model, @RequestParam Integer id, HttpServletRequest request) {
        User user = userMng.get(id);
        model.addAttribute("user", user);
        return "/map";
    }

    @RequestMapping(value = {"/index"})
    public String index(ModelMap model, HttpServletRequest request) {
        return "/index";
    }

    @RequestMapping(value = {"/edit"})
    public String edit(ModelMap model, @RequestParam Integer id, HttpServletRequest request) {
        User user = userMng.get(id);
        model.addAttribute("user", user);
        return "/edit";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(ModelMap model, @RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        userMng.add(username, password);
        return list(model, request);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(ModelMap model, @RequestParam Integer id, HttpServletRequest request) {
        userMng.delete(id);
        return list(model, request);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(ModelMap model, @RequestParam Integer id, @RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        userMng.update(id, username, password);
        return list(model, request);
    }

    @Autowired
    private UserMng userMng;
}
