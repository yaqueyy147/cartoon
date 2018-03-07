package com.ljy.cartoon.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/6 0006.
 */
@Entity
public class Cartoonseries {
    private String id;
    private String cartoonid;
    private String seriesnum;
    private String seriestitle;
    private String seriesinfo;
    private String seriesurl;
    private String seriesduration;
    private String playtimes;
    private String seriespic;
    private String remark;
    private String deleteflag;
    private Date createdate;
    private String createid;
    private String createname;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "cartoonid")
    public String getCartoonid() {
        return cartoonid;
    }

    public void setCartoonid(String cartoonid) {
        this.cartoonid = cartoonid;
    }

    @Basic
    @Column(name = "seriesnum")
    public String getSeriesnum() {
        return seriesnum;
    }

    public void setSeriesnum(String seriesnum) {
        this.seriesnum = seriesnum;
    }

    @Basic
    @Column(name = "seriestitle")
    public String getSeriestitle() {
        return seriestitle;
    }

    public void setSeriestitle(String seriestitle) {
        this.seriestitle = seriestitle;
    }

    @Basic
    @Column(name = "seriesinfo")
    public String getSeriesinfo() {
        return seriesinfo;
    }

    public void setSeriesinfo(String seriesinfo) {
        this.seriesinfo = seriesinfo;
    }

    @Basic
    @Column(name = "seriesurl")
    public String getSeriesurl() {
        return seriesurl;
    }

    public void setSeriesurl(String seriesurl) {
        this.seriesurl = seriesurl;
    }

    @Basic
    @Column(name = "seriesduration")
    public String getSeriesduration() {
        return seriesduration;
    }

    public void setSeriesduration(String seriesduration) {
        this.seriesduration = seriesduration;
    }

    @Basic
    @Column(name = "playtimes")
    public String getPlaytimes() {
        return playtimes;
    }

    public void setPlaytimes(String playtimes) {
        this.playtimes = playtimes;
    }

    @Basic
    @Column(name = "seriespic")
    public String getSeriespic() {
        return seriespic;
    }

    public void setSeriespic(String seriespic) {
        this.seriespic = seriespic;
    }

    @Basic
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "deleteflag")
    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag;
    }

    @Basic
    @Column(name = "createdate")
    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    @Basic
    @Column(name = "createid")
    public String getCreateid() {
        return createid;
    }

    public void setCreateid(String createid) {
        this.createid = createid;
    }

    @Basic
    @Column(name = "createname")
    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cartoonseries that = (Cartoonseries) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cartoonid != null ? !cartoonid.equals(that.cartoonid) : that.cartoonid != null) return false;
        if (seriesnum != null ? !seriesnum.equals(that.seriesnum) : that.seriesnum != null) return false;
        if (seriestitle != null ? !seriestitle.equals(that.seriestitle) : that.seriestitle != null) return false;
        if (seriesinfo != null ? !seriesinfo.equals(that.seriesinfo) : that.seriesinfo != null) return false;
        if (seriesurl != null ? !seriesurl.equals(that.seriesurl) : that.seriesurl != null) return false;
        if (seriesduration != null ? !seriesduration.equals(that.seriesduration) : that.seriesduration != null)
            return false;
        if (playtimes != null ? !playtimes.equals(that.playtimes) : that.playtimes != null) return false;
        if (seriespic != null ? !seriespic.equals(that.seriespic) : that.seriespic != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (deleteflag != null ? !deleteflag.equals(that.deleteflag) : that.deleteflag != null) return false;
        if (createdate != null ? !createdate.equals(that.createdate) : that.createdate != null) return false;
        if (createid != null ? !createid.equals(that.createid) : that.createid != null) return false;
        if (createname != null ? !createname.equals(that.createname) : that.createname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cartoonid != null ? cartoonid.hashCode() : 0);
        result = 31 * result + (seriesnum != null ? seriesnum.hashCode() : 0);
        result = 31 * result + (seriestitle != null ? seriestitle.hashCode() : 0);
        result = 31 * result + (seriesinfo != null ? seriesinfo.hashCode() : 0);
        result = 31 * result + (seriesurl != null ? seriesurl.hashCode() : 0);
        result = 31 * result + (seriesduration != null ? seriesduration.hashCode() : 0);
        result = 31 * result + (playtimes != null ? playtimes.hashCode() : 0);
        result = 31 * result + (seriespic != null ? seriespic.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deleteflag != null ? deleteflag.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (createid != null ? createid.hashCode() : 0);
        result = 31 * result + (createname != null ? createname.hashCode() : 0);
        return result;
    }
}
