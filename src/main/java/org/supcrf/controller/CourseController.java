package org.supcrf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.supcrf.common.lang.ResultO;
import org.supcrf.config.MinioConfig;
import org.supcrf.entity.*;
import org.supcrf.service.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 课程信息 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-06
 */
@RestController
@RequestMapping("/course")
@Api(tags = "课程模块")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private HomeworkService homeworkService;

    @Autowired
    private ExamService examService;

    @Autowired
    private MinioService minioService;

    @Autowired
    private MinioConfig minioConfig;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private TeacherUserService teacherUserService;

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @GetMapping("/all")
    public ResultO all() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0).orderByDesc("id");
        List<Course> courses = courseService.list(queryWrapper);
        return ResultO.ok(courses);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/centerAll")
    public ResultO centerAll(String userId, String userType) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", 0).eq("user_id", userId).eq("user_type", userType).orderByDesc("id");
        List<Course> courses = courseService.list(queryWrapper);
        return ResultO.ok(courses);
    }

    @CrossOrigin
    @PostMapping("/courseInfo")
    public ResultO courseInfo(String courseId) {
        System.out.println(courseId);
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        Course course = courseService.getOne(queryWrapper);
        return ResultO.ok(course);
    }

    @CrossOrigin
    @GetMapping("/client")
    public ResultO client() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id").last("limit 8");
        List<Course> courses = courseService.list(queryWrapper);
        return ResultO.ok(courses);
    }

    @CrossOrigin
    @GetMapping("/clientAll")
    public ResultO clientAll() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Course> courses = courseService.list(queryWrapper);
        return ResultO.ok(courses);
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfo")
    public ResultO updateInfo(String courseId, String title) {
        Course temp = new Course();
        temp.setTitle(title);
        temp.setUpdateTime(LocalDateTime.now());
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",courseId);
        courseService.update(temp, queryWrapper);
        return ResultO.ok("修改成功");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/start")
    public ResultO start(String coursename) {
        Course temp = new Course();
        temp.setState(0);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Course> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_name",coursename);
        boolean update = courseService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/delete")
    public ResultO delete(String coursename) {
        Course temp = new Course();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Course> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_name",coursename);
        boolean update = courseService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/deleteById")
    public ResultO deleteById(String courseid) {
        System.out.println(courseid);
        Course temp = new Course();
        temp.setState(1);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Course> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_id",courseid);
        boolean update = courseService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/modif")
    public ResultO modif(String coursename, String title) {
        Course temp = new Course();
        temp.setTitle(title);
        temp.setUpdateTime(LocalDateTime.now());
        UpdateWrapper<Course> UpdateWrapper = new UpdateWrapper<>();
        UpdateWrapper.eq("course_name",coursename);
        boolean update = courseService.update(temp, UpdateWrapper);
        if (update) {
            return ResultO.ok("ok");
        }
        return ResultO.error("err");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/save")
    public ResultO save(String coursename, String userid, String usertype, MultipartFile video, MultipartFile coursepic,String title) {
        String uploadvideo = uploadFile(video, "edu");
        String uploadcoursepic = uploadFile(coursepic, "edu");
        String username = "";
        if (usertype.equals("admin")) {
            AdminUser adminUser = adminUserService.getById(userid);
            username = adminUser.getUsername();

        } else if (usertype.equals("teacher")){
            TeacherUser teacherUser = teacherUserService.getById(userid);
            username = teacherUser.getUsername();
        } else {
            return ResultO.error("错误");
        }
        Course course = new Course();
        course.setUserType(usertype);
        course.setUserName(username);
        course.setCourseId(String.valueOf(UUID.randomUUID()));
        course.setUserId(userid);
        course.setCourseName(coursename);
        course.setCourseUrl(uploadvideo);
        course.setState(0);
        course.setCoursePic(uploadcoursepic);
        course.setTitle(title);
        course.setCreateTime(LocalDateTime.now());
        course.setUpdateTime(LocalDateTime.now());

        Homework homework = new Homework();
        homework.setHomeworkId(String.valueOf(UUID.randomUUID()));
        homework.setCourseId(course.getCourseId());
        homework.setHomeworkName(course.getCourseName());
        homework.setCourseContent(0);
        homework.setState(0);
        homework.setTitle(course.getTitle());
        homework.setCreateTime(LocalDateTime.now());
        homework.setUpdateTime(LocalDateTime.now());

        Exam exam = new Exam();
        exam.setExamId(String.valueOf(UUID.randomUUID()));
        exam.setCourseId(course.getCourseId());
        exam.setExamName(course.getCourseName());
        exam.setExamContent(0);
        exam.setState(0);
        exam.setTitle(course.getTitle());
        exam.setCreateTime(LocalDateTime.now());
        exam.setUpdateTime(LocalDateTime.now());

        boolean save2 = examService.save(exam);
        boolean save1 = homeworkService.save(homework);
        boolean save = courseService.save(course);
        if (save && save1 && save2) {
            return ResultO.ok("添加成功");
        }
        return ResultO.error("添加失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/updateInfoTo")
    public ResultO updateInfoTo(String coursename,String courseid, String userid, String usertype, String title) {

        System.out.println(coursename);
        System.out.println(courseid);
        System.out.println(userid);
        System.out.println(usertype);
        System.out.println(title);

        Course temp1 = new Course();
        temp1.setCourseName(coursename);
        temp1.setTitle(title);
        temp1.setUpdateTime(LocalDateTime.now());
        QueryWrapper<Course> queryWrapperCourse = new QueryWrapper<>();
        queryWrapperCourse.eq("course_id", courseid).eq("user_id",userid).eq("user_type", usertype);
        boolean update = courseService.update(temp1, queryWrapperCourse);

        Homework temp2 = new Homework();
        temp2.setHomeworkName(temp1.getCourseName());
        temp2.setTitle(temp1.getTitle());
        QueryWrapper<Homework> queryWrapperHomework = new QueryWrapper<>();
        queryWrapperHomework.eq("course_id", courseid);
        boolean update1 = homeworkService.update(temp2, queryWrapperHomework);

        Exam temp3 = new Exam();
        temp3.setExamName(temp1.getCourseName());
        temp3.setTitle(temp1.getTitle());
        QueryWrapper<Exam> queryWrapperExam = new QueryWrapper<>();
        queryWrapperExam.eq("course_id", courseid);
        boolean update2 = examService.update(temp3, queryWrapperExam);

        if (update && update1 && update2) {
            return ResultO.ok("修改成功");
        }
        return ResultO.error("修改失败");
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @PostMapping("/upload")
    public ResultO uploads(MultipartFile video) {
        String uploadvideo = uploadFile(video, "edu");
        return ResultO.ok(uploadvideo);
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

    public static String videoImage(String filePath, String dir) throws Exception {
        String pngPath = "";
        FFmpegFrameGrabber ff = FFmpegFrameGrabber.createDefault(new File(filePath));

        ff.start();
        int ffLength = ff.getLengthInFrames();
        Frame f;
        int i = 0;
        while (i < ffLength) {
            f = ff.grabFrame();
            //截取第6帧
            if ((i > 5) && (f.image != null)) {
                //生成图片的相对路径 例如：pic/uuid.png
                pngPath = getPngPath();
                //执行截图并放入指定位置
                System.out.println("存储图片 ： " + (dir + pngPath));
                doExecuteFrame(f, dir + pngPath);
                break;
            }
            i++;
        }
        ff.stop();

        return pngPath;
    }

    /**
     * 生成图片的相对路径
     *
     * @return 图片的相对路径 例：pic/1.png
     */
    private static String getPngPath() {
        return getUUID() + ".png";
    }


    /**
     * 生成唯一的uuid
     *
     * @return uuid
     */
    private static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 截取缩略图
     *
     * @param f Frame
     * @param targerFilePath:封面图片存放路径
     */
    private static void doExecuteFrame(Frame f, String targerFilePath) {
        String imagemat = "png";
        if (null == f || null == f.image) {
            return;
        }
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bi = converter.getBufferedImage(f);
        File output = new File(targerFilePath);
        try {
            ImageIO.write(bi, imagemat, output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
