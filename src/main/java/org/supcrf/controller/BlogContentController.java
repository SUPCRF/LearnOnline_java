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
import org.supcrf.service.*;

import java.time.LocalDateTime;
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
@RequestMapping("/blog-content")
public class BlogContentController {

    @Autowired
    private BlogContentService blogContentService;

    @CrossOrigin
    @PostMapping("/all")
    public ResultO all(String blogId) {
        System.out.println(blogId);
        QueryWrapper<BlogContent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId).orderByDesc("id");
        List<BlogContent> blogContents = blogContentService.list(queryWrapper);
        return ResultO.ok(blogContents);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String blogId, String username, String content) {
        BlogContent blogContent = new BlogContent();
        blogContent.setBlogId(blogId);
        blogContent.setUsername(username);
        blogContent.setContent(content);
        blogContent.setCreateTime(LocalDateTime.now());
        boolean save = blogContentService.save(blogContent);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

}
