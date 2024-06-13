package com.project.wuye.controller.negative;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.NegativeList;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.NegativeListService;
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
 * @description: 欠费记录controller
 * @date 2024/03/08 02:44
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class NegativeListController {

    @Autowired
    private NegativeListService negativeListService;
    @Autowired
    private UserService userService;

    /** 分页获取欠费记录 */
    @PostMapping("getNegativeListPage")
    public Result getNegativeListPage(@RequestBody NegativeList negativeList) {
        Page<NegativeList> page = new Page<>(negativeList.getPageNumber(),negativeList.getPageSize());
        QueryWrapper<NegativeList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(negativeList.getType()),NegativeList::getType,negativeList.getType())
                .eq(StringUtils.isNotBlank(negativeList.getUserId()),NegativeList::getUserId,negativeList.getUserId())
                .like(StringUtils.isNotBlank(negativeList.getUserName()),NegativeList::getUserName,negativeList.getUserName())
                .eq(negativeList.getState() != null,NegativeList::getState,negativeList.getState());
        Page<NegativeList> negativeListPage = negativeListService.page(page, queryWrapper);
        return Result.success(negativeListPage);
    }

    /** 根据id获取欠费记录 */
    @GetMapping("getNegativeListById")
    public Result getNegativeListById(@RequestParam("id")String id) {
        NegativeList negativeList = negativeListService.getById(id);
        return Result.success(negativeList);
    }

    /** 保存欠费记录 */
    @PostMapping("saveNegativeList")
    public Result saveNegativeList(@RequestBody NegativeList negativeList) {
        User user = userService.getById(negativeList.getUserId());
        negativeList.setUserName(user.getUserName());
        boolean save = negativeListService.save(negativeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑欠费记录 */
    @PostMapping("editNegativeList")
    public Result editNegativeList(@RequestBody NegativeList negativeList) {
        User user = userService.getById(negativeList.getUserId());
        negativeList.setUserName(user.getUserName());
        boolean save = negativeListService.updateById(negativeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除欠费记录 */
    @GetMapping("removeNegativeList")
    public Result removeNegativeList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                negativeListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("欠费记录id不能为空！");
        }
    }

}
