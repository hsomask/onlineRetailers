package com.imooc.backOffice.controller;

import com.imooc.backOffice.constant.ShiroConstant;
import com.imooc.backOffice.entity.UserEntity;
import com.imooc.backOffice.service.UserService;
import com.imooc.backOffice.utils.RedisUtil;
import com.imooc.backOffice.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author:hsoluo
 * @date 2021/3/16 10:36
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<?> login(@RequestParam String username, @RequestParam String password) {
        Result<String> result = new Result<>();
        UserEntity user = userService.findUserByUserName(username);
        if (user == null) {
            result.setSuccess(false);
            result.setMessage("无该用户，请进行注册");
            result.setCode(400);
        } else if (!user.getPassword().equals(password)) {
            result.setSuccess(false);
            result.setMessage("用户名或者密码错误");
            result.setCode(400);
        } else {
            String token = userService.loginSuccess(user);
            result.setSuccess(true);
            result.setResult(token);
            result.setCode(200);
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result<?> logout() {
        Result<?> result = new Result<>();
        Subject sub = SecurityUtils.getSubject();
        log.info("user: " + sub.getPrincipals());
        UserEntity user = (UserEntity) sub.getPrincipal();
        RedisUtil.del(ShiroConstant.LOGIN_SHIRO_CACHE + user.getId(), ShiroConstant.ROLE_SHIRO_CACHE + user.getId());

        return result;
    }


    @RequestMapping(value = "/sign", method = RequestMethod.POST)
    @RequiresPermissions({"1"})
    public Result<?> sign(@RequestBody UserEntity user) {
        Result<UserEntity> result = new Result<>();
        if (userService.sign(user)) {
            result.setCode(200);
            result.setSuccess(true);
            result.setResult(user);
        } else {
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("注册用户失败");
        }

        return result;
    }

    @RequestMapping(value = "/delUser", method = RequestMethod.POST)
    public Result<?> delUser(@RequestParam Long userId){
        Result<?> result=new Result<>();
        if(userService.delUser(userId)){
            result.setCode(200);
            result.setSuccess(true);
            result.setMessage("删除用户成功");
        }else {
            result.setCode(500);
            result.setSuccess(false);
            result.setMessage("删除用户失败");
        }

        return result;
    }

}
