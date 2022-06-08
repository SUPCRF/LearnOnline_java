package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.*;
import org.supcrf.service.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/exam-content")
@Api(tags = "考试内容模块")
public class ExamContentController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ExamContentService examContentService;

    @Autowired
    private ExamService examService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/all")
    public ResultO all(String courseId) {
        QueryWrapper<ExamContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).orderByDesc("id");
        List<ExamContent> examContents = examContentService.list(queryWrapper);
        return ResultO.ok(examContents);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String coursename, String content, String answer) {
        ExamContent examContent = new ExamContent();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", coursename);
        Course course = courseService.getOne(queryWrapper);
        examContent.setCourseId(course.getCourseId());
        examContent.setContent(content);
        examContent.setAnswer(answer);
        QueryWrapper<Exam> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", course.getCourseId());
        Exam exam = examService.getOne(queryWrapper2);
        System.out.println(course.getCourseId());
        Exam exam1 = new Exam();
        exam1.setExamContent(exam.getExamContent()+1);

        boolean update1 = examService.update(exam1, queryWrapper2);

        boolean save = examContentService.save(examContent);

        if (save || update1) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/saveById")
    public ResultO saveById(String courseId, String content, String answer) {
        ExamContent examContent = new ExamContent();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Course course = courseService.getOne(queryWrapper);
        examContent.setCourseId(course.getCourseId());
        examContent.setContent(content);
        examContent.setAnswer(answer);
        QueryWrapper<Exam> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", course.getCourseId());
        Exam exam = examService.getOne(queryWrapper2);
        System.out.println(course.getCourseId());
        Exam exam1 = new Exam();
        exam1.setExamContent(exam.getExamContent()+1);
        boolean update1 = examService.update(exam1, queryWrapper2);
        boolean save = examContentService.save(examContent);
        if (save || update1) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modifExam")
    public ResultO modifExam(String id, String courseId, String content, String answer) {
        ExamContent temp = new ExamContent();
        temp.setContent(content);
        temp.setAnswer(answer);
        QueryWrapper<ExamContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("course_id", courseId);
        boolean update = examContentService.update(temp, queryWrapper);
        if (update) {
            return ResultO.ok("修改成功");
        }else {
            return ResultO.error("修改失败");
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delExam")
    public ResultO delExam(String id) {
        boolean remove = examContentService.removeById(id);
        if (remove) {
            return ResultO.ok("删除成功");
        }else {
            return ResultO.error("删除失败");
        }
    }

}
