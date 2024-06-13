package com.project.wuye.controller.user;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.project.wuye.domain.Result;
import com.project.wuye.domain.User;
import com.project.wuye.enums.ResultCode;
import com.project.wuye.service.UserService;
import com.project.wuye.utils.PasswordUtils;
import com.project.wuye.utils.RedisUtils;
import com.project.wuye.utils.TokenUtils;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @description: 用户controller
 * @date 2024/2/26 21:00
 */
@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtils redisUtils;


    /** 分页查询用户 */
    @PostMapping("getUserPage")
    public Result getUserPage(@RequestBody User user) {
        Page<User> page = userService.getUserPage(user);
        return Result.success(page);
    }

    /** 根据id查询用户 */
    @GetMapping("getUserById")
    public Result getUserById(@RequestParam("id") String id) {
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("getUserListByUserType")
    public Result getUserListByUserType(@RequestParam("userType") String userType) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUserType,userType);
        List<User> userList = userService.list(queryWrapper);
        return Result.success(userList);
    }

    /** 新增用户 */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("saveUser")
    public Result saveUser(@RequestBody User user) {
        //先校验登陆账号是否重复
        boolean account = checkAccount(user);
        if (!account) {
            return Result.fail("登陆账号已存在不可重复！");
        }
        String uuid = IdWorker.get32UUID();
        //密码加盐加密
        String encrypt = PasswordUtils.encrypt(user.getPassword());
        String[] split = encrypt.split("\\$");
        user.setId(uuid);
        user.setPassword(split[0]);
        user.setSalt(split[1]);
        user.setAvatar("/img/170912815513783e8154518eff925fac63d6e7af52851.jpeg");
        user.setPwdUpdateDate(new Date());
        //保存用户
        boolean save = userService.save(user);
        return Result.success();
    }

    /** 编辑用户 */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("editUser")
    public Result editUser(@RequestBody User user) {
        User user1 = userService.getById(user.getId());
        if (!user1.getLoginAccount().equals(user.getLoginAccount())) {
            //先校验登陆账号是否重复
            boolean account = checkAccount(user);
            if (!account) {
                return Result.fail("登陆账号已存在不可重复！");
            }
        }
        //更新用户
        boolean edit = userService.updateById(user);
        return Result.success();
    }

    /** 校验用户 */
    public boolean checkAccount(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getLoginAccount,user.getLoginAccount());
        int count = userService.count(queryWrapper);
        return count <= 0;
    }

    /** 删除用户 */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("removeUser")
    public Result removeUser(@RequestParam("ids")String ids) {
        if (StringUtils.isNotBlank(ids)) {
            String[] asList = ids.split(",");
            for (String id : asList) {
                User user = userService.getById(id);
                boolean remove = userService.removeById(id);
            }
            return Result.success();
        } else {
            return Result.fail("角色id不能为空！");
        }
    }

    @PostMapping("changePassword")
    public Result changePassword(@RequestBody JSONObject json) {
        String id = json.getString("id");
        String password = json.getString("password");
        User apeUser = userService.getById(id);
        boolean decrypt = PasswordUtils.decrypt(password, apeUser.getPassword() + "$" + apeUser.getSalt());
        if (decrypt) {
            String newPassword = json.getString("newPassword");
            String encrypt = PasswordUtils.encrypt(newPassword);
            String[] split = encrypt.split("\\$");
            apeUser.setSalt(split[1]);
            apeUser.setPassword(split[0]);
            apeUser.setPwdUpdateDate(new Date());
            boolean update = userService.updateById(apeUser);
            if (update) {
                return Result.success();
            } else {
                return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
            }
        } else {
            return Result.fail("旧密码不正确");
        }
    }

    /** 重置密码 */
    @PostMapping("resetPassword")
    public Result resetPassword(@RequestBody JSONObject json) {
        String id = json.getString("id");
        String newPassword = json.getString("newPassword");
        String encrypt = PasswordUtils.encrypt(newPassword);
        String[] split = encrypt.split("\\$");
        User user = userService.getById(id);
        boolean decrypt = PasswordUtils.decrypt(newPassword, user.getPassword() + "$" + user.getSalt());
        if (decrypt) {
            return Result.fail("新密码不可和旧密码相同！");
        }
        user.setPassword(split[0]);
        user.setSalt(split[1]);
        user.setPwdUpdateDate(new Date());
        boolean update = userService.updateById(user);
        if (update) {
            return Result.success();
        } else {
            return Result.fail(ResultCode.COMMON_DATA_OPTION_ERROR.getMessage());
        }
    }

    /** 获取登陆用户信息 */
    @GetMapping("getUserInfo")
    public Result getUserInfo() {
        String id = TokenUtils.getUserIdByToken();
        User user = userService.getById(id);
        return Result.success(user);
    }

    /** 修改个人信息 */
    @PostMapping("setUserInfo")
    public Result setUserInfo(@RequestBody User user) {
        String id = TokenUtils.getUserIdByToken();
        user.setId(id);
        userService.updateById(user);
        return Result.success();
    }

    /** 修改个人头像 */
    @PostMapping("setUserAvatar/{id}")
    public Result setUserAvatar(@PathVariable("id") String id, @RequestParam("file") MultipartFile avatar) {
        if(StringUtils.isBlank(id)){
            return Result.fail("用户id为空!");
        }
        User apeUser = userService.getById(id);
        if(avatar.isEmpty()){
            return Result.fail("上传的头像不能为空!");
        }
        String coverType = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
        if ("jpeg".equals(coverType)  || "gif".equals(coverType) || "png".equals(coverType) || "bmp".equals(coverType)  || "jpg".equals(coverType)) {
            //文件路径
            String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+"img";
            //文件名=当前时间到毫秒+原来的文件名
            String fileName = id + "."+ coverType;
            //如果文件路径不存在，新增该路径
            File file1 = new File(filePath);
            if(!file1.exists()){
                boolean mkdir = file1.mkdir();
            }
            //现在的文件地址
            if (StringUtils.isNotBlank(apeUser.getAvatar())) {
                String s = apeUser.getAvatar().split("/")[2];
                File now = new File(filePath + System.getProperty("file.separator") + s);
                boolean delete = now.delete();
            }
            //实际的文件地址
            File dest = new File(filePath + System.getProperty("file.separator") + fileName);
            //存储到数据库里的相对文件地址
            String storeImgPath = "/img/"+fileName;
            try {
                avatar.transferTo(dest);
                //更新头像
                apeUser.setAvatar(storeImgPath);
                userService.updateById(apeUser);
                return Result.success(storeImgPath);
            } catch (IOException e) {
                return Result.fail("上传失败");
            }
        } else {
            return Result.fail("请选择正确的图片格式");
        }
    }

}
