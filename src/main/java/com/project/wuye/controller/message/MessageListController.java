package com.project.wuye.controller.message;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.MessageList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.MessageListService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 消息controller
 * @date 2024/03/09 09:36
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class MessageListController {

    @Autowired
    private MessageListService messageListService;

    /** 分页获取消息 */
    @PostMapping("getMessageListPage")
    public Result getMessageListPage(@RequestBody MessageList messageList) {
        Page<MessageList> page = new Page<>(messageList.getPageNumber(),messageList.getPageSize());
        QueryWrapper<MessageList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(messageList.getContent()),MessageList::getContent,messageList.getContent());
        Page<MessageList> messageListPage = messageListService.page(page, queryWrapper);
        return Result.success(messageListPage);
    }

    /** 根据id获取消息 */
    @GetMapping("getMessageListById")
    public Result getMessageListById(@RequestParam("id")String id) {
        MessageList messageList = messageListService.getById(id);
        return Result.success(messageList);
    }

    /** 保存消息 */
    @PostMapping("saveMessageList")
    public Result saveMessageList(@RequestBody MessageList messageList) {
        boolean save = messageListService.save(messageList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑消息 */
    @PostMapping("editMessageList")
    public Result editMessageList(@RequestBody MessageList messageList) {
        boolean save = messageListService.updateById(messageList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除消息 */
    @GetMapping("removeMessageList")
    public Result removeMessageList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                messageListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("消息id不能为空！");
        }
    }

}
