package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.HousesList;
import com.project.wuye.mapper.HousesListMapper;
import com.project.wuye.service.HousesListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 房屋service实现类
 * @date 2024/03/08 03:49
 */
@Service
public class HousesListServiceImpl extends ServiceImpl<HousesListMapper, HousesList> implements HousesListService {
}
