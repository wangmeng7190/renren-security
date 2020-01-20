package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Constant;
import io.renren.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.SysMenuService;


@Service("sysMenuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuDao, SysMenuEntity> implements SysMenuService {
    @Autowired
    private SysUserService sysUserService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysMenuEntity> page = this.page(
                new Query<SysMenuEntity>().getPage(params),
                new QueryWrapper<SysMenuEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 获取该登录用户所拥有的菜单
     * @param userId
     * @return
     */
    @Override
    public List<SysMenuEntity> getUserMenuList(Long userId) {
        //admin，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public List<SysMenuEntity> queryNotButtonList() {
        return baseMapper.queryNotButtonList();
    }

    /**
     * 获取所有菜单列表
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
        //递归获取子菜单
        return getMenuTreeList(menuList, menuIdList);
        //return menuList;
    }

    private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList) {
        List<SysMenuEntity> subMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList){
            //目录
            if(menu.getType() == Constant.MenuType.CATALOG.getValue()){
                menu.setList(getMenuTreeList(queryListParentId(menu.getMenuId(), menuIdList), menuIdList));
            }
            subMenuList.add(menu);
        }
        return subMenuList;
    }

    /**
     * 查询跟菜单列表
     * @param parentId
     * @param menuIdList
     * @return
     */
    private List<SysMenuEntity> queryListParentId(long parentId, List<Long> menuIdList) {
        List<SysMenuEntity> menuList = queryListParentId(parentId);
        if(menuIdList == null){
            return menuList;
        }
        List<SysMenuEntity> userMenuList = new ArrayList<>();
        for(SysMenuEntity menu : menuList){
            if(menuIdList.contains(menu.getMenuId())){
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    private List<SysMenuEntity> queryListParentId(long parentId) {
        return baseMapper.queryListParentId(parentId);
    }


}
