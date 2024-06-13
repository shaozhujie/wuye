package com.project.wuye.controller.repair;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.RepairList;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.RepairListService;
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
 * @description: 报修controller
 * @date 2024/03/08 05:09
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class RepairListController {

    @Autowired
    private RepairListService repairListService;
    @Autowired
    private UserService userService;

    /** 分页获取报修 */
    @PostMapping("getRepairListPage")
    public Result getRepairListPage(@RequestBody RepairList repairList) {
        Page<RepairList> page = new Page<>(repairList.getPageNumber(),repairList.getPageSize());
        QueryWrapper<RepairList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(repairList.getUserId()),RepairList::getUserId,repairList.getUserId())
                .like(StringUtils.isNotBlank(repairList.getIntroduce()),RepairList::getIntroduce,repairList.getIntroduce())
                .like(StringUtils.isNotBlank(repairList.getCreateBy()),RepairList::getCreateBy,repairList.getCreateBy());
        Page<RepairList> repairListPage = repairListService.page(page, queryWrapper);
        return Result.success(repairListPage);
    }

    /** 根据id获取报修 */
    @GetMapping("getRepairListById")
    public Result getRepairListById(@RequestParam("id")String id) {
        RepairList repairList = repairListService.getById(id);
        return Result.success(repairList);
    }

    /** 保存报修 */
    @PostMapping("saveRepairList")
    public Result saveRepairList(@RequestBody RepairList repairList) {
        String id = TokenUtils.getUserIdByToken();
        repairList.setUserId(id);
        boolean save = repairListService.save(repairList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑报修 */
    @PostMapping("editRepairList")
    public Result editRepairList(@RequestBody RepairList repairList) {
        String id = TokenUtils.getUserIdByToken();
        repairList.setUserId(id);
        boolean save = repairListService.updateById(repairList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除报修 */
    @GetMapping("removeRepairList")
    public Result removeRepairList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                repairListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("报修id不能为空！");
        }
    }

}
