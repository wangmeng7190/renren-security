package io.renren.modules.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.Constant;
import io.renren.modules.sys.dao.SysMenuDao;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysMenuDao sysMenuDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
        Long userId = user.getUserId();

        List<String> permsList;

        //系统管理员拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for(SysMenuEntity menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserDao.queryAllPerms(userId);
        }

        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perm : permsList){
            if(StringUtils.isBlank(perm)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perm.trim().split(",")));
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenToken;
        //查询用户信息
        SysUserEntity user =sysUserDao.selectOne(new QueryWrapper<SysUserEntity>().eq("username", token.getUsername()));
        //账号不存在
        if(user == null){
            throw new UnknownAccountException("账号或密码不正确");
        }

        //账号被锁定
        if(user.getStatus() == 0){
            throw new LockedAccountException("账号已经被锁定，请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
        return info;
    }

    public void setCredentialsMatcher(){
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName(ShiroUtils.hashAlgorithmName);
        credentialsMatcher.setHashIterations(ShiroUtils.hashIterations);
        super.setCredentialsMatcher(credentialsMatcher);
    }
}
