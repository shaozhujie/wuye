package com.project.wuye.controller.dispute;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.DisputeList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.DisputeListService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author 超级管理员
 * @version 1.0
 * @description: 纠纷controller
 * @date 2024/03/09 10:44
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class DisputeListController {

    @Autowired
    private DisputeListService disputeListService;

    /** 分页获取纠纷 */
    @PostMapping("getDisputeListPage")
    public Result getDisputeListPage(@RequestBody DisputeList disputeList) {
        Page<DisputeList> page = new Page<>(disputeList.getPageNumber(),disputeList.getPageSize());
        QueryWrapper<DisputeList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StringUtils.isNotBlank(disputeList.getContent()),DisputeList::getContent,disputeList.getContent());
        Page<DisputeList> disputeListPage = disputeListService.page(page, queryWrapper);
        return Result.success(disputeListPage);
    }

    /** 根据id获取纠纷 */
    @GetMapping("getDisputeListById")
    public Result getDisputeListById(@RequestParam("id")String id) {
        DisputeList disputeList = disputeListService.getById(id);
        return Result.success(disputeList);
    }

    /** 保存纠纷 */
    @PostMapping("saveDisputeList")
    public Result saveDisputeList(@RequestBody DisputeList disputeList) {
        boolean save = disputeListService.save(disputeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑纠纷 */
    @PostMapping("editDisputeList")
    public Result editDisputeList(@RequestBody DisputeList disputeList) {
        boolean save = disputeListService.updateById(disputeList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除纠纷 */
    @GetMapping("removeDisputeList")
    public Result removeDisputeList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                disputeListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("纠纷id不能为空！");
        }
    }

}
