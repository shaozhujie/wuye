package com.project.wuye.controller.post;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.PostList;
import com.project.wuye.domain.Result;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.PostListService;
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
 * @description: 职位controller
 * @date 2024/03/09 09:12
 */
@Controller
@ResponseBody
@RequestMapping("list")
public class PostListController {

    @Autowired
    private PostListService postListService;

    /** 分页获取职位 */
    @PostMapping("getPostListPage")
    public Result getPostListPage(@RequestBody PostList postList) {
        Page<PostList> page = new Page<>(postList.getPageNumber(),postList.getPageSize());
        QueryWrapper<PostList> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(StringUtils.isNotBlank(postList.getName()),PostList::getName,postList.getName());
        Page<PostList> postListPage = postListService.page(page, queryWrapper);
        return Result.success(postListPage);
    }

    @GetMapping("getPostList")
    public Result getPostList() {
        List<PostList> listList = postListService.list();
        return Result.success(listList);
    }

    /** 根据id获取职位 */
    @GetMapping("getPostListById")
    public Result getPostListById(@RequestParam("id")String id) {
        PostList postList = postListService.getById(id);
        return Result.success(postList);
    }

    /** 保存职位 */
    @PostMapping("savePostList")
    public Result savePostList(@RequestBody PostList postList) {
        boolean save = postListService.save(postList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 编辑职位 */
    @PostMapping("editPostList")
    public Result editPostList(@RequestBody PostList postList) {
        boolean save = postListService.updateById(postList);
        if (save) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 删除职位 */
    @GetMapping("removePostList")
    public Result removePostList(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                postListService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("职位id不能为空！");
        }
    }

}
