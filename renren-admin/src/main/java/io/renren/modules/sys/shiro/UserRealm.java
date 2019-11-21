package io.renren.modules.sys.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.modules.sys.dao.SysUserDao;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private SysUserDao sysUserDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        return null;
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
