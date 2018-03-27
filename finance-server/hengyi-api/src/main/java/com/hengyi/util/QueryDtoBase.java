package com.hengyi.util;

/**
 * 分页实体类
 * */
public class QueryDtoBase {

    /**mysql每页条数*/
    private Integer limit;

    /**mysql分页位移*/
    private Integer offset;

    /**orderby语句, 数据库字段名, e.g. column_1 asc, column_2 desc*/
    private String orderByClause;

    /** 页码 */
    private Integer pageIndex;
    /** 每页数据量 */
    private Integer pageCount;

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }
}
