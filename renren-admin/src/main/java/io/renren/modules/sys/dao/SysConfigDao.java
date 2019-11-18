package io.renren.modules.sys.dao;

import io.renren.modules.sys.entity.SysConfigEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统配置信息表
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:41:56
 */
@Mapper
public interface SysConfigDao extends BaseMapper<SysConfigEntity> {
	
}
