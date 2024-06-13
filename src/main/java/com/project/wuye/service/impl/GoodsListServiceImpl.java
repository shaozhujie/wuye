package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.GoodsList;
import com.project.wuye.mapper.GoodsListMapper;
import com.project.wuye.service.GoodsListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 物品service实现类
 * @date 2024/03/09 08:56
 */
@Service
public class GoodsListServiceImpl extends ServiceImpl<GoodsListMapper, GoodsList> implements GoodsListService {
}
