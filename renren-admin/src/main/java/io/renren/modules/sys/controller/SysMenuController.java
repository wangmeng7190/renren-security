package io.renren.modules.sys.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.validator.ValidatorUtils;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("sys/menu")
public class SysMenuController extends AbstractController{
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 当前登录用户所拥有的菜单
     * @return
     */
    @RequestMapping("/nav")
    public R nav(){
        List<SysMenuEntity> menuList = sysMenuService.getUserMenuList(getUserId());
        return R.ok().put("menuList", menuList);
    }


    /**
     * 所有菜单列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:sysmenu:list")
    public List<SysMenuEntity> list(@RequestParam Map<String, Object> params){
        List<SysMenuEntity> menuList = sysMenuService.list();
        for(SysMenuEntity menu : menuList){
            SysMenuEntity parentMenu = sysMenuService.getById(menu.getParentId());
            if(parentMenu != null){
                menu.setParentName(parentMenu.getName());
            }
        }

        return menuList;
    }

    /**
     * 选择菜单
     * @return
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:menu:select")
    public R select(){
        List<SysMenuEntity> menuList = sysMenuService.queryNotButtonList();

        //添加顶级菜单
        SysMenuEntity root = new SysMenuEntity();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);
        menuList.add(root);
        return R.ok().put("menuList", menuList);
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
    @SysLog("保存菜单")
    @RequestMapping("/save")
    @RequiresPermissions("sys:sysmenu:save")
    public R save(@RequestBody SysMenuEntity sysMenu){
        //数据校验
        verifyForm(sysMenu);

        sysMenuService.save(sysMenu);

        return R.ok();
    }

    /**
     * 修改
     */
    @SysLog("修改菜单")
    @RequestMapping("/update")
    @RequiresPermissions("sys:sysmenu:update")
    public R update(@RequestBody SysMenuEntity sysMenu){
        //数据校验
        verifyForm(sysMenu);

        sysMenuService.updateById(sysMenu);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @SysLog("删除菜单")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:sysmenu:delete")
    public R delete(@RequestBody Long[] menuIds){
        sysMenuService.removeByIds(Arrays.asList(menuIds));

        return R.ok();
    }

    //验证参数是否正确
    private void verifyForm(SysMenuEntity sysMenu) {
        if(StringUtils.isBlank(sysMenu.getName())){
            throw new RRException("菜单名称不能为空");
        }
        if(sysMenu.getParentId() == null){
            throw new RRException("上级菜单不能为空");
        }

        //上级菜单类型
        int parentType = Constant.MenuType.CATALOG.getValue();
        if(sysMenu.getParentId() != 0){
            SysMenuEntity parenMenu = sysMenuService.getById(sysMenu.getParentId());
            parentType = parenMenu.getType();
        }

        //目录、菜单
        if(sysMenu.getType() == Constant.MenuType.CATALOG.getValue() ||
                sysMenu.getType() == Constant.MenuType.MENU.getValue()){
            if(parentType != Constant.MenuType.CATALOG.getValue()){
                throw new RRException("上级菜单只能为目录类型");
            }
            return ;
        }

        //按钮
        if(sysMenu.getType() == Constant.MenuType.BUTTON.getValue()){
            if(parentType != Constant.MenuType.MENU.getValue()){
                throw new RRException("上级菜单只能为菜单类型");
            }
            return ;
        }
    }

}
