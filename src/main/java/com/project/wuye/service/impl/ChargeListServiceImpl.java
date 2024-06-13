package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.ChargeList;
import com.project.wuye.mapper.ChargeListMapper;
import com.project.wuye.service.ChargeListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 收费service实现类
 * @date 2024/03/08 02:17
 */
@Service
public class ChargeListServiceImpl extends ServiceImpl<ChargeListMapper, ChargeList> implements ChargeListService {
}
