/*
 * Copyright Titan Industries Limited  All Rights Reserved. * 
 * This code is written by Enteg Info tech Private Limited for the Titan Eye+ Project * 
 *
 * 
 * 
 * VERSION
 * Initial Version
 * Date of Release
 * 
 * 
 * Change History
 * Version <vvv>
 * Date of Release <dd/mm/yyyy>
 * To be filled by the code Developer in Future
 * 
 * 
 * USAGE
 * This is Clinical History POJO for Clinical History Data
 * 
 * 
 */

package ISRetail.inquiry;

import java.io.Serializable;


public class ClinicalHistoryPOJO implements  Serializable{
    
//table fields-start
private String clinicalhistoryslno;
private String examinedby;
private String pasthistory;
private String comments;
private String slitlampexamination;
//table fields-end


private boolean chHasData;

private String customercode;
private String customername;
private String datasheetno;
private String pre_gp_right_dist_sph;
private String pre_gp_right_dist_cyl;
private String pre_gp_right_dist_axis;
private String pre_gp_right_dist_prism;
private String pre_gp_right_dist_base;
private String pre_gp_right_dist_va;
private String pre_gp_right_near_sph;
private String pre_gp_right_near_cyl;
private String pre_gp_right_near_axis;
private String pre_gp_right_near_prism;
private String pre_gp_right_near_base;
private String pre_gp_right_near_va;
private String pre_cl_right_brand;
private String pre_cl_right_bc;
private String pre_cl_right_dia;
private String pre_cl_right_sph;
private String pre_cl_right_cyl;
private String pre_cl_right_axis;
private String pre_cl_right_add;
private String pre_cl_right_tint;
private String pre_cl_right_oz;
private String pre_cl_right_va;
private String auto_refr_right;
private String retino_right;
private String kerato_right;
private String cyclo_ar_right;
private String cyclo_refr_right;
private String fundus_media_right;
private String iop_right;
private String eom;
private String covertest;
private String right_stockno;

private String pre_gp_left_dist_sph;
private String pre_gp_left_dist_cyl;
private String pre_gp_left_dist_axis;
private String pre_gp_left_dist_prism;
private String pre_gp_left_dist_base;
private String pre_gp_left_dist_va;
private String pre_gp_left_near_sph;
private String pre_gp_left_near_cyl;
private String pre_gp_left_near_axis;
private String pre_gp_left_near_prism;
private String pre_gp_left_near_base;
private String pre_gp_left_near_va;
private String pre_cl_left_brand;
private String pre_cl_left_bc;
private String pre_cl_left_dia;
private String pre_cl_left_sph;
private String pre_cl_left_cyl;
private String pre_cl_left_axis;
private String pre_cl_left_add;
private String pre_cl_left_tint;
private String pre_cl_left_oz;
private String pre_cl_left_va;
private String auto_refr_left;
private String retino_left;
private String kerato_left;
private String cyclo_ar_left;
private String cyclo_refr_left;
private String fundus_media_left;
private String iop_left;

    public String getClinicalhistoryslno() {
        return clinicalhistoryslno;
    }

    public void setClinicalhistoryslno(String clinicalhistoryslno) {
        this.clinicalhistoryslno = clinicalhistoryslno;
    }

    public String getExaminedby() {
        return examinedby;
    }

    public void setExaminedby(String examinedby) {
        this.examinedby = examinedby;
    }

    public String getPasthistory() {
        return pasthistory;
    }

