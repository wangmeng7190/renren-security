package io.renren.modules.sys.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;


/**
 * @author wangmeng
 * @date 2020-01-15 14:37
 */
@Component
public class ShiroTag {
    /**
     * 是否拥有该权限
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission){
        Subject subject = SecurityUtils.getSubject();
        return subject != null && subject.isPermitted(permission);
    }
}
