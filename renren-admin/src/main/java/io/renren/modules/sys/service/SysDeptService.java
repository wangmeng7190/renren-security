package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysDeptEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
public interface SysDeptService extends IService<SysDeptEntity> {
    /**
     * 查询列表
     * @param stringObjectHashMap
     * @return
     */
    List<SysDeptEntity> queryList(Map<String, Object> stringObjectHashMap);
}

