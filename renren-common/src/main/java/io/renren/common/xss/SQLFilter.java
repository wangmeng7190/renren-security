package io.renren.common.xss;


import io.renren.common.exception.RRException;
import org.apache.commons.lang.StringUtils;

public class SQLFilter {

    public static String sqlInject(String str){
        if(StringUtils.isEmpty(str)){
            return null;
        }
        //去掉'|"|;|\字符串
        str = StringUtils.replace(str, "'","");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();
        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RRException("包含非法字符");
            }
        }
        return str;
    }
}
