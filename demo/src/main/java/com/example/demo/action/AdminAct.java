package com.example.demo.action;

import com.example.demo.action.Request.AdminLoginRequest;
import com.example.demo.action.Response.AdminLoginResponse;
import com.example.demo.entity.User;
import com.example.demo.manager.UserMng;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "admin", description = "管理员")
@Controller
public class AdminAct {
    @ApiOperation(value = "登录", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public AdminLoginResponse login(AdminLoginRequest request, @ApiIgnore AdminLoginResponse response, @ApiIgnore ModelMap model) {

        User user = userMng.get(request.getUserId());
//        response.setUsername(user.getUsername());
//        response.setPassword(user.getPassword());
        model.addAttribute("user", user);
        return response.mergeModel(model);
    }

    @Autowired
    private UserMng userMng;
}
