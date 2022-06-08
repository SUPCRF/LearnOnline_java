package org.supcrf.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.supcrf.common.lang.Result;
import org.supcrf.common.lang.ResultO;
import org.supcrf.common.lang.ResultUtil;
import org.supcrf.entity.StudentUser;
import org.supcrf.entity.TeacherUser;
import org.supcrf.service.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * @title: CommonController
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/15/2021 21:51
 */
@RequestMapping("/common")
@RestController
@Api(tags = "公共接口")
public class CommonController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private StudentUserService studentUserService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/ping")
    public ResultO Ping() {
        try {
            return ResultO.ok(isConnect());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultO.error("错误");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @ApiOperation(value = "获取用户信息")
    @PostMapping("/userInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "userType", value = "用户类型", required = false),
            @ApiImplicitParam(dataType = "String", name = "userId", value = "用户Id", required = false),
    })
    public Result userInfo(String userType, String userId) {
        Object user = new Object();
        if (userType.equals("admin")) {
            user = adminUserService.getById(userId);
        }else if (userType.equals("teacher")) {
            user = teacherUserService.getById(userId);
        } else if(userType.equals("student")){
            user = studentUserService.getById(userId);
        }
        return ResultUtil.success(user);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @ApiOperation(value = "获取总条数")
    @PostMapping("/count")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String", name = "type", value = "类型", required = false),
    })
    public Result Count(String type) {
        Integer count = 0;
        if (type.equals("teacher")) {
            count = teacherUserService.count();
        }else if(type.equals("student")) {
            count = studentUserService.count();
        }else if(type.equals("course")){
            count = courseService.count();
        }else if(type.equals("blog")){
            count = blogService.count();
        }
        System.out.println(count);
        return ResultUtil.success(count);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @ApiOperation(value = "获取所以总条数")
    @GetMapping("/counts")
    public Result AllCount() {
        int tcount = teacherUserService.count();
        int scount = studentUserService.count();
        int ccount = courseService.count();
        int bcount = blogService.count();
        return ResultUtil.success(MapUtil.builder()
                .put("teacher",tcount)
                .put("student", scount)
                .put("course", ccount)
                .put("blog", bcount).map());
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfo")
    public ResultO updateInfo(String userId, String userType, String sex, String phone, String email) {
        if (userType.equals("student")) {
            StudentUser temp = new StudentUser();
            QueryWrapper<StudentUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId);
            temp.setSex(Integer.parseInt(sex));
            temp.setPhone(phone);
            temp.setEmail(email);
            temp.setUpdateTime(LocalDateTime.now());
            studentUserService.update(temp, queryWrapper);
            return ResultO.ok("修改成功");
        } else if (userType.equals("teacher")) {
            TeacherUser temp = new TeacherUser();
            QueryWrapper<TeacherUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId);
            temp.setSex(Integer.parseInt(sex));
            temp.setPhone(phone);
            temp.setEmail(email);
            temp.setUpdateTime(LocalDateTime.now());
            teacherUserService.update(temp, queryWrapper);
            return ResultO.ok("修改成功");
        } else {
            return ResultO.error("错误");
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updatePass")
    public ResultO updatePass(String userId, String userType, String oldPass, String newPass) {
        if (userType.equals("student")) {
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
        } else if (userType.equals("teacher")) {
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
        } else {
            return ResultO.error("错误");
        }
    }

    public String isConnect() throws Exception {
        BufferedReader br = null;
        try{
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("ping " + "http://www.baidu.com");
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream(), "GB2312");
            br = new BufferedReader(inputStreamReader);
            String line = null;
            StringBuffer sb = new StringBuffer();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            if(!sb.toString().contains("平均")){
                return "999";
            }
            else{
                return sb.toString().substring(sb.toString().lastIndexOf("平均")+5,sb.length());
            }
        }catch (Exception e){
            throw new Exception();
        }finally {
            if (br != null){
                br.close();
            }
        }
    }
}
