package com.project.wuye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.project.wuye.domain.RepairList;
import com.project.wuye.mapper.RepairListMapper;
import com.project.wuye.service.RepairListService;
import org.springframework.stereotype.Service;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 报修service实现类
 * @date 2024/03/08 05:09
 */
@Service
public class RepairListServiceImpl extends ServiceImpl<RepairListMapper, RepairList> implements RepairListService {
}
