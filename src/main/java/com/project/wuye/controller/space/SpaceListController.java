package com.project.wuye.controller.space;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.SpaceList;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.SpaceListService;
import com.project.wuye.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 车位controller
 * @date 2024/03/08 03:24
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class SpaceListController {

    @Autowired
    private SpaceListService spaceListService;
    @Autowired
    private UserService userService;

    /** 分页获取车位 */
    @PostMapping("getSpaceListPage")
    public Result getSpaceListPage(@RequestBody SpaceList spaceList) {
        Page<SpaceList> page = new Page<>(spaceList.getPageNumber(),spaceList.getPageSize());
        QueryWrapper<SpaceList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(spaceList.getAddress()),SpaceList::getAddress,spaceList.getAddress())
                .like(StringUtils.isNotBlank(spaceList.getSpaceNumber()),SpaceList::getSpaceNumber,spaceList.getSpaceNumber())
                .eq(spaceList.getType() != null,SpaceList::getType,spaceList.getType());
        Page<SpaceList> spaceListPage = spaceListService.page(page, queryWrapper);
        return Result.success(spaceListPage);
    }

    /** 根据id获取车位 */
    @GetMapping("getSpaceListById")
    public Result getSpaceListById(@RequestParam("id")String id) {
        SpaceList spaceList = spaceListService.getById(id);
        return Result.success(spaceList);
    }

    /** 保存车位 */
    @PostMapping("saveSpaceList")
    public Result saveSpaceList(@RequestBody SpaceList spaceList) {
        if (spaceList.getType() != 0 && StringUtils.isNotBlank(spaceList.getUserId())) {
            User user = userService.getById(spaceList.getUserId());
            spaceList.setUserName(user.getUserName());
        }
        boolean save = spaceListService.save(spaceList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑车位 */
    @PostMapping("editSpaceList")
    public Result editSpaceList(@RequestBody SpaceList spaceList) {
        if (spaceList.getType() != 0 && StringUtils.isNotBlank(spaceList.getUserId())) {
            User user = userService.getById(spaceList.getUserId());
            spaceList.setUserName(user.getUserName());
        }
        boolean save = spaceListService.updateById(spaceList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除车位 */
    @GetMapping("removeSpaceList")
    public Result removeSpaceList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                spaceListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("车位id不能为空！");
        }
    }

}
