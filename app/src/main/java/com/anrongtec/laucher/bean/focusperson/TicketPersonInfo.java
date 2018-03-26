package com.anrongtec.laucher.bean.focusperson;

/**
 * Created by huiliu on 2018/2/2.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TicketPersonInfo {
    /**
     * 对比信息ID
     * protected Long id;
     * <p>
     * 结果ID
     * protected String resultId;
     * <p>
     * 布控人员编号
     * protected String bkrybh;
     * <p>
     * 姓名
     * protected String xm;
     * <p>
     * 身份证
     * protected String sfzh;
     * <p>
     * 信息文本
     * protected String xxwb;
     * <p>
     * 购票时间
     * protected String buytime;
     * <p>
     * 诉求信息
     * protected String sqxx;
     * <p>
     * 列控等级
     * protected String lkdj;
     * <p>
     * 户籍地详址
     * protected String hzdxz;
     * <p>
     * 现住地详址
     * protected String xzdxz;
     * <p>
     * 责任民警
     * protected String zrszxm;
     * <p>
     * 责任民警联系电话
     * protected String zrszlxdh;
     * <p>
     * 属地分局
     * protected String sdfj;
     * <p>
     * 数据来源
     * protected String sjly;
     * <p>
     * 入库时间（巨龙）
     * protected String rksj;
     * <p>
     * 创建时间
     * protected Timestamp createTime;
     * <p>
     * 修改人code
     * protected String updatorId;
     * <p>
     * 修改时间
     * protected Timestamp updateTime;
     * <p>
     * 是否删除标识(0:未删除; 1:已删除)这里用于已发送未发送
     * protected Boolean del;
     * 状态
     * protected Integer status;
     * <p>
     * 相片
     * protected String xp;
     */
    private String sdfj;

    private String bkrybh;

    private String xzdxz;

    private int status;

    private String statusName;

    private String xm;

    private String sfzh;

    private String xp;

    private String sjly;

    private int id;

    private String lkdj;

    private String jpsj;

    private String result_id;

    private String xxwb;

    private String rksj;

    private String sqxx;

    private int create_time;

    private String zrszxm;

    private boolean del;

    private String hzdxz;

    private String zrszlxdh;

    public String getSdfj() {
        return sdfj;
    }

    public void setSdfj(String sdfj) {
        this.sdfj = sdfj;
    }

    public String getBkrybh() {
        return bkrybh;
    }

    public void setBkrybh(String bkrybh) {
        this.bkrybh = bkrybh;
    }

    public String getXzdxz() {
        return xzdxz;
    }

    public void setXzdxz(String xzdxz) {
        this.xzdxz = xzdxz;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    public String getSjly() {
        return sjly;
    }

    public void setSjly(String sjly) {
        this.sjly = sjly;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLkdj() {
        return lkdj;
    }

    public void setLkdj(String lkdj) {
        this.lkdj = lkdj;
    }

    public String getJpsj() {
        return jpsj;
    }

    public void setJpsj(String jpsj) {
        this.jpsj = jpsj;
    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getXxwb() {
        return xxwb;
    }

    public void setXxwb(String xxwb) {
        this.xxwb = xxwb;
    }

    public String getRksj() {
        return rksj;
    }

    public void setRksj(String rksj) {
        this.rksj = rksj;
    }

    public String getSqxx() {
        return sqxx;
    }

    public void setSqxx(String sqxx) {
        this.sqxx = sqxx;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public String getZrszxm() {
        return zrszxm;
    }

    public void setZrszxm(String zrszxm) {
        this.zrszxm = zrszxm;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public String getHzdxz() {
        return hzdxz;
    }

    public void setHzdxz(String hzdxz) {
        this.hzdxz = hzdxz;
    }

    public String getZrszlxdh() {
        return zrszlxdh;
    }

    public void setZrszlxdh(String zrszlxdh) {
        this.zrszlxdh = zrszlxdh;
    }
}
