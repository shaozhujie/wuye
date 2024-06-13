package com.project.wuye.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.GoodsList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.GoodsListService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 物品controller
 * @date 2024/03/09 08:56
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class GoodsListController {

    @Autowired
    private GoodsListService goodsListService;

    /** 分页获取物品 */
    @PostMapping("getGoodsListPage")
    public Result getGoodsListPage(@RequestBody GoodsList goodsList) {
        Page<GoodsList> page = new Page<>(goodsList.getPageNumber(),goodsList.getPageSize());
        QueryWrapper<GoodsList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(goodsList.getName()),GoodsList::getName,goodsList.getName())
                .eq(goodsList.getState() != null,GoodsList::getState,goodsList.getState());
        Page<GoodsList> goodsListPage = goodsListService.page(page, queryWrapper);
        return Result.success(goodsListPage);
    }

    /** 根据id获取物品 */
    @GetMapping("getGoodsListById")
    public Result getGoodsListById(@RequestParam("id")String id) {
        GoodsList goodsList = goodsListService.getById(id);
        return Result.success(goodsList);
    }

    /** 保存物品 */
    @PostMapping("saveGoodsList")
    public Result saveGoodsList(@RequestBody GoodsList goodsList) {
        boolean save = goodsListService.save(goodsList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑物品 */
    @PostMapping("editGoodsList")
    public Result editGoodsList(@RequestBody GoodsList goodsList) {
        if (goodsList.getState() == 1) {
            goodsList.setOutTime(new Date());
        }
        boolean save = goodsListService.updateById(goodsList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除物品 */
    @GetMapping("removeGoodsList")
    public Result removeGoodsList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                goodsListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("物品id不能为空！");
        }
    }

}