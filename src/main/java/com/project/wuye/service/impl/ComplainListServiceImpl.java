package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.ComplainList;
import com.project.wuye.mapper.ComplainListMapper;
import com.project.wuye.service.ComplainListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 投诉service实现类
 * @date 2024/03/09 10:19
 */
@Service
public class ComplainListServiceImpl extends ServiceImpl<ComplainListMapper, ComplainList> implements ComplainListService {
}