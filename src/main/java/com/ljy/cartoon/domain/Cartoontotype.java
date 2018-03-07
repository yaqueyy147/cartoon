package com.ljy.cartoon.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 2018/3/6 0006.
 */
@Entity
public class Cartoontotype {
    private String id;
    private String typeid;
    private String cartoonid;

    @Id
    @Column(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "typeid")
    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    @Basic
    @Column(name = "cartoonid")
    public String getCartoonid() {
        return cartoonid;
    }

    public void setCartoonid(String cartoonid) {
        this.cartoonid = cartoonid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cartoontotype that = (Cartoontotype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeid != null ? !typeid.equals(that.typeid) : that.typeid != null) return false;
        if (cartoonid != null ? !cartoonid.equals(that.cartoonid) : that.cartoonid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (typeid != null ? typeid.hashCode() : 0);
        result = 31 * result + (cartoonid != null ? cartoonid.hashCode() : 0);
        return result;
    }
}
