package com.project.wuye.controller.complain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.ComplainList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.ComplainListService;
import com.project.wuye.service.UserService;
import com.project.wuye.utils.TokenUtils;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 投诉controller
 * @date 2024/03/09 10:19
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class ComplainListController {

    @Autowired
    private ComplainListService complainListService;

    /** 分页获取投诉 */
    @PostMapping("getComplainListPage")
    public Result getComplainListPage(@RequestBody ComplainList complainList) {
        Page<ComplainList> page = new Page<>(complainList.getPageNumber(),complainList.getPageSize());
        QueryWrapper<ComplainList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(complainList.getUserId()),ComplainList::getUserId,complainList.getUserId())
                .like(StringUtils.isNotBlank(complainList.getContent()),ComplainList::getContent,complainList.getContent());
        Page<ComplainList> complainListPage = complainListService.page(page, queryWrapper);
        return Result.success(complainListPage);
    }

    /** 根据id获取投诉 */
    @GetMapping("getComplainListById")
    public Result getComplainListById(@RequestParam("id")String id) {
        ComplainList complainList = complainListService.getById(id);
        return Result.success(complainList);
    }

    /** 保存投诉 */
    @PostMapping("saveComplainList")
    public Result saveComplainList(@RequestBody ComplainList complainList) {
        complainList.setUserId(TokenUtils.getUserIdByToken());
        boolean save = complainListService.save(complainList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑投诉 */
    @PostMapping("editComplainList")
    public Result editComplainList(@RequestBody ComplainList complainList) {
        complainList.setUserId(TokenUtils.getUserIdByToken());
        boolean save = complainListService.updateById(complainList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除投诉 */
    @GetMapping("removeComplainList")
    public Result removeComplainList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                complainListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("投诉id不能为空！");
        }
    }

}