package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.Course;
import org.supcrf.entity.StudentCourse;
import org.supcrf.service.CourseService;
import org.supcrf.service.StudentCourseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-03-02
 */
@RestController
@RequestMapping("/student-course")
public class StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private CourseService courseService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/all")
    public ResultO all(String courseId) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).orderByDesc("id");
        List<StudentCourse> studentCourses = studentCourseService.list(queryWrapper);
        return ResultO.ok(studentCourses);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/studentAll")
    public ResultO studentAll(String courseId, String username, String userId) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).eq("student_name", username).eq("student_id", userId);
        StudentCourse studentCourse = studentCourseService.getOne(queryWrapper);
        return ResultO.ok(studentCourse);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/centerAll")
    public ResultO centerAll(String userId, String userName) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id", userId).eq("student_name", userName).orderByDesc("id");
        List<StudentCourse> studentCourses = studentCourseService.list(queryWrapper);

        List<Course> list = new ArrayList<>();
        for (StudentCourse studentCours : studentCourses) {
            System.out.println(studentCours.getCourseId());
            QueryWrapper<Course> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", studentCours.getCourseId());
            Course course = courseService.getOne(wrapper);
            list.add(course);
        }
        return ResultO.ok(list);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String courseId, String courseName, String username, String userid) {
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(userid);
        studentCourse.setStudentName(username);
        studentCourse.setCourseId(courseId);
        studentCourse.setCourseName(courseName);
        boolean save = studentCourseService.save(studentCourse);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

}
