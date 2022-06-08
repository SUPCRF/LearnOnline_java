package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.AdminUser;
import org.supcrf.service.AdminUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 管理员信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/admin-user")
@Api(tags = "管理员模块")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<AdminUser> adminUsers = adminUserService.list(queryWrapper);
        return ResultO.ok(adminUsers);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfo")
    public ResultO updateInfo(String userId, String sex, String phone, String email) {
        AdminUser temp = new AdminUser();
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        temp.setSex(Integer.parseInt(sex));
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        adminUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updatePass")
    public ResultO updatePass(String userId, String oldPass, String newPass) {
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        AdminUser user = adminUserService.getById(userId);
        if(!user.getPassword().equals(oldPass)) {
            System.out.println(user.getPassword());
            System.out.println(oldPass);
            return ResultO.error("修改失败");
        }
        AdminUser temp = new AdminUser();
        temp.setPassword(newPass);
        temp.setUpdateTime(LocalDateTime.now());
        adminUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/start")
    public ResultO start(String username) {
        AdminUser temp = new AdminUser();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<AdminUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = adminUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String username) {
        AdminUser temp = new AdminUser();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<AdminUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = adminUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modif")
    public ResultO modif(String username, String phone, String email) {
        AdminUser temp = new AdminUser();
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<AdminUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = adminUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String username, String sex, String phone, String email) {
        AdminUser adminUser = new AdminUser();
        adminUser.setAdminId(String.valueOf(UUID.randomUUID()));
        adminUser.setUsername(username);
        adminUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
        adminUser.setState(0);
        adminUser.setSex(Integer.parseInt(sex));
        adminUser.setAvatar("http://192.168.163.130:9000/edu/wallhaven-zxqj3j.png");
        adminUser.setPhone(phone);
        adminUser.setEmail(email);
        adminUser.setBirth(LocalDateTime.now());
        adminUser.setCreateTime(LocalDateTime.now());
        adminUser.setUpdateTime(LocalDateTime.now());
        boolean save = adminUserService.save(adminUser);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

}
