package com.hengyi.util;

/**
 * 分页实体类
 * */
public class Page {
    //默认页面记录数
    public final static int DEFAULT_PAGE_SIZE = 10;
    //当前所在页码
    private Integer pageNo;
    //页面大小
    private Integer pageSize;
    //总记录数
    private Long total;
    //偏移记录数
    private Integer offset;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Page(Integer pageNo, Integer pageSize, Long total) {
        this.pageNo = pageNo > 0 ? pageNo : 1;

        this.total = total;

        if(pageSize != null && pageSize > 0) {
            this.pageSize = pageSize;
        } else {
            this.pageSize = DEFAULT_PAGE_SIZE;
        }
        this.offset = (this.pageNo - 1) * this.pageSize;
    }
}
