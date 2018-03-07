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
public class Cartoontype {
    private String id;
    private String cartoontype;
    private String typedesc;
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
    @Column(name = "cartoontype")
    public String getCartoontype() {
        return cartoontype;
    }

    public void setCartoontype(String cartoontype) {
        this.cartoontype = cartoontype;
    }

    @Basic
    @Column(name = "typedesc")
    public String getTypedesc() {
        return typedesc;
    }

    public void setTypedesc(String typedesc) {
        this.typedesc = typedesc;
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

        Cartoontype that = (Cartoontype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cartoontype != null ? !cartoontype.equals(that.cartoontype) : that.cartoontype != null) return false;
        if (typedesc != null ? !typedesc.equals(that.typedesc) : that.typedesc != null) return false;
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
        result = 31 * result + (cartoontype != null ? cartoontype.hashCode() : 0);
        result = 31 * result + (typedesc != null ? typedesc.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (deleteflag != null ? deleteflag.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (createid != null ? createid.hashCode() : 0);
        result = 31 * result + (createname != null ? createname.hashCode() : 0);
        return result;
    }
}
