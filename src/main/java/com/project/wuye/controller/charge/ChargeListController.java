package com.project.wuye.controller.charge;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.ChargeList;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.ChargeListService;
import com.project.wuye.service.UserService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 收费controller
 * @date 2024/03/08 02:17
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class ChargeListController {

    @Autowired
    private ChargeListService chargeListService;
    @Autowired
    private UserService userService;

    /** 分页获取收费 */
    @PostMapping("getChargeListPage")
    public Result getChargeListPage(@RequestBody ChargeList chargeList) {
        Page<ChargeList> page = new Page<>(chargeList.getPageNumber(),chargeList.getPageSize());
        QueryWrapper<ChargeList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(chargeList.getUserId()),ChargeList::getUserId,chargeList.getUserId())
                .like(StringUtils.isNotBlank(chargeList.getType()),ChargeList::getType,chargeList.getType())
                .like(StringUtils.isNotBlank(chargeList.getUserName()),ChargeList::getUserName,chargeList.getUserName());
        Page<ChargeList> chargeListPage = chargeListService.page(page, queryWrapper);
        return Result.success(chargeListPage);
    }

    /** 根据id获取收费 */
    @GetMapping("getChargeListById")
    public Result getChargeListById(@RequestParam("id")String id) {
        ChargeList chargeList = chargeListService.getById(id);
        return Result.success(chargeList);
    }

    /** 保存收费 */
    @PostMapping("saveChargeList")
    public Result saveChargeList(@RequestBody ChargeList chargeList) {
        User user = userService.getById(chargeList.getUserId());
        chargeList.setUserName(user.getUserName());
        boolean save = chargeListService.save(chargeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑收费 */
    @PostMapping("editChargeList")
    public Result editChargeList(@RequestBody ChargeList chargeList) {
        User user = userService.getById(chargeList.getUserId());
        chargeList.setUserName(user.getUserName());
        boolean save = chargeListService.updateById(chargeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除收费 */
    @GetMapping("removeChargeList")
    public Result removeChargeList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                chargeListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("收费id不能为空！");
        }
    }

}
