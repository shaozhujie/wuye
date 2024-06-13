package com.project.wuye.controller.chargeType;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.ChargeType;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.ChargeTypeService;
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
 * @description: 收费类型controller
 * @date 2024/03/08 11:31
 */
@Controller
@ResponseBody
@RequestMapping("type")
public class ChargeTypeController {

    @Autowired
    private ChargeTypeService chargeTypeService;

    /** 分页获取收费类型 */
    @PostMapping("getChargeTypePage")
    public Result getChargeTypePage(@RequestBody ChargeType chargeType) {
        Page<ChargeType> page = new Page<>(chargeType.getPageNumber(),chargeType.getPageSize());
        QueryWrapper<ChargeType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(chargeType.getName()),ChargeType::getName,chargeType.getName());
        Page<ChargeType> chargeTypePage = chargeTypeService.page(page, queryWrapper);
        return Result.success(chargeTypePage);
    }

    @GetMapping("getChargeTypeList")
    public Result getChargeTypeList() {
        List<ChargeType> typeList = chargeTypeService.list();
        return Result.success(typeList);
    }

    /** 根据id获取收费类型 */
    @GetMapping("getChargeTypeById")
    public Result getChargeTypeById(@RequestParam("id")String id) {
        ChargeType chargeType = chargeTypeService.getById(id);
        return Result.success(chargeType);
    }

    /** 保存收费类型 */
    @PostMapping("saveChargeType")
    public Result saveChargeType(@RequestBody ChargeType chargeType) {
        boolean save = chargeTypeService.save(chargeType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑收费类型 */
    @PostMapping("editChargeType")
    public Result editChargeType(@RequestBody ChargeType chargeType) {
        boolean save = chargeTypeService.updateById(chargeType);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除收费类型 */
    @GetMapping("removeChargeType")
    public Result removeChargeType(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                chargeTypeService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("收费类型id不能为空！");
        }
    }

}
