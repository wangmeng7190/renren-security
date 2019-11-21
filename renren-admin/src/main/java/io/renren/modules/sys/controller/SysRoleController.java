package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.sys.entity.SysRoleEntity;
import io.renren.modules.sys.service.SysRoleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 角色
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
@RestController
@RequestMapping("sys/sysrole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:sysrole:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRoleEntity sysRole = sysRoleService.getById(roleId);

        return R.ok().put("sysRole", sysRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysrole:save")
    public R save(@RequestBody SysRoleEntity sysRole){
        sysRoleService.save(sysRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysrole:update")
    public R update(@RequestBody SysRoleEntity sysRole){
        ValidatorUtils.validateEntity(sysRole);
        sysRoleService.updateById(sysRole);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysrole:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
