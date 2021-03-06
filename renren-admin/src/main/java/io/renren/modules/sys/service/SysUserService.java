package io.renren.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-11-18 22:42:11
 */
public interface SysUserService extends IService<SysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean updatePassword(Long userId, String password, String newPassword);

    List<Long> queryAllMenuId(Long userId);

    void saveUser(SysUserEntity sysUser);
}

