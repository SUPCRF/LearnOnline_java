package org.supcrf.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.supcrf.common.lang.ResultO;
import org.supcrf.entity.Message;
import org.supcrf.service.MessageService;

/**
 * <p>
 * 留言 前端控制器
 * </p>
 *
 * @author supcrf
 * @since 2021-02-28
 */
@RestController
@RequestMapping("/message")
@Api(tags = "留言模块")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @CrossOrigin
    @PostMapping("/index")
    public ResultO message(String email, String content) {
        Message message = new Message();
        message.setEmail(email);
        message.setContent(content);
        boolean save = messageService.save(message);
        if (save) {
            return ResultO.ok("ok");
        }
        return ResultO.error("error");
    }

}
