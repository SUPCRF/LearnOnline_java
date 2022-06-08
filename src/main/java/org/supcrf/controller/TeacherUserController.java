package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.TeacherUser;
import org.supcrf.service.TeacherUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 教师信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/teacher-user")
@Api(tags = "教师模块")
public class TeacherUserController {

    @Autowired
    private TeacherUserService teacherUserService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<TeacherUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<TeacherUser> teacherUsers = teacherUserService.list(queryWrapper);
        return ResultO.ok(teacherUsers);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String username, String sex, String phone, String email) {
        TeacherUser teacherUser = new TeacherUser();
        teacherUser.setTeacherId(String.valueOf(UUID.randomUUID()));
        teacherUser.setUsername(username);
        teacherUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
        teacherUser.setState(0);
        teacherUser.setSex(Integer.parseInt(sex));
        teacherUser.setAvatar("http://192.168.163.130:9000/edu/wallhaven-zxqj3j.png");
        teacherUser.setPhone(phone);
        teacherUser.setEmail(email);
        teacherUser.setBirth(LocalDateTime.now());
        teacherUser.setCreateTime(LocalDateTime.now());
        teacherUser.setUpdateTime(LocalDateTime.now());
        boolean save = teacherUserService.save(teacherUser);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String username) {
        TeacherUser temp = new TeacherUser();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<TeacherUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = teacherUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }
    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modif")
    public ResultO modif(String username, String phone, String email) {
        TeacherUser temp = new TeacherUser();
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<TeacherUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = teacherUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/start")
    public ResultO start(String username) {
        TeacherUser temp = new TeacherUser();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<TeacherUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = teacherUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfo")
    public ResultO updateInfo(String userId, String sex, String phone, String email) {
        TeacherUser temp = new TeacherUser();
        QueryWrapper<TeacherUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        temp.setSex(Integer.parseInt(sex));
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        teacherUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updatePass")
    public ResultO updatePass(String userId, String oldPass, String newPass) {
        QueryWrapper<TeacherUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        TeacherUser user = teacherUserService.getById(userId);
        if(!user.getPassword().equals(oldPass)) {
            System.out.println(user.getPassword());
            System.out.println(oldPass);
            return ResultO.error("修改失败");
        }
        TeacherUser temp = new TeacherUser();
        temp.setPassword(newPass);
        temp.setUpdateTime(LocalDateTime.now());
        teacherUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

}
