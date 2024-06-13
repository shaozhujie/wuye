package com.project.wuye.controller.environment;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.EnvironmentList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.EnvironmentListService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 环境controller
 * @date 2024/03/08 04:30
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class EnvironmentListController {

    @Autowired
    private EnvironmentListService environmentListService;

    /** 分页获取环境 */
    @PostMapping("getEnvironmentListPage")
    public Result getEnvironmentListPage(@RequestBody EnvironmentList environmentList) {
        Page<EnvironmentList> page = new Page<>(environmentList.getPageNumber(),environmentList.getPageSize());
        QueryWrapper<EnvironmentList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(environmentList.getName()),EnvironmentList::getName,environmentList.getName())
                .eq(StringUtils.isNotBlank(environmentList.getName()),EnvironmentList::getName,environmentList.getName())
                .eq(StringUtils.isNotBlank(environmentList.getSecurity()),EnvironmentList::getSecurity,environmentList.getSecurity())
                .eq(StringUtils.isNotBlank(environmentList.getRemark()),EnvironmentList::getRemark,environmentList.getRemark())
                .eq(StringUtils.isNotBlank(environmentList.getCreateBy()),EnvironmentList::getCreateBy,environmentList.getCreateBy())
                .eq(environmentList.getCreateTime() != null,EnvironmentList::getCreateTime,environmentList.getCreateTime())
                .eq(StringUtils.isNotBlank(environmentList.getUpdateBy()),EnvironmentList::getUpdateBy,environmentList.getUpdateBy())
                .eq(environmentList.getUpdateTime() != null,EnvironmentList::getUpdateTime,environmentList.getUpdateTime());
        Page<EnvironmentList> environmentListPage = environmentListService.page(page, queryWrapper);
        return Result.success(environmentListPage);
    }

    /** 根据id获取环境 */
    @GetMapping("getEnvironmentListById")
    public Result getEnvironmentListById(@RequestParam("id")String id) {
        EnvironmentList environmentList = environmentListService.getById(id);
        return Result.success(environmentList);
    }

    /** 保存环境 */
    @PostMapping("saveEnvironmentList")
    public Result saveEnvironmentList(@RequestBody EnvironmentList environmentList) {
        boolean save = environmentListService.save(environmentList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑环境 */
    @PostMapping("editEnvironmentList")
    public Result editEnvironmentList(@RequestBody EnvironmentList environmentList) {
        boolean save = environmentListService.updateById(environmentList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除环境 */
    @GetMapping("removeEnvironmentList")
    public Result removeEnvironmentList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                environmentListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("环境id不能为空！");
        }
    }

}
