package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.ChargeType;
import com.project.wuye.mapper.ChargeTypeMapper;
import com.project.wuye.service.ChargeTypeService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 收费类型service实现类
 * @date 2024/03/08 11:31
 */
@Service
public class ChargeTypeServiceImpl extends ServiceImpl<ChargeTypeMapper, ChargeType> implements ChargeTypeService {
}