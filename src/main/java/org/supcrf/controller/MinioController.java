package org.supcrf.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.supcrf.common.lang.Result;
import org.supcrf.common.lang.ResultUtil;
import org.supcrf.config.MinioConfig;
import org.supcrf.entity.AdminUser;
import org.supcrf.entity.StudentUser;
import org.supcrf.entity.TeacherUser;
import org.supcrf.service.AdminUserService;
import org.supcrf.service.MinioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.supcrf.service.StudentUserService;
import org.supcrf.service.TeacherUserService;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @title: MinioController
 * @projectName learn-online_java
 * @description: TODO
 * @Author supcrf
 * @Version 1.0
 * @Date 2/15/2021 19:18
 */
@RequestMapping("/minio")
@RestController
@Api(tags = "文件上传接口")
public class MinioController {

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
    @ApiOperation(value = "使用minio文件上传")
    @PostMapping("/uploadFile")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "MultipartFile", name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称", required = false)
    })
    public Result uploadFile(MultipartFile file, String bucketName) {
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
            return ResultUtil.success(minioService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.sendErrorMessage("上传失败");
        }
    }

    public Result uploadIputSreamFile(MultipartFile file, String bucketName) {
        try {
            bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
            if (!minioService.bucketExists(bucketName)) {
                minioService.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();
            minioService.putObject(bucketName, objectName, inputStream, MimeTypeUtils.IMAGE_JPEG_VALUE);
            inputStream.close();
            return ResultUtil.success(minioService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.sendErrorMessage("上传失败");
        }
    }

    public String uploadvideo(MultipartFile file, String bucketName) {
        try {
            bucketName = StringUtils.isNotBlank(bucketName) ? bucketName : minioConfig.getBucketName();
            if (!minioService.bucketExists(bucketName)) {
                minioService.makeBucket(bucketName);
            }
            String fileName = file.getOriginalFilename();
            String objectName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + UUID.randomUUID().toString().replaceAll("-", "")
                    + fileName.substring(fileName.lastIndexOf("."));

            InputStream inputStream = file.getInputStream();
            minioService.putObject(bucketName, objectName, inputStream, MimeTypeUtils.IMAGE_JPEG_VALUE);
            inputStream.close();
            return minioService.getObjectUrl(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
            return "上传失败";
        }
    }

    @CrossOrigin
    @RequiresAuthentication //登录才可访问
    @ApiOperation(value = "使用minio文件上传头像")
    @PostMapping("/changeAvatar")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "MultipartFile", name = "file", value = "上传的文件", required = true),
            @ApiImplicitParam(dataType = "String", name = "bucketName", value = "对象存储桶名称", required = false),
            @ApiImplicitParam(dataType = "String", name = "userType", value = "用户类型", required = false),
            @ApiImplicitParam(dataType = "String", name = "userId", value = "用户Id", required = false),
    })
    public Result changeAvatar(MultipartFile file, String bucketName, String userType, String userId) {
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
            if (userType.equals("admin")) {
                QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("id",userId);
                AdminUser temp = new AdminUser();
                temp.setAvatar(minioUrl);
                adminUserService.update(temp, queryWrapper);
            }else if (userType.equals("teacher")) {
                QueryWrapper<TeacherUser> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("id",userId);
                TeacherUser temp = new TeacherUser();
                temp.setAvatar(minioUrl);
                teacherUserService.update(temp, queryWrapper1);
            } else {
                QueryWrapper<StudentUser> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("id",userId);
                StudentUser temp = new StudentUser();
                temp.setAvatar(minioUrl);
                studentUserService.update(temp, queryWrapper2);
            }
            return ResultUtil.success(minioService.getObjectUrl(bucketName, objectName));
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.sendErrorMessage("上传失败");
        }
    }
}