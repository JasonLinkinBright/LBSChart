package com.graduate.lsj.lbschartforgraduate.dao.pojo;

import cn.bmob.v3.BmobObject;

/**
 * 近一周(六天)内的浏览记录(人数)
 * Created by lsj on 2016/3/31.
 */
public class VisitData extends BmobObject{

    /**
     * 访问项目名称
     */
    private String visitName;
    /**
     * 从今天倒推六天内的访问量
     */
    private Integer visit1;
    private Integer visit2;
    private Integer visit3;
    private Integer visit4;
    private Integer visit5;
    private Integer visit6;

    /**
     * 优惠项目名称
     */
    private String discountName;
    /**
     * 从今天开始未来六天内的优惠额度(利率等等)
     */
    private Double discount1;
    private Double discount2;
    private Double discount3;
    private Double discount4;
    private Double discount5;
    private Double discount6;

    /**
     * 指向的机构名称，表关联
     */
    private NearInstitution institution;

    /**
     * 点赞数
     */
    private Integer like;

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public Integer getVisit1() {
        return visit1;
    }

    public void setVisit1(Integer visit1) {
        this.visit1 = visit1;
    }

    public Integer getVisit2() {
        return visit2;
    }

    public void setVisit2(Integer visit2) {
        this.visit2 = visit2;
    }

    public Integer getVisit3() {
        return visit3;
    }

    public void setVisit3(Integer visit3) {
        this.visit3 = visit3;
    }

    public Integer getVisit4() {
        return visit4;
    }

    public void setVisit4(Integer visit4) {
        this.visit4 = visit4;
    }

    public Integer getVisit5() {
        return visit5;
    }

    public void setVisit5(Integer visit5) {
        this.visit5 = visit5;
    }

    public Integer getVisit6() {
        return visit6;
    }

    public void setVisit6(Integer visit6) {
        this.visit6 = visit6;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Double getDiscount1() {
        return discount1;
    }

    public void setDiscount1(Double discount1) {
        this.discount1 = discount1;
    }

    public Double getDiscount2() {
        return discount2;
    }

    public void setDiscount2(Double discount2) {
        this.discount2 = discount2;
    }

    public Double getDiscount3() {
        return discount3;
    }

    public void setDiscount3(Double discount3) {
        this.discount3 = discount3;
    }

    public Double getDiscount4() {
        return discount4;
    }

    public void setDiscount4(Double discount4) {
        this.discount4 = discount4;
    }

    public Double getDiscount5() {
        return discount5;
    }

    public void setDiscount5(Double discount5) {
        this.discount5 = discount5;
    }

    public Double getDiscount6() {
        return discount6;
    }

    public void setDiscount6(Double discount6) {
        this.discount6 = discount6;
    }

    public NearInstitution getInstitution() {
        return institution;
    }

    public void setInstitution(NearInstitution institution) {
        this.institution = institution;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }
}
