package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.Course;
import org.supcrf.entity.Homework;
import org.supcrf.entity.HomeworkContent;
import org.supcrf.service.CourseService;
import org.supcrf.service.HomeworkContentService;
import org.supcrf.service.HomeworkService;

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
@RequestMapping("/homework-content")
@Api(tags = "作业内容模块")
public class HomeworkContentController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private HomeworkContentService homeworkContentService;

    @Autowired
    private HomeworkService homeworkService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/all")
    public ResultO all(String courseId) {
        QueryWrapper<HomeworkContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).orderByDesc("id");
        List<HomeworkContent> homework = homeworkContentService.list(queryWrapper);
        return ResultO.ok(homework);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String coursename, String content, String answer) {
        HomeworkContent homeworkContent = new HomeworkContent();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_name", coursename);
        Course course = courseService.getOne(queryWrapper);
        homeworkContent.setCourseId(course.getCourseId());
        homeworkContent.setContent(content);
        homeworkContent.setAnswer(answer);
        QueryWrapper<Homework> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", course.getCourseId());
        Homework homework = homeworkService.getOne(queryWrapper2);
        System.out.println(course.getCourseId());
        Homework homework1 = new Homework();
        homework1.setCourseContent(homework.getCourseContent()+1);
        boolean update1 = homeworkService.update(homework1, queryWrapper2);
        boolean save = homeworkContentService.save(homeworkContent);
        if (save || update1) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/saveById")
    public ResultO saveById(String courseId, String content, String answer) {
        HomeworkContent homeworkContent = new HomeworkContent();
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Course course = courseService.getOne(queryWrapper);
        homeworkContent.setCourseId(course.getCourseId());
        homeworkContent.setContent(content);
        homeworkContent.setAnswer(answer);
        QueryWrapper<Homework> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("course_id", course.getCourseId());
        Homework homework = homeworkService.getOne(queryWrapper2);
        System.out.println(course.getCourseId());
        Homework homework1 = new Homework();
        homework1.setCourseContent(homework.getCourseContent()+1);
        boolean update1 = homeworkService.update(homework1, queryWrapper2);
        boolean save = homeworkContentService.save(homeworkContent);
        if (save || update1) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modifHomework")
    public ResultO modifHomework(String id, String courseId, String content, String answer) {
        HomeworkContent temp = new HomeworkContent();
        temp.setContent(content);
        temp.setAnswer(answer);
        QueryWrapper<HomeworkContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).eq("course_id", courseId);
        boolean update = homeworkContentService.update(temp, queryWrapper);
        if (update) {
            return ResultO.ok("修改成功");
        }else {
            return ResultO.error("修改失败");
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delHomework")
    public ResultO delHomework(String id) {
        boolean remove = homeworkContentService.removeById(id);
        if (remove) {
            return ResultO.ok("删除成功");
        }else {
            return ResultO.error("删除失败");
        }
    }

}
