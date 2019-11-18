package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysRoleDeptEntity;

import java.util.Map;

/**
 * 角色与部门对应关系
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
public interface SysRoleDeptService extends IService<SysRoleDeptEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

