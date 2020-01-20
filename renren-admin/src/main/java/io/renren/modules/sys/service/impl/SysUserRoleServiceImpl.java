package io.renren.modules.sys.service.impl;

import io.renren.common.utils.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;

import io.renren.modules.sys.dao.SysUserRoleDao;
import io.renren.modules.sys.entity.SysUserRoleEntity;
import io.renren.modules.sys.service.SysUserRoleService;


@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysUserRoleEntity> page = this.page(
                new Query<SysUserRoleEntity>().getPage(params),
                new QueryWrapper<SysUserRoleEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        List<Long> roleIdList = baseMapper.queryRoleIdList(userId);
        return null;
    }

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除当前用户与角色的关系
        this.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));
        if(roleIdList == null || roleIdList.size() == 0){
            return;
        }

        //保存用户角色关系
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);
            this.save(sysUserRoleEntity);
        }
    }

}
