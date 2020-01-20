package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.service.SysRoleDeptService;
import io.renren.modules.sys.service.SysRoleMenuService;
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
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController{
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleDeptService sysRoleDeptService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 角色列表
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public R select(){
        List<SysRoleEntity> list = sysRoleService.list();
        return R.ok().put("list", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenyIdList(roleId);
        role.setMenuIdList(menuIdList);

        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);

        return R.ok().put("role", role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity sysRole){
        ValidatorUtils.validateEntity(sysRole);
        sysRoleService.save(sysRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity sysRole){
        ValidatorUtils.validateEntity(sysRole);
        sysRoleService.updateById(sysRole);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
