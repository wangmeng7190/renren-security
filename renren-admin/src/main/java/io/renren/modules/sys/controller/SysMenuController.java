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

import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 菜单管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
@RestController
@RequestMapping("sys/sysmenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysmenu:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysMenuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{menuId}")
    @RequiresPermissions("sys:sysmenu:info")
    public R info(@PathVariable("menuId") Long menuId){
        SysMenuEntity sysMenu = sysMenuService.getById(menuId);

        return R.ok().put("sysMenu", sysMenu);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysmenu:save")
    public R save(@RequestBody SysMenuEntity sysMenu){
        sysMenuService.save(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysmenu:update")
    public R update(@RequestBody SysMenuEntity sysMenu){
        ValidatorUtils.validateEntity(sysMenu);
        sysMenuService.updateById(sysMenu);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysmenu:delete")
    public R delete(@RequestBody Long[] menuIds){
        sysMenuService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

}
