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
public class Cartooninfo {
    private String id;
    private String cartoonname;
    private String cartoontyperel;
    private String cartoonauthor;
    private String cartoonduration;
    private String cartoonarea;
    private String cartooninfo;
    private String cartoonseriesnum;
    private String cartoonlangue;
    private String cartoonversion;
    private String cartoondub;
    private String cartoonurl;
    private String cartoonpic;
    private String playtimes;
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
    @Column(name = "cartoonname")
    public String getCartoonname() {
        return cartoonname;
    }

    public void setCartoonname(String cartoonname) {
        this.cartoonname = cartoonname;
    }

    @Basic
    @Column(name = "cartoontyperel")
    public String getCartoontyperel() {
        return cartoontyperel;
    }

    public void setCartoontyperel(String cartoontyperel) {
        this.cartoontyperel = cartoontyperel;
    }

    @Basic
    @Column(name = "cartoonauthor")
    public String getCartoonauthor() {
        return cartoonauthor;
    }

    public void setCartoonauthor(String cartoonauthor) {
        this.cartoonauthor = cartoonauthor;
    }

    @Basic
    @Column(name = "cartoonduration")
    public String getCartoonduration() {
        return cartoonduration;
    }

    public void setCartoonduration(String cartoonduration) {
        this.cartoonduration = cartoonduration;
    }

    @Basic
    @Column(name = "cartoonarea")
    public String getCartoonarea() {
        return cartoonarea;
    }

    public void setCartoonarea(String cartoonarea) {
        this.cartoonarea = cartoonarea;
    }

    @Basic
    @Column(name = "cartooninfo")
    public String getCartooninfo() {
        return cartooninfo;
    }

    public void setCartooninfo(String cartooninfo) {
        this.cartooninfo = cartooninfo;
    }

    @Basic
    @Column(name = "cartoonseriesnum")
    public String getCartoonseriesnum() {
        return cartoonseriesnum;
    }

    public void setCartoonseriesnum(String cartoonseriesnum) {
        this.cartoonseriesnum = cartoonseriesnum;
    }

    @Basic
    @Column(name = "cartoonlangue")
    public String getCartoonlangue() {
        return cartoonlangue;
    }

    public void setCartoonlangue(String cartoonlangue) {
        this.cartoonlangue = cartoonlangue;
    }

    @Basic
    @Column(name = "cartoonversion")
    public String getCartoonversion() {
        return cartoonversion;
    }

    public void setCartoonversion(String cartoonversion) {
        this.cartoonversion = cartoonversion;
    }

    @Basic
    @Column(name = "cartoondub")
    public String getCartoondub() {
        return cartoondub;
    }

    public void setCartoondub(String cartoondub) {
        this.cartoondub = cartoondub;
    }

    @Basic
    @Column(name = "cartoonurl")
    public String getCartoonurl() {
        return cartoonurl;
    }

    public void setCartoonurl(String cartoonurl) {
        this.cartoonurl = cartoonurl;
    }

    @Basic
    @Column(name = "cartoonpic")
    public String getCartoonpic() {
        return cartoonpic;
    }

    public void setCartoonpic(String cartoonpic) {
        this.cartoonpic = cartoonpic;
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

        Cartooninfo that = (Cartooninfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cartoonname != null ? !cartoonname.equals(that.cartoonname) : that.cartoonname != null) return false;
        if (cartoontyperel != null ? !cartoontyperel.equals(that.cartoontyperel) : that.cartoontyperel != null)
            return false;
        if (cartoonauthor != null ? !cartoonauthor.equals(that.cartoonauthor) : that.cartoonauthor != null)
            return false;
        if (cartoonduration != null ? !cartoonduration.equals(that.cartoonduration) : that.cartoonduration != null)
            return false;
        if (cartoonarea != null ? !cartoonarea.equals(that.cartoonarea) : that.cartoonarea != null) return false;
        if (cartooninfo != null ? !cartooninfo.equals(that.cartooninfo) : that.cartooninfo != null) return false;
        if (cartoonseriesnum != null ? !cartoonseriesnum.equals(that.cartoonseriesnum) : that.cartoonseriesnum != null)
            return false;
        if (cartoonlangue != null ? !cartoonlangue.equals(that.cartoonlangue) : that.cartoonlangue != null)
            return false;
        if (cartoonversion != null ? !cartoonversion.equals(that.cartoonversion) : that.cartoonversion != null)
            return false;
        if (cartoondub != null ? !cartoondub.equals(that.cartoondub) : that.cartoondub != null) return false;
        if (cartoonurl != null ? !cartoonurl.equals(that.cartoonurl) : that.cartoonurl != null) return false;
        if (cartoonpic != null ? !cartoonpic.equals(that.cartoonpic) : that.cartoonpic != null) return false;
        if (playtimes != null ? !playtimes.equals(that.playtimes) : that.playtimes != null) return false;
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
        result = 31 * result + (cartoonname != null ? cartoonname.hashCode() : 0);
        result = 31 * result + (cartoontyperel != null ? cartoontyperel.hashCode() : 0);
        result = 31 * result + (cartoonauthor != null ? cartoonauthor.hashCode() : 0);
        result = 31 * result + (cartoonduration != null ? cartoonduration.hashCode() : 0);
        result = 31 * result + (cartoonarea != null ? cartoonarea.hashCode() : 0);
        result = 31 * result + (cartooninfo != null ? cartooninfo.hashCode() : 0);
        result = 31 * result + (cartoonseriesnum != null ? cartoonseriesnum.hashCode() : 0);
        result = 31 * result + (cartoonlangue != null ? cartoonlangue.hashCode() : 0);
        result = 31 * result + (cartoonversion != null ? cartoonversion.hashCode() : 0);
        result = 31 * result + (cartoondub != null ? cartoondub.hashCode() : 0);
        result = 31 * result + (cartoonurl != null ? cartoonurl.hashCode() : 0);
        result = 31 * result + (cartoonpic != null ? cartoonpic.hashCode() : 0);
        result = 31 * result + (playtimes != null ? playtimes.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deleteflag != null ? deleteflag.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (createid != null ? createid.hashCode() : 0);
        result = 31 * result + (createname != null ? createname.hashCode() : 0);
        return result;
    }
}
