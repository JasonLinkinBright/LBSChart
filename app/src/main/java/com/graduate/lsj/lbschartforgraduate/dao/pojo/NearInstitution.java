package com.graduate.lsj.lbschartforgraduate.dao.pojo;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

/**
 * Created by lsj on 2016/3/29.
 */
public class NearInstitution extends BmobObject implements Serializable{

    private String institutionName;
    private String institutionArea;
    private String institutionType;
    private Integer typeID;
    private Double institutionLongitude;
    private Double institutionLatitude;
    private String institutionPic;
    private Double numberStar;

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public String getInstitutionArea() {
        return institutionArea;
    }

    public void setInstitutionArea(String institutionArea) {
        this.institutionArea = institutionArea;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public Integer getTypeID() {
        return typeID;
    }

    public void setTypeID(Integer typeID) {
        this.typeID = typeID;
    }

    public Double getInstitutionLongitude() {
        return institutionLongitude;
    }

    public void setInstitutionLongitude(Double institutionLongitude) {
        this.institutionLongitude = institutionLongitude;
    }

    public Double getInstitutionLatitude() {
        return institutionLatitude;
    }

    public void setInstitutionLatitude(Double institutionLatitude) {
        this.institutionLatitude = institutionLatitude;
    }

    public String getInstitutionPic() {
        return institutionPic;
    }

    public void setInstitutionPic(String institutionPic) {
        this.institutionPic = institutionPic;
    }

    public Double getNumberStar() {
        return numberStar;
    }

    public void setNumberStar(Double numberStar) {
        this.numberStar = numberStar;
    }
}
