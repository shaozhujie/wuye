package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.NegativeList;
import com.project.wuye.mapper.NegativeListMapper;
import com.project.wuye.service.NegativeListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 欠费记录service实现类
 * @date 2024/03/08 02:44
 */
@Service
public class NegativeListServiceImpl extends ServiceImpl<NegativeListMapper, NegativeList> implements NegativeListService {
}
