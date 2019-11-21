package io.renren.common.utils;

/**
 * 常量
 */
public class Constant {
    /**
     * 超级管理员id
     */
    public final static int SUPER_ADMIN = 1;
    /**
     * 数据权限过滤
     */
    public final static String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public final static String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    public enum MenuType{
        /**
         * 目录
         */
        CATALOG(0),

        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value){
            this.value = value;
        }
        public int getValue(){
            return value;
        }
    }



}
