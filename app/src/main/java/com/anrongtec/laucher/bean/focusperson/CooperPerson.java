package com.anrongtec.laucher.bean.focusperson;

import java.util.List;

/**
 * Created by huiliu on 2018/3/3.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class CooperPerson {
    private CooperPage page;

    private List<CooperPersonInfos> rows ;

    public CooperPage getPage() {
        return page;
    }

    public void setPage(CooperPage page) {
        this.page = page;
    }

    public List<CooperPersonInfos> getRows() {
        return rows;
    }

    public void setRows(List<CooperPersonInfos> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "CooperPerson{" +
                "page=" + page +
                ", rows=" + rows +
                '}';
    }
}
