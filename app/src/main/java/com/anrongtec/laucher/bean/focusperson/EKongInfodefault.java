package com.anrongtec.laucher.bean.focusperson;

/**
 * Created by huiliu on 2018/3/2.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class EKongInfodefault {
    /**
     * 对比信息ID
     */
    private Long id;

    /**
     * 结果ID
     */
    private String resultId;

    /**
     * 任务ID
     */
    private String taskId;

    /**
     * 方案ID
     */
    private String bdId;

    /**
     * 报警号
     */
    private String alarmCode;

    /**
     * 碰撞时间
     */
    private String dtMatched;

    /**
     * 布控民警
     */
    private String bkmj;

    /**
     * 业务信息类型 (表码)
     */
    private String ywxxlx;

    /**
     * 信息文本
     */
    private String xxwb;

    /**
     * 布控人员编号
     */
    private String bkrybh;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 身份证
     */
    private String sfzh;

    /**
     * 性别
     */
    private String xb;

    /**
     * 出生日期
     */
    private String csrq;

    /**
     * 登记日期
     */
    private String djrq;

    /**
     * 出现地点
     */
    private String cxdd;

    /**
     * 布控级别
     */
    private String bkyxjb;

    /**
     * 布控日期(申请日期或审批日期)
     */
    private String bkrq;

    /**
     * 标准部门编码
     */
    private String bdBzbmbm;

    /**
     * 布控单位
     */
    private String bkdw;

    /**
     * 场景id
     */
    private String entironId;

    /**
     * 结果表数据对象id
     */
    private String jgbSjdxGid;

    /**
     * 异地布控人员编号，6位行政区划+服务方id+ 12位序号
     */
    private String bkbh;

    /**
     * 布控地域区划
     */
    private String bkdyqh;

    /**
     * 业务操作名称,如"旅馆国内旅客入住登记"
     */
    private String ywcz;

    /**
     * 登记单位名称
     */
    private String jgmc;

    /**
     * 其它信息
     */
    private String qtxx;

    /**
     * 详细信息json串，用于详细展现，需要配置详细模板，类似xxwb
     */
    private String detailjson;

    /**
     * 活动发生地区划
     */
    private String hdfsdqh;

    /**
     * 布控屏蔽结果表数据对象id
     */
    private String jgbSjdxGid2;

    /**
     * 活动发生时间
     */
    private String hdfssj;

    /**
     * 活动发生地点所属的社会场所代码
     */
    private String hdfsddshcsdm;

    /**
     * 登记外文姓名
     */
    private String djwwxm;

    /**
     * 动态信息提供单位
     */
    private String dtxxtgdw;

    /**
     * 动态信息提供单位机构代码
     */
    private String dtxxtgdwjgdm;

    /**
     * 动态轨迹信息编号
     */
    private String dtgjxxbh;

    /**
     * 操作验证码
     */
    private String czyzm;

    /**
     * 活动发生地点社会场所
     */
    private String hdfsddshcs;

    /**
     * 活动发生地点所属公安机关
     */
    private String hdfsddssgajg;

    /**
     * 活动发生地点所属公安机关机构代码
     */
    private String hdfsddssgajgjgdm;

    /**
     * 创建时间
     */
    private long createTime;

    /**
     * 修改人code
     */
    private String updatorId;

    /**
     * 修改时间
     */
    private long updateTime;

    /**
     * 是否删除标识(0:未删除; 1:已删除)这里用于已发送未发送
     */
    private Boolean del;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 相片
     */
    private String xp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getBdId() {
        return bdId;
    }

    public void setBdId(String bdId) {
        this.bdId = bdId;
    }

    public String getAlarmCode() {
        return alarmCode;
    }

    public void setAlarmCode(String alarmCode) {
        this.alarmCode = alarmCode;
    }

    public String getDtMatched() {
        return dtMatched;
    }

    public void setDtMatched(String dtMatched) {
        this.dtMatched = dtMatched;
    }

    public String getBkmj() {
        return bkmj;
    }

    public void setBkmj(String bkmj) {
        this.bkmj = bkmj;
    }

    public String getYwxxlx() {
        return ywxxlx;
    }

    public void setYwxxlx(String ywxxlx) {
        this.ywxxlx = ywxxlx;
    }

    public String getXxwb() {
        return xxwb;
    }

    public void setXxwb(String xxwb) {
        this.xxwb = xxwb;
    }

    public String getBkrybh() {
        return bkrybh;
    }

    public void setBkrybh(String bkrybh) {
        this.bkrybh = bkrybh;
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

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq;
    }

    public String getDjrq() {
        return djrq;
    }

    public void setDjrq(String djrq) {
        this.djrq = djrq;
    }

    public String getCxdd() {
        return cxdd;
    }

    public void setCxdd(String cxdd) {
        this.cxdd = cxdd;
    }

    public String getBkyxjb() {
        return bkyxjb;
    }

    public void setBkyxjb(String bkyxjb) {
        this.bkyxjb = bkyxjb;
    }

    public String getBkrq() {
        return bkrq;
    }

    public void setBkrq(String bkrq) {
        this.bkrq = bkrq;
    }

    public String getBdBzbmbm() {
        return bdBzbmbm;
    }

    public void setBdBzbmbm(String bdBzbmbm) {
        this.bdBzbmbm = bdBzbmbm;
    }

    public String getBkdw() {
        return bkdw;
    }

    public void setBkdw(String bkdw) {
        this.bkdw = bkdw;
    }

    public String getEntironId() {
        return entironId;
    }

    public void setEntironId(String entironId) {
        this.entironId = entironId;
    }

    public String getJgbSjdxGid() {
        return jgbSjdxGid;
    }

    public void setJgbSjdxGid(String jgbSjdxGid) {
        this.jgbSjdxGid = jgbSjdxGid;
    }

    public String getBkbh() {
        return bkbh;
    }

    public void setBkbh(String bkbh) {
        this.bkbh = bkbh;
    }

    public String getBkdyqh() {
        return bkdyqh;
    }

    public void setBkdyqh(String bkdyqh) {
        this.bkdyqh = bkdyqh;
    }

    public String getYwcz() {
        return ywcz;
    }

    public void setYwcz(String ywcz) {
        this.ywcz = ywcz;
    }

    public String getJgmc() {
        return jgmc;
    }

    public void setJgmc(String jgmc) {
        this.jgmc = jgmc;
    }

    public String getQtxx() {
        return qtxx;
    }

    public void setQtxx(String qtxx) {
        this.qtxx = qtxx;
    }

    public String getDetailjson() {
        return detailjson;
    }

    public void setDetailjson(String detailjson) {
        this.detailjson = detailjson;
    }

    public String getHdfsdqh() {
        return hdfsdqh;
    }

    public void setHdfsdqh(String hdfsdqh) {
        this.hdfsdqh = hdfsdqh;
    }

    public String getJgbSjdxGid2() {
        return jgbSjdxGid2;
    }

    public void setJgbSjdxGid2(String jgbSjdxGid2) {
        this.jgbSjdxGid2 = jgbSjdxGid2;
    }

    public String getHdfssj() {
        return hdfssj;
    }

    public void setHdfssj(String hdfssj) {
        this.hdfssj = hdfssj;
    }

    public String getHdfsddshcsdm() {
        return hdfsddshcsdm;
    }

    public void setHdfsddshcsdm(String hdfsddshcsdm) {
        this.hdfsddshcsdm = hdfsddshcsdm;
    }

    public String getDjwwxm() {
        return djwwxm;
    }

    public void setDjwwxm(String djwwxm) {
        this.djwwxm = djwwxm;
    }

    public String getDtxxtgdw() {
        return dtxxtgdw;
    }

    public void setDtxxtgdw(String dtxxtgdw) {
        this.dtxxtgdw = dtxxtgdw;
    }

    public String getDtxxtgdwjgdm() {
        return dtxxtgdwjgdm;
    }

    public void setDtxxtgdwjgdm(String dtxxtgdwjgdm) {
        this.dtxxtgdwjgdm = dtxxtgdwjgdm;
    }

    public String getDtgjxxbh() {
        return dtgjxxbh;
    }

    public void setDtgjxxbh(String dtgjxxbh) {
        this.dtgjxxbh = dtgjxxbh;
    }

    public String getCzyzm() {
        return czyzm;
    }

    public void setCzyzm(String czyzm) {
        this.czyzm = czyzm;
    }

    public String getHdfsddshcs() {
        return hdfsddshcs;
    }

    public void setHdfsddshcs(String hdfsddshcs) {
        this.hdfsddshcs = hdfsddshcs;
    }

    public String getHdfsddssgajg() {
        return hdfsddssgajg;
    }

    public void setHdfsddssgajg(String hdfsddssgajg) {
        this.hdfsddssgajg = hdfsddssgajg;
    }

    public String getHdfsddssgajgjgdm() {
        return hdfsddssgajgjgdm;
    }

    public void setHdfsddssgajgjgdm(String hdfsddssgajgjgdm) {
        this.hdfsddssgajgjgdm = hdfsddssgajgjgdm;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDel() {
        return del;
    }

    public void setDel(Boolean del) {
        this.del = del;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getXp() {
        return xp;
    }

    public void setXp(String xp) {
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "EKongInfodefault{" +
                "id=" + id +
                ", resultId='" + resultId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", bdId='" + bdId + '\'' +
                ", alarmCode='" + alarmCode + '\'' +
                ", dtMatched='" + dtMatched + '\'' +
                ", bkmj='" + bkmj + '\'' +
                ", ywxxlx='" + ywxxlx + '\'' +
                ", xxwb='" + xxwb + '\'' +
                ", bkrybh='" + bkrybh + '\'' +
                ", xm='" + xm + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", xb='" + xb + '\'' +
                ", csrq='" + csrq + '\'' +
                ", djrq='" + djrq + '\'' +
                ", cxdd='" + cxdd + '\'' +
                ", bkyxjb='" + bkyxjb + '\'' +
                ", bkrq='" + bkrq + '\'' +
                ", bdBzbmbm='" + bdBzbmbm + '\'' +
                ", bkdw='" + bkdw + '\'' +
                ", entironId='" + entironId + '\'' +
                ", jgbSjdxGid='" + jgbSjdxGid + '\'' +
                ", bkbh='" + bkbh + '\'' +
                ", bkdyqh='" + bkdyqh + '\'' +
                ", ywcz='" + ywcz + '\'' +
                ", jgmc='" + jgmc + '\'' +
                ", qtxx='" + qtxx + '\'' +
                ", detailjson='" + detailjson + '\'' +
                ", hdfsdqh='" + hdfsdqh + '\'' +
                ", jgbSjdxGid2='" + jgbSjdxGid2 + '\'' +
                ", hdfssj='" + hdfssj + '\'' +
                ", hdfsddshcsdm='" + hdfsddshcsdm + '\'' +
                ", djwwxm='" + djwwxm + '\'' +
                ", dtxxtgdw='" + dtxxtgdw + '\'' +
                ", dtxxtgdwjgdm='" + dtxxtgdwjgdm + '\'' +
                ", dtgjxxbh='" + dtgjxxbh + '\'' +
                ", czyzm='" + czyzm + '\'' +
                ", hdfsddshcs='" + hdfsddshcs + '\'' +
                ", hdfsddssgajg='" + hdfsddssgajg + '\'' +
                ", hdfsddssgajgjgdm='" + hdfsddssgajgjgdm + '\'' +
                ", createTime=" + createTime +
                ", updatorId='" + updatorId + '\'' +
                ", updateTime=" + updateTime +
                ", del=" + del +
                ", status=" + status +
                ", xp='" + xp + '\'' +
                '}';
    }
}
