package com.anrongtec.laucher.bean.sign;

import java.util.List;

/**
 * @author huiliu
 * @date 2018/4/23
 * @email liu594545591@126.com
 * @introduce
 */
public class SignData {
    private SignPageData page;
    private List<SignInfoData> rows ;
    public SignPageData getPage() {
        return page;
    }

    public void setPage(SignPageData page) {
        this.page = page;
    }

    public List<SignInfoData> getRows() {
        return rows;
    }

    public void setRows(List<SignInfoData> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "SignData{" +
                "page=" + page +
                ", rows=" + rows +
                '}';
    }
}
