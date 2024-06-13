package com.project.wuye.controller.houses;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.HousesList;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.HousesListService;
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
 * @description: 房屋controller
 * @date 2024/03/08 03:49
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class HousesListController {

    @Autowired
    private HousesListService housesListService;
    @Autowired
    private UserService userService;

    /** 分页获取房屋 */
    @PostMapping("getHousesListPage")
    public Result getHousesListPage(@RequestBody HousesList housesList) {
        Page<HousesList> page = new Page<>(housesList.getPageNumber(),housesList.getPageSize());
        QueryWrapper<HousesList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(housesList.getHouses()),HousesList::getHouses,housesList.getHouses())
                .eq(housesList.getLayer() != null,HousesList::getLayer,housesList.getLayer());
        Page<HousesList> housesListPage = housesListService.page(page, queryWrapper);
        return Result.success(housesListPage);
    }

    /** 根据id获取房屋 */
    @GetMapping("getHousesListById")
    public Result getHousesListById(@RequestParam("id")String id) {
        HousesList housesList = housesListService.getById(id);
        return Result.success(housesList);
    }

    /** 保存房屋 */
    @PostMapping("saveHousesList")
    public Result saveHousesList(@RequestBody HousesList housesList) {
        if (housesList.getState() != 0 && StringUtils.isNotBlank(housesList.getUserId())) {
            User user = userService.getById(housesList.getUserId());
            housesList.setUserName(user.getUserName());
        }
        boolean save = housesListService.save(housesList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }
    
    /** 编辑房屋 */
    @PostMapping("editHousesList")
    public Result editHousesList(@RequestBody HousesList housesList) {
        if (housesList.getState() != 0 && StringUtils.isNotBlank(housesList.getUserId())) {
            User user = userService.getById(housesList.getUserId());
            housesList.setUserName(user.getUserName());
        }
        boolean save = housesListService.updateById(housesList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除房屋 */
    @GetMapping("removeHousesList")
    public Result removeHousesList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                housesListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("房屋id不能为空！");
        }
    }

}
