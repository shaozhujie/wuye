package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.PostList;
import com.project.wuye.mapper.PostListMapper;
import com.project.wuye.service.PostListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 职位service实现类
 * @date 2024/03/09 09:12
 */
@Service
public class PostListServiceImpl extends ServiceImpl<PostListMapper, PostList> implements PostListService {
}
