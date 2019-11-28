package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 菜单管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
@Mapper
public interface SysMenuDao extends BaseMapper<SysMenuEntity> {
    /**
     * 根据父菜单ID查询出父菜单
     * @param parentId
     * @return
     */
    List<SysMenuEntity> queryListParentId(long parentId);

    List<SysMenuEntity> queryNotButtonList();
}
