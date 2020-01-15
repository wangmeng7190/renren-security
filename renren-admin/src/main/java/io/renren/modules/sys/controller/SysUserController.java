package io.renren.modules.sys.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.common.validator.group.AddGroup;
import io.renren.common.validator.group.UpdateGroup;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.modules.sys.service.SysUserService;
import io.renren.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 系统用户
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    @RequestMapping("/info")
    public R info(){
        return R.ok().put("user", getUser());
    }

    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword){
        if(StringUtils.isBlank(newPassword)){
            return R.error("新密码不能为空");
        }
        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if(!flag){
            return R.error("原密码不正确");
        }
        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:sysuser:info")
    public R info(@PathVariable("userId") Long userId){
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysuser:save")
    public R save(@RequestBody SysUserEntity sysUser){
        ValidatorUtils.validateEntity(sysUser, AddGroup.class);
        sysUserService.save(sysUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysuser:update")
    public R update(@RequestBody SysUserEntity sysUser){
        ValidatorUtils.validateEntity(sysUser, UpdateGroup.class);
        sysUserService.updateById(sysUser);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysuser:delete")
    public R delete(@RequestBody Long[] userIds){
        if(ArrayUtils.contains(userIds, 1L)){
            return R.error("超级管理员不能删除");
        }
        if(ArrayUtils.contains(userIds, getUserId())){
            return R.error("不能删除当前登录用户");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));
        return R.ok();
    }

}
