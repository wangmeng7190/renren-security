package io.renren.common.annotation;

import java.lang.annotation.*;

/**
 * 数据过滤
 * @author wangmeng
 * @date 2020-01-17 15:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFilter {
    /**
     * 表的别名
     * @return
     */
    String tableAlias() default "";

    /**
     * true: 没有本部门数据权限，也能查询本人数据
     * @return
     */
    boolean user()default true;

    /**
     * true: 拥有子部门数据权限
     * @return
     */
    boolean subDept() default false;

    /**
     * 部门id
     * @return
     */
    String deptId() default "dept_id";

    /**
     * 用户id
     * @return
     */
    String userId()default "user_id";
}
