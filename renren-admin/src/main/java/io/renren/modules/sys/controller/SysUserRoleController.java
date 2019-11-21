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

import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 用户与角色对应关系
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@RestController
@RequestMapping("sys/sysuserrole")
public class SysUserRoleController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysuserrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysUserRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:sysuserrole:info")
    public R info(@PathVariable("id") Long id){
        SysUserRoleEntity sysUserRole = sysUserRoleService.getById(id);

        return R.ok().put("sysUserRole", sysUserRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysuserrole:save")
    public R save(@RequestBody SysUserRoleEntity sysUserRole){
        sysUserRoleService.save(sysUserRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysuserrole:update")
    public R update(@RequestBody SysUserRoleEntity sysUserRole){
        ValidatorUtils.validateEntity(sysUserRole);
        sysUserRoleService.updateById(sysUserRole);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysuserrole:delete")
    public R delete(@RequestBody Long[] ids){
        sysUserRoleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
