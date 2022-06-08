package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.ExamAnswer;
import org.supcrf.entity.HomeworkAnswer;
import org.supcrf.entity.StudentCourse;
import org.supcrf.service.ExamAnswerService;
import org.supcrf.service.StudentCourseService;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-03-04
 */
@RestController
@RequestMapping("/exam-answer")
public class ExamAnswerController {

    @Autowired
    private ExamAnswerService examAnswerService;

    @Autowired
    private StudentCourseService studentCourseService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String courseId, String userId, String userName, String content, String answer, String myAnswer) {
        ExamAnswer temp = new ExamAnswer();
        temp.setCourseId(courseId);
        temp.setUserId(userId);
        temp.setUsername(userName);
        temp.setContent(content);
        temp.setAnswer(answer);
        temp.setMyAnswer(myAnswer);
        boolean save = examAnswerService.save(temp);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setExam(1);
        studentCourse.setGrades(-1);
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).eq("student_id", userId).eq("student_name", userName);
        boolean update = studentCourseService.update(studentCourse, queryWrapper);

        if (save && update) {
            return ResultO.ok("ok");
        } else {
            return ResultO.error("error");
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/selectAnswer")
    public ResultO selectAnswer(String courseId, String userId, String userName) {
        QueryWrapper<ExamAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("username", userName).eq("course_id", courseId);
        List<ExamAnswer> examAnswers = examAnswerService.list(queryWrapper);
        return ResultO.ok(examAnswers);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/remove")
    public ResultO remove(String courseId, String userId, String userName) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId).eq("student_name", userName).eq("course_id", courseId);
        StudentCourse temp = new StudentCourse();
        temp.setExam(0);
        boolean update = studentCourseService.update(temp, queryWrapper);
        if (update) {
            return ResultO.ok("success");
        } else  {
            return ResultO.error("error");
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/saveGrades")
    public ResultO saveGrades(String courseId, String userId, String userName, String grades) {
        StudentCourse temp = new StudentCourse();
        temp.setGrades(Integer.parseInt(grades));

        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId).eq("student_name", userName).eq("course_id", courseId);
        boolean update = studentCourseService.update(temp, queryWrapper);
        if (update) {
            return ResultO.ok("success");
        } else {
            return ResultO.error("error");
        }
    }

}
