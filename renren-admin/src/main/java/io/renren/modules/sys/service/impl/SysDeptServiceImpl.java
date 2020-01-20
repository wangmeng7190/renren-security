package io.renren.modules.sys.service.impl;

import io.renren.common.annotation.DataFilter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sys.dao.SysDeptDao;
import io.renren.modules.sys.entity.SysDeptEntity;
import io.renren.modules.sys.service.SysDeptService;

/**
 * @author wangmeng
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Override
    @DataFilter(subDept = true, user = false, tableAlias = "t1")
    public List<SysDeptEntity> queryList(Map<String, Object> params) {
        return baseMapper.queryList(params);
    }

}
