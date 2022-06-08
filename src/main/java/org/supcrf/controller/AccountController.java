package org.supcrf.controller;
import java.time.LocalDateTime;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.supcrf.common.dto.LoginDto;
import org.supcrf.common.dto.RegisterDto;
import org.supcrf.common.dto.SignDto;
import org.supcrf.common.lang.ResultO;
import org.supcrf.common.util.JwtUtils;
import org.supcrf.entity.AdminUser;
import org.supcrf.entity.StudentUser;
import org.supcrf.entity.TeacherUser;
import org.supcrf.service.AdminUserService;
import org.supcrf.service.StudentUserService;
import org.supcrf.service.TeacherUserService;

import javax.servlet.http.HttpServletResponse;

/**
 * @title: AccountController
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/13/2021 21:07
 */
@RequestMapping("/login")
@RestController
@Api(tags = "登录模块")
public class AccountController {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private StudentUserService studentUserService;

    @CrossOrigin
    @PostMapping("/admin")
    @ApiOperation("管理员登录接口")
    public ResultO admin(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        AdminUser user = adminUserService.getOne(new QueryWrapper<AdminUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return ResultO.error("密码错误！");
        }
        System.out.println(user.getState());
        if (user.getState().equals(1)) {
            return ResultO.error("账户以停用");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return ResultO.ok(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("sex", user.getSex())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .put("phone", user.getPhone())
                .put("birth", user.getBirth())
                .put("token", jwt)
                .map()
        );
    }

    @CrossOrigin
    @PostMapping("/teacher")
    @ApiOperation("教师登录接口")
    public ResultO teacher(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        TeacherUser user = teacherUserService.getOne(new QueryWrapper<TeacherUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return ResultO.error("密码错误！");
        }
        if (!user.getState().equals(1)) {
            return ResultO.error("账户以停用");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return ResultO.ok(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @CrossOrigin
    @PostMapping("/student")
    @ApiOperation("学生登录接口")
    public ResultO student(@Validated @RequestBody LoginDto loginDto, HttpServletResponse response) {
        StudentUser user = studentUserService.getOne(new QueryWrapper<StudentUser>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return ResultO.error("密码错误！");
        }
        if (!user.getState().equals(1)) {
            return ResultO.error("账户以停用");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return ResultO.ok(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("avatar", user.getAvatar())
                .put("email", user.getEmail())
                .map()
        );
    }

    @CrossOrigin
    @PostMapping("/sign")
    @ApiOperation("通用登录接口")
    public ResultO sign(@Validated @RequestBody SignDto signDto, HttpServletResponse response) {
        if(signDto.getType().equals("1")){
            StudentUser studentUser = studentUserService.getOne(new QueryWrapper<StudentUser>().eq("username", signDto.getUsername()));
            Assert.notNull(studentUser, "用户不存在");
            if(!studentUser.getPassword().equals(SecureUtil.md5(signDto.getPassword()))) {
                return ResultO.error("密码错误！");
            }
            if (studentUser.getState().equals(1)) {
                return ResultO.error("账户以停用");
            }
            String jwt = jwtUtils.generateToken(studentUser.getId());
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            // 用户可以另一个接口
            return ResultO.ok(MapUtil.builder()
                    .put("id", studentUser.getId())
                    .put("userid", studentUser.getStudentId())
                    .put("username", studentUser.getUsername())
                    .put("avatar", studentUser.getAvatar())
                    .put("email", studentUser.getEmail())
                    .put("token", jwt)
                    .map()
            );
        } else if (signDto.getType().equals("2")) {
            TeacherUser teacherUser = teacherUserService.getOne(new QueryWrapper<TeacherUser>().eq("username", signDto.getUsername()));
            Assert.notNull(teacherUser, "用户不存在");
            if(!teacherUser.getPassword().equals(SecureUtil.md5(signDto.getPassword()))) {
                return ResultO.error("密码错误！");
            }
            if (teacherUser.getState().equals(1)) {
                return ResultO.error("账户以停用");
            }
            String jwt = jwtUtils.generateToken(teacherUser.getId());
            response.setHeader("Authorization", jwt);
            response.setHeader("Access-Control-Expose-Headers", "Authorization");
            // 用户可以另一个接口
            return ResultO.ok(MapUtil.builder()
                    .put("id", teacherUser.getId())
                    .put("userid", teacherUser.getTeacherId())
                    .put("username", teacherUser.getUsername())
                    .put("avatar", teacherUser.getAvatar())
                    .put("email", teacherUser.getEmail())
                    .put("token", jwt)
                    .map()
            );
        }else {
            return ResultO.error("错误");
        }
    }

    @CrossOrigin
    @PostMapping("/register")
    @ApiOperation("注册接口")
    public ResultO register(@Validated @RequestBody RegisterDto registerDto, HttpServletResponse response) {
        StudentUser studentUser = new StudentUser();
        studentUser.setStudentId(String.valueOf(UUID.randomUUID()));
        studentUser.setUsername(registerDto.getUsername());
        studentUser.setPassword(registerDto.getPassword());
        studentUser.setState(0);
        studentUser.setSex(0);
        studentUser.setAvatar("http://192.168.163.130:9000/edu/wallhaven-zxqj3j.png");
        studentUser.setPhone("请填写电话号码");
        studentUser.setEmail(registerDto.getEmail());
        studentUser.setBirth(LocalDateTime.now());
        studentUser.setCreateTime(LocalDateTime.now());
        studentUser.setUpdateTime(LocalDateTime.now());
        boolean flag = studentUserService.save(studentUser);
        if (flag) {
            return ResultO.ok("成功");
        }else {
            return ResultO.error("错误");
        }
    }

    @CrossOrigin
    @PostMapping("/timeout")
    @ApiOperation("获取延时接口")
    public ResultO timeout() {
        return ResultO.ok("ok");
    }

    @CrossOrigin
    @RequiresAuthentication
    @GetMapping("/logout")
    @ApiOperation("登录退出接口")
    public ResultO logout() {
        SecurityUtils.getSubject().logout();
        return ResultO.ok(null);
    }
}