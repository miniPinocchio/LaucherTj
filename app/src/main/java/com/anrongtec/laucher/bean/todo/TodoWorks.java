package com.anrongtec.laucher.bean.todo;

import java.util.List;

/**
 *
 * @author huiliu
 * @date 2017/11/22
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TodoWorks {
    private int total;

    private List<TodoWorkDetail> data ;

    private String aggregates;

    private String carrier;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<TodoWorkDetail> getData() {
        return data;
    }

    public void setData(List<TodoWorkDetail> data) {
        this.data = data;
    }

    public String getAggregates() {
        return aggregates;
    }

    public void setAggregates(String aggregates) {
        this.aggregates = aggregates;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toString() {
        return "TodoWorks{" +
                "total=" + total +
                ", data=" + data +
                ", aggregates='" + aggregates + '\'' +
                ", carrier='" + carrier + '\'' +
                '}';
    }
}
