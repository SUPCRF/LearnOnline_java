package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.StudentUser;
import org.supcrf.service.StudentUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 学生信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/student-user")
@Api(tags = "学生模块")
public class StudentUserController {

    @Autowired
    private StudentUserService studentUserService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<StudentUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<StudentUser> studentUsers = studentUserService.list(queryWrapper);
        return ResultO.ok(studentUsers);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfo")
    public ResultO updateInfo(String userId, String sex, String phone, String email) {
        StudentUser temp = new StudentUser();
        QueryWrapper<StudentUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        temp.setSex(Integer.parseInt(sex));
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        studentUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updatePass")
    public ResultO updatePass(String userId, String oldPass, String newPass) {
        QueryWrapper<StudentUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",userId);
        StudentUser user = studentUserService.getById(userId);
        if(!user.getPassword().equals(oldPass)) {
            System.out.println(user.getPassword());
            System.out.println(oldPass);
            return ResultO.error("修改失败");
        }
        StudentUser temp = new StudentUser();
        temp.setPassword(newPass);
        temp.setUpdateTime(LocalDateTime.now());
        studentUserService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/start")
    public ResultO start(String username) {
        StudentUser temp = new StudentUser();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<StudentUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = studentUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String username) {
        StudentUser temp = new StudentUser();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<StudentUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = studentUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modif")
    public ResultO modif(String username, String phone, String email) {
        StudentUser temp = new StudentUser();
        temp.setPhone(phone);
        temp.setEmail(email);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<StudentUser> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("username",username);
        boolean update = studentUserService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String username, String sex, String phone, String email) {
        StudentUser studentUser = new StudentUser();
        studentUser.setStudentId(String.valueOf(UUID.randomUUID()));
        studentUser.setUsername(username);
        studentUser.setPassword("e10adc3949ba59abbe56e057f20f883e");
        studentUser.setState(0);
        studentUser.setSex(Integer.parseInt(sex));
        studentUser.setAvatar("http://192.168.163.130:9000/edu/wallhaven-zxqj3j.png");
        studentUser.setPhone(phone);
        studentUser.setEmail(email);
        studentUser.setBirth(LocalDateTime.now());
        studentUser.setCreateTime(LocalDateTime.now());
        studentUser.setUpdateTime(LocalDateTime.now());
        boolean save = studentUserService.save(studentUser);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResultO register(String username, String email, String pass) {
        StudentUser studentUser = new StudentUser();
        studentUser.setStudentId(String.valueOf(UUID.randomUUID()));
        studentUser.setUsername(username);
        studentUser.setPassword(pass);
        studentUser.setState(0);
        studentUser.setSex(0);
        studentUser.setAvatar("http://192.168.163.130:9000/edu/wallhaven-zxqj3j.png");
        studentUser.setPhone("请填写电话号码");
        studentUser.setEmail(email);
        studentUser.setBirth(LocalDateTime.now());
        studentUser.setCreateTime(LocalDateTime.now());
        studentUser.setUpdateTime(LocalDateTime.now());
        boolean save = studentUserService.save(studentUser);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

}
