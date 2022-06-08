package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.Exam;
import org.supcrf.entity.StudentCourse;
import org.supcrf.service.ExamService;
import org.supcrf.service.StudentCourseService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 考试信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-27
 */
@RestController
@RequestMapping("/exam")
@Api(tags = "考试模块")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentCourseService studentCourseService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<Exam> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Exam> exams = examService.list(queryWrapper);
        return ResultO.ok(exams);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/centerExam")
    public ResultO centerExam(String userId, String userName) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId).eq("student_name", userName).orderByDesc("id");
        List<StudentCourse> studentCourses = studentCourseService.list(queryWrapper);
        return ResultO.ok(studentCourses);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/start")
    public ResultO start(String courseId) {
        Exam temp = new Exam();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Exam> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_id",courseId);
        boolean update = examService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String courseId) {
        Exam temp = new Exam();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Exam> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_id",courseId);
        boolean update = examService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

}
