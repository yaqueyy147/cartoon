package com.ljy.cartoon.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Administrator on 2018/3/6 0006.
 */
@Entity
public class Cartoonpic {
    private String id;
    private String cartoonid;
    private String cartoonpic;

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
    @Column(name = "cartoonpic")
    public String getCartoonpic() {
        return cartoonpic;
    }

    public void setCartoonpic(String cartoonpic) {
        this.cartoonpic = cartoonpic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cartoonpic that = (Cartoonpic) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (cartoonid != null ? !cartoonid.equals(that.cartoonid) : that.cartoonid != null) return false;
        if (cartoonpic != null ? !cartoonpic.equals(that.cartoonpic) : that.cartoonpic != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cartoonid != null ? cartoonid.hashCode() : 0);
        result = 31 * result + (cartoonpic != null ? cartoonpic.hashCode() : 0);
        return result;
    }
}
