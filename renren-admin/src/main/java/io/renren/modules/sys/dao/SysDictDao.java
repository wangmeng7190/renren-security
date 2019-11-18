package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysDictEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
	
}
