package com.anrongtec.laucher.bean.favorite;

import java.util.List;

/**
 *
 * @author huiliu
 * @date 2017/12/6
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Favorite {
    private Page page;

    private List<Rows> rows;

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return this.page;
    }

    public void setRows(List<Rows> rows) {
        this.rows = rows;
    }

    public List<Rows> getRows() {
        return this.rows;
    }

}
