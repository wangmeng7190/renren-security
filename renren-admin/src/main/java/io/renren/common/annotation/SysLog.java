package io.renren.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 * @author wangmeng
 * @date 2020-01-14 15:52
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
