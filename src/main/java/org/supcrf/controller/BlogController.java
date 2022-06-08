package org.supcrf.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.supcrf.common.lang.ResultO;
import org.supcrf.common.util.ShiroUtil;
import org.supcrf.config.MinioConfig;
import org.supcrf.entity.*;
import org.supcrf.service.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 博客信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/blog")
@Api(tags = "博客模块")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TeacherUserService teacherUserService;

    @Autowired
    private StudentUserService studentUserService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Blog> blogs = blogService.list(queryWrapper);
        return ResultO.ok(blogs);
    }

    @CrossOrigin
    @GetMapping("/clientAll")
    public ResultO clientAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0).orderByDesc("id");
        List<Blog> blogs = blogService.list(queryWrapper);
        return ResultO.ok(blogs);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/blogTypeAll")
    public ResultO blogTypeAll(String userType,String userid,String username) {
        System.out.println(userType);
        System.out.println(userid);
        System.out.println(username);
        if(userType.equals("student")) {
            QueryWrapper<Blog> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id", userid).orderByDesc("id");
            List<Blog> blogs1 = blogService.list(queryWrapper1);
            return ResultO.ok(blogs1);
        }else if(userType.equals("teacher")) {
            QueryWrapper<Blog> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_id", userid).orderByDesc("id");
            List<Blog> blogs2 = blogService.list(queryWrapper2);
            return ResultO.ok(blogs2);
        } else {
            return ResultO.error("错误");
        }
    }

    @CrossOrigin
    @GetMapping("/client")
    public ResultO client() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0).orderByDesc("id").last("limit 6");
        List<Blog> blogs = blogService.list(queryWrapper);
        return ResultO.ok(blogs);
    }

    @CrossOrigin
    @PostMapping("/blogMd")
    public ResultO blogMd(String blogId) {
        System.out.println(blogId);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        Blog blog = blogService.getOne(queryWrapper);
        return ResultO.ok(MapUtil.builder()
                .put("blogname", blog.getBlogName())
                .put("blogmd", blog.getMd())
                .map());
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/blogInfos")
    public ResultO blogInfos(String blogId) {
        System.out.println(blogId);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        Blog blog = blogService.getOne(queryWrapper);
        return ResultO.ok(blog);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/passall")
    public ResultO passAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0);
        List<Blog> blogList = blogService.list(queryWrapper);
        return ResultO.ok(blogList);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/startall")
    public ResultO startAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", -1);
        List<Blog> blogList = blogService.list(queryWrapper);
        return ResultO.ok(blogList);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/unpassall")
    public ResultO unPassAll() {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 1);
        List<Blog> blogList = blogService.list(queryWrapper);
        return ResultO.ok(blogList);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/pass")
    public ResultO pass(String blogId) {
        Blog temp = new Blog();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Blog> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("blog_id",blogId);
        boolean update = blogService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/unpass")
    public ResultO unPass(String blogId) {
        Blog temp = new Blog();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Blog> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("blog_id",blogId);
        boolean update = blogService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @GetMapping("/blogs")
    public ResultO blogs(Integer currentPage) {
        if(currentPage == null || currentPage < 1) currentPage = 1;
        Page page = new Page(currentPage, 5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return ResultO.ok(pageData);
    }

    @CrossOrigin
    @GetMapping("/blog/{id}")
    public ResultO detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已删除！");
        return ResultO.ok(blog);
    }

    @CrossOrigin
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public ResultO edit(@Validated @RequestBody Blog blog) {
        System.out.println(blog.toString());
        Blog temp = null;
        if(blog.getId() != null) {
            temp = blogService.getById(blog.getId());
            Assert.isTrue(Long.valueOf(temp.getUserId()) == ShiroUtil.getProfile().getId(), "没有权限编辑");
        } else {
            temp = new Blog();
            temp.setUserId(String.valueOf(ShiroUtil.getProfile().getId().intValue()));
            temp.setCreateTime(LocalDateTime.now());
            temp.setState(0);
        }
        BeanUtil.copyProperties(blog, temp, "id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return ResultO.ok("操作成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String blogId) {
        UpdateWrapper<Blog> blogUpdateWrapper = new UpdateWrapper<>();
        blogUpdateWrapper.eq("blog_id", blogId);
        boolean remove = blogService.remove(blogUpdateWrapper);
        if (remove) {
            return ResultO.ok("删除成功");
        }
        return ResultO.error("删除失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/blogInfo")
    public ResultO blogInfo(String blogId) {
        Blog blog = blogService.getById(blogId);
        return ResultO.ok(blog.getMd());
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/editBlog")
    public ResultO editBlog(String blogId, String blogname, String title, String md, String html) {
        Blog temp = new Blog();
        temp.setBlogName(blogname);
        temp.setTitle(title);
        temp.setMd(md);
        temp.setHtml(html);
        temp.setUpdateTime(LocalDateTime.now());

        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        boolean update = blogService.update(temp, queryWrapper);
        if (update) {
            return ResultO.ok("编辑成功");
        }
        return ResultO.error("编辑失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String blogname, String userid, String usertype, MultipartFile blogpic , String title, String md, String html) {
        String uploadUrl = uploadFile(blogpic, "edu");
        String username = "";
        if (usertype.equals("admin")) {
            AdminUser adminUser = adminUserService.getById(userid);
            username = adminUser.getUsername();
        } else if (usertype.equals("teacher")){
            TeacherUser teacherUser = teacherUserService.getById(userid);
            username = teacherUser.getUsername();
        } else if (usertype.equals("student")){
            StudentUser studentUser = studentUserService.getById(userid);
            username = studentUser.getUsername();
        }else {
            return ResultO.error("错误");
        }
        Blog temp = new Blog();
        temp.setBlogId(String.valueOf(UUID.randomUUID()));
        temp.setUserId(userid);
        temp.setUserName(username);
        temp.setBlogName(blogname);
        temp.setBlogUrl(uploadUrl);
        temp.setState(-1);
        temp.setBlogPic(uploadUrl);
        temp.setTitle(title);
        temp.setCreateTime(LocalDateTime.now());
        temp.setUpdateTime(LocalDateTime.now());
        temp.setMd(md);
        temp.setHtml(html);
        boolean save = blogService.save(temp);
        if (save) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    public String uploadFile(MultipartFile file, String bucketName) {
        try {
            bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
            if (!minioService.bucketExists(bucketName)) {
                minioService.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));
            minioService.putObject(bucketName, file, objectName);
            String minioUrl = minioService.getObjectUrl(bucketName, objectName);
            System.out.println(minioUrl);
            return minioService.getObjectUrl(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }
}