    public void setPasthistory(String pasthistory) {
        this.pasthistory = pasthistory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSlitlampexamination() {
        return slitlampexamination;
    }

    public void setSlitlampexamination(String slitlampexamination) {
        this.slitlampexamination = slitlampexamination;
    }

    public boolean isChHasData() {
        return chHasData;
    }

    public void setChHasData(boolean chHasData) {
        this.chHasData = chHasData;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

    public String getPre_gp_right_dist_sph() {
        return pre_gp_right_dist_sph;
    }

    public void setPre_gp_right_dist_sph(String pre_gp_right_dist_sph) {
        this.pre_gp_right_dist_sph = pre_gp_right_dist_sph;
    }

    public String getPre_gp_right_dist_cyl() {
        return pre_gp_right_dist_cyl;
    }

    public void setPre_gp_right_dist_cyl(String pre_gp_right_dist_cyl) {
        this.pre_gp_right_dist_cyl = pre_gp_right_dist_cyl;
    }

    public String getPre_gp_right_dist_axis() {
        return pre_gp_right_dist_axis;
    }

    public void setPre_gp_right_dist_axis(String pre_gp_right_dist_axis) {
        this.pre_gp_right_dist_axis = pre_gp_right_dist_axis;
    }

    public String getPre_gp_right_dist_prism() {
        return pre_gp_right_dist_prism;
    }

    public void setPre_gp_right_dist_prism(String pre_gp_right_dist_prism) {
        this.pre_gp_right_dist_prism = pre_gp_right_dist_prism;
    }

    public String getPre_gp_right_dist_base() {
        return pre_gp_right_dist_base;
    }

    public void setPre_gp_right_dist_base(String pre_gp_right_dist_base) {
        this.pre_gp_right_dist_base = pre_gp_right_dist_base;
    }

    public String getPre_gp_right_dist_va() {
        return pre_gp_right_dist_va;
    }

    public void setPre_gp_right_dist_va(String pre_gp_right_dist_va) {
        this.pre_gp_right_dist_va = pre_gp_right_dist_va;
    }

    public String getPre_gp_right_near_sph() {
        return pre_gp_right_near_sph;
    }

    public void setPre_gp_right_near_sph(String pre_gp_right_near_sph) {
        this.pre_gp_right_near_sph = pre_gp_right_near_sph;
    }

    public String getPre_gp_right_near_cyl() {
        return pre_gp_right_near_cyl;
    }

    public void setPre_gp_right_near_cyl(String pre_gp_right_near_cyl) {
        this.pre_gp_right_near_cyl = pre_gp_right_near_cyl;
    }

    public String getPre_gp_right_near_axis() {
        return pre_gp_right_near_axis;
    }

    public void setPre_gp_right_near_axis(String pre_gp_right_near_axis) {
        this.pre_gp_right_near_axis = pre_gp_right_near_axis;
    }

    public String getPre_gp_right_near_prism() {
        return pre_gp_right_near_prism;
    }

    public void setPre_gp_right_near_prism(String pre_gp_right_near_prism) {
        this.pre_gp_right_near_prism = pre_gp_right_near_prism;
    }

    public String getPre_gp_right_near_base() {
        return pre_gp_right_near_base;
    }

    public void setPre_gp_right_near_base(String pre_gp_right_near_base) {
        this.pre_gp_right_near_base = pre_gp_right_near_base;
    }

    public String getPre_gp_right_near_va() {
        return pre_gp_right_near_va;
    }

    public void setPre_gp_right_near_va(String pre_gp_right_near_va) {
        this.pre_gp_right_near_va = pre_gp_right_near_va;
    }

    public String getPre_cl_right_brand() {
        return pre_cl_right_brand;
    }

    public void setPre_cl_right_brand(String pre_cl_right_brand) {
        this.pre_cl_right_brand = pre_cl_right_brand;
    }

    public String getPre_cl_right_bc() {
        return pre_cl_right_bc;
    }

    public void setPre_cl_right_bc(String pre_cl_right_bc) {
        this.pre_cl_right_bc = pre_cl_right_bc;
    }

    public String getPre_cl_right_dia() {
        return pre_cl_right_dia;
    }

    public void setPre_cl_right_dia(String pre_cl_right_dia) {
        this.pre_cl_right_dia = pre_cl_right_dia;
    }

    public String getPre_cl_right_sph() {
        return pre_cl_right_sph;
    }

    public void setPre_cl_right_sph(String pre_cl_right_sph) {
        this.pre_cl_right_sph = pre_cl_right_sph;
    }

    public String getPre_cl_right_cyl() {
        return pre_cl_right_cyl;
    }

    public void setPre_cl_right_cyl(String pre_cl_right_cyl) {
        this.pre_cl_right_cyl = pre_cl_right_cyl;
    }

    public String getPre_cl_right_axis() {
        return pre_cl_right_axis;
    }

    public void setPre_cl_right_axis(String pre_cl_right_axis) {
        this.pre_cl_right_axis = pre_cl_right_axis;
    }

    public String getPre_cl_right_add() {
        return pre_cl_right_add;
    }

    public void setPre_cl_right_add(String pre_cl_right_add) {
        this.pre_cl_right_add = pre_cl_right_add;
    }

    public String getPre_cl_right_tint() {
        return pre_cl_right_tint;
    }

    public void setPre_cl_right_tint(String pre_cl_right_tint) {
        this.pre_cl_right_tint = pre_cl_right_tint;
    }

    public String getPre_cl_right_oz() {
        return pre_cl_right_oz;
    }

    public void setPre_cl_right_oz(String pre_cl_right_oz) {
        this.pre_cl_right_oz = pre_cl_right_oz;
    }

    public String getPre_cl_right_va() {
        return pre_cl_right_va;
    }

    public void setPre_cl_right_va(String pre_cl_right_va) {
        this.pre_cl_right_va = pre_cl_right_va;
    }

    public String getAuto_refr_right() {
        return auto_refr_right;
    }

    public void setAuto_refr_right(String auto_refr_right) {
        this.auto_refr_right = auto_refr_right;
    }

    public String getRetino_right() {
        return retino_right;
    }

    public void setRetino_right(String retino_right) {
        this.retino_right = retino_right;
    }

    public String getKerato_right() {
        return kerato_right;
    }

    public void setKerato_right(String kerato_right) {
        this.kerato_right = kerato_right;
    }

    public String getCyclo_ar_right() {
        return cyclo_ar_right;
    }

    public void setCyclo_ar_right(String cyclo_ar_right) {
        this.cyclo_ar_right = cyclo_ar_right;
    }

    public String getCyclo_refr_right() {
        return cyclo_refr_right;
    }

    public void setCyclo_refr_right(String cyclo_refr_right) {
        this.cyclo_refr_right = cyclo_refr_right;
    }

    public String getFundus_media_right() {
        return fundus_media_right;
    }

    public void setFundus_media_right(String fundus_media_right) {
        this.fundus_media_right = fundus_media_right;
    }

    public String getIop_right() {
        return iop_right;
    }

    public void setIop_right(String iop_right) {
        this.iop_right = iop_right;
    }

    public String getEom() {
        return eom;
    }

    public void setEom(String eom) {
        this.eom = eom;
    }

    public String getCovertest() {
        return covertest;
    }

    public void setCovertest(String covertest) {
        this.covertest = covertest;
    }

    public String getRight_stockno() {
        return right_stockno;
    }

    public void setRight_stockno(String right_stockno) {
        this.right_stockno = right_stockno;
    }

    public String getPre_gp_left_dist_sph() {
        return pre_gp_left_dist_sph;
    }

    public void setPre_gp_left_dist_sph(String pre_gp_left_dist_sph) {
        this.pre_gp_left_dist_sph = pre_gp_left_dist_sph;
    }

    public String getPre_gp_left_dist_cyl() {
        return pre_gp_left_dist_cyl;
    }

    public void setPre_gp_left_dist_cyl(String pre_gp_left_dist_cyl) {
        this.pre_gp_left_dist_cyl = pre_gp_left_dist_cyl;
    }

    public String getPre_gp_left_dist_axis() {
        return pre_gp_left_dist_axis;
    }

    public void setPre_gp_left_dist_axis(String pre_gp_left_dist_axis) {
        this.pre_gp_left_dist_axis = pre_gp_left_dist_axis;
    }

    public String getPre_gp_left_dist_prism() {
        return pre_gp_left_dist_prism;
    }

    public void setPre_gp_left_dist_prism(String pre_gp_left_dist_prism) {
        this.pre_gp_left_dist_prism = pre_gp_left_dist_prism;
    }

    public String getPre_gp_left_dist_base() {
        return pre_gp_left_dist_base;
    }

    public void setPre_gp_left_dist_base(String pre_gp_left_dist_base) {
        this.pre_gp_left_dist_base = pre_gp_left_dist_base;
    }

    public String getPre_gp_left_dist_va() {
        return pre_gp_left_dist_va;
    }

    public void setPre_gp_left_dist_va(String pre_gp_left_dist_va) {
        this.pre_gp_left_dist_va = pre_gp_left_dist_va;
    }

    public String getPre_gp_left_near_sph() {
        return pre_gp_left_near_sph;
    }

    public void setPre_gp_left_near_sph(String pre_gp_left_near_sph) {
        this.pre_gp_left_near_sph = pre_gp_left_near_sph;
    }

    public String getPre_gp_left_near_cyl() {
        return pre_gp_left_near_cyl;
    }

    public void setPre_gp_left_near_cyl(String pre_gp_left_near_cyl) {
        this.pre_gp_left_near_cyl = pre_gp_left_near_cyl;
    }

    public String getPre_gp_left_near_axis() {
        return pre_gp_left_near_axis;
    }

    public void setPre_gp_left_near_axis(String pre_gp_left_near_axis) {
        this.pre_gp_left_near_axis = pre_gp_left_near_axis;
    }

    public String getPre_gp_left_near_prism() {
        return pre_gp_left_near_prism;
    }

    public void setPre_gp_left_near_prism(String pre_gp_left_near_prism) {
        this.pre_gp_left_near_prism = pre_gp_left_near_prism;
    }

    public String getPre_gp_left_near_base() {
        return pre_gp_left_near_base;
    }

    public void setPre_gp_left_near_base(String pre_gp_left_near_base) {
        this.pre_gp_left_near_base = pre_gp_left_near_base;
    }

    public String getPre_gp_left_near_va() {
        return pre_gp_left_near_va;
    }

    public void setPre_gp_left_near_va(String pre_gp_left_near_va) {
        this.pre_gp_left_near_va = pre_gp_left_near_va;
    }

    public String getPre_cl_left_brand() {
        return pre_cl_left_brand;
    }

    public void setPre_cl_left_brand(String pre_cl_left_brand) {
        this.pre_cl_left_brand = pre_cl_left_brand;
    }

    public String getPre_cl_left_bc() {
        return pre_cl_left_bc;
    }

    public void setPre_cl_left_bc(String pre_cl_left_bc) {
        this.pre_cl_left_bc = pre_cl_left_bc;
    }

    public String getPre_cl_left_dia() {
        return pre_cl_left_dia;
    }

    public void setPre_cl_left_dia(String pre_cl_left_dia) {
        this.pre_cl_left_dia = pre_cl_left_dia;
    }

    public String getPre_cl_left_sph() {
        return pre_cl_left_sph;
    }

    public void setPre_cl_left_sph(String pre_cl_left_sph) {
        this.pre_cl_left_sph = pre_cl_left_sph;
    }

    public String getPre_cl_left_cyl() {
        return pre_cl_left_cyl;
    }

    public void setPre_cl_left_cyl(String pre_cl_left_cyl) {
        this.pre_cl_left_cyl = pre_cl_left_cyl;
    }

    public String getPre_cl_left_axis() {
        return pre_cl_left_axis;
    }

    public void setPre_cl_left_axis(String pre_cl_left_axis) {
        this.pre_cl_left_axis = pre_cl_left_axis;
    }

    public String getPre_cl_left_add() {
        return pre_cl_left_add;
    }

    public void setPre_cl_left_add(String pre_cl_left_add) {
        this.pre_cl_left_add = pre_cl_left_add;
    }

    public String getPre_cl_left_tint() {
        return pre_cl_left_tint;
    }

    public void setPre_cl_left_tint(String pre_cl_left_tint) {
        this.pre_cl_left_tint = pre_cl_left_tint;
    }

    public String getPre_cl_left_oz() {
        return pre_cl_left_oz;
    }

    public void setPre_cl_left_oz(String pre_cl_left_oz) {
        this.pre_cl_left_oz = pre_cl_left_oz;
    }

    public String getPre_cl_left_va() {
        return pre_cl_left_va;
    }

    public void setPre_cl_left_va(String pre_cl_left_va) {
        this.pre_cl_left_va = pre_cl_left_va;
    }

    public String getAuto_refr_left() {
        return auto_refr_left;
    }

    public void setAuto_refr_left(String auto_refr_left) {
        this.auto_refr_left = auto_refr_left;
    }

    public String getRetino_left() {
        return retino_left;
    }

    public void setRetino_left(String retino_left) {
        this.retino_left = retino_left;
    }

    public String getKerato_left() {
        return kerato_left;
    }

    public void setKerato_left(String kerato_left) {
        this.kerato_left = kerato_left;
    }

    public String getCyclo_ar_left() {
        return cyclo_ar_left;
    }

    public void setCyclo_ar_left(String cyclo_ar_left) {
        this.cyclo_ar_left = cyclo_ar_left;
    }

    public String getCyclo_refr_left() {
        return cyclo_refr_left;
    }

    public void setCyclo_refr_left(String cyclo_refr_left) {
        this.cyclo_refr_left = cyclo_refr_left;
    }

    public String getFundus_media_left() {
        return fundus_media_left;
    }

    public void setFundus_media_left(String fundus_media_left) {
        this.fundus_media_left = fundus_media_left;
    }

    public String getIop_left() {
        return iop_left;
    }

    public void setIop_left(String iop_left) {
        this.iop_left = iop_left;
    }
//private String eom_left;
//private String covertest_left;
  
  

}
