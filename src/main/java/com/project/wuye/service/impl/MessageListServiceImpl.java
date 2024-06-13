package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.MessageList;
import com.project.wuye.mapper.MessageListMapper;
import com.project.wuye.service.MessageListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 消息service实现类
 * @date 2024/03/09 09:36
 */
@Service
public class MessageListServiceImpl extends ServiceImpl<MessageListMapper, MessageList> implements MessageListService {
}
