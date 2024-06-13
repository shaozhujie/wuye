package com.project.wuye.controller.region;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.RegionList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.RegionListService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 区域controller
 * @date 2024/03/09 09:19
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class RegionListController {

    @Autowired
    private RegionListService regionListService;

    /** 分页获取区域 */
    @PostMapping("getRegionListPage")
    public Result getRegionListPage(@RequestBody RegionList regionList) {
        Page<RegionList> page = new Page<>(regionList.getPageNumber(),regionList.getPageSize());
        QueryWrapper<RegionList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(regionList.getName()),RegionList::getName,regionList.getName());
        Page<RegionList> regionListPage = regionListService.page(page, queryWrapper);
        return Result.success(regionListPage);
    }

    @GetMapping("getRegionList")
    public Result getRegionList() {
        List<RegionList> listList = regionListService.list();
        return Result.success(listList);
    }

    /** 根据id获取区域 */
    @GetMapping("getRegionListById")
    public Result getRegionListById(@RequestParam("id")String id) {
        RegionList regionList = regionListService.getById(id);
        return Result.success(regionList);
    }

    /** 保存区域 */
    @PostMapping("saveRegionList")
    public Result saveRegionList(@RequestBody RegionList regionList) {
        boolean save = regionListService.save(regionList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑区域 */
    @PostMapping("editRegionList")
    public Result editRegionList(@RequestBody RegionList regionList) {
        boolean save = regionListService.updateById(regionList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除区域 */
    @GetMapping("removeRegionList")
    public Result removeRegionList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                regionListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("区域id不能为空！");
        }
    }

}
