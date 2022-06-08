package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.*;
import org.supcrf.service.HomeworkAnswerService;
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
@RequestMapping("/homework-answer")
public class HomeworkAnswerController {

    @Autowired
    private HomeworkAnswerService homeworkAnswerService;

    @Autowired
    private StudentCourseService studentCourseService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String courseId, String userId, String userName, String content, String answer, String myAnswer) {
        HomeworkAnswer temp = new HomeworkAnswer();
        temp.setCourseId(courseId);
        temp.setUserId(userId);
        temp.setUsername(userName);
        temp.setContent(content);
        temp.setAnswer(answer);
        temp.setMyAnswer(myAnswer);
        boolean save = homeworkAnswerService.save(temp);

        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setHomework(1);
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
        QueryWrapper<HomeworkAnswer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("username", userName).eq("course_id", courseId);
        List<HomeworkAnswer> homeworkAnswers = homeworkAnswerService.list(queryWrapper);
        return ResultO.ok(homeworkAnswers);
    }

}
