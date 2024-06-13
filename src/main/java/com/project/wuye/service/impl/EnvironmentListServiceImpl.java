package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.EnvironmentList;
import com.project.wuye.mapper.EnvironmentListMapper;
import com.project.wuye.service.EnvironmentListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 环境service实现类
 * @date 2024/03/08 04:30
 */
@Service
public class EnvironmentListServiceImpl extends ServiceImpl<EnvironmentListMapper, EnvironmentList> implements EnvironmentListService {
}