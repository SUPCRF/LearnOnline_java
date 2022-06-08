package org.supcrf.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.CourseContent;
import org.supcrf.service.CourseContentService;

import java.time.LocalDateTime;
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
@RequestMapping("/course-content")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;

    @CrossOrigin
    @PostMapping("/all")
    public ResultO all(String courseId) {
        System.out.println(courseId);
        QueryWrapper<CourseContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId).orderByDesc("id");
        List<CourseContent> courseContents = courseContentService.list(queryWrapper);
        return ResultO.ok(courseContents);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String courseId, String username, String content) {
        CourseContent courseContent = new CourseContent();
        courseContent.setCourseId(courseId);
        courseContent.setUsername(username);
        courseContent.setContent(content);
        courseContent.setCreateTime(LocalDateTime.now());
        boolean save = courseContentService.save(courseContent);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }
}
