package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysDeptEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 部门管理
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
@Mapper
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {
	
}
