package com.anrongtec.laucher.bean.sign;

/**
 * @author huiliu
 * @date 2018/4/23
 * @email liu594545591@126.com
 * @introduce
 */
public class SignPageData {
    private int pageSize;

    private int page;

    private int totalCount;

    private int totalPage;

    private String sort;

    private String order;

    private String orderByClause;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Override
    public String toString() {
        return "CooperPage{" +
                "pageSize=" + pageSize +
                ", page=" + page +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                ", sort='" + sort + '\'' +
                ", order='" + order + '\'' +
                ", orderByClause='" + orderByClause + '\'' +
                '}';
    }
}
