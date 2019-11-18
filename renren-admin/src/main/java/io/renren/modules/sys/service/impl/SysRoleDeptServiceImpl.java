package io.renren.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysRoleDeptDao;
import io.renren.modules.sys.entity.SysRoleDeptEntity;
import io.renren.modules.sys.service.SysRoleDeptService;


@Service("sysRoleDeptService")
public class SysRoleDeptServiceImpl extends ServiceImpl<SysRoleDeptDao, SysRoleDeptEntity> implements SysRoleDeptService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SysRoleDeptEntity> page = this.page(
                new Query<SysRoleDeptEntity>().getPage(params),
                new QueryWrapper<SysRoleDeptEntity>()
        );

        return new PageUtils(page);
    }

}
