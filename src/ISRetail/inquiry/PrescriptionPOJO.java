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
 * This is Prescription POJO for handling the Inquiry Data
 * 
 * 
 */

package ISRetail.inquiry;
import java.io.Serializable;
import java.sql.Date;
import java.util.Properties;

/**
 *
 * @author Administrator
 */
public class PrescriptionPOJO implements  Serializable{
    
    
private String prescriptionslno;
private String examinedby;
private String comments;
private String followup;
private int followupdate;
private String referred_to;
private String doctor_name;
private String doctor_contactno;
private String doctor_area;
private String doctor_city;
private String othername;

private String customercode;
private String customername;
private String datasheetno;
private String pres_verified;
private String pres_deviation;

private boolean prHasData; 

private String right_dist_sph;
private String right_dist_cyl;
private String right_dist_axis;
private String right_dist_prism;
private String right_dist_base;
private String right_dist_va;
private String right_inter_sph;
private String right_inter_cyl;
private String right_inter_axis;
private String right_inter_prism;
private String right_inter_base;
private String right_inter_va;
private String right_near_sph;
private String right_near_cyl;
private String right_near_axis;
private String right_near_prism;
private String right_near_base;
private String right_near_va;
private String right_addition;
private String left_dist_sph;
private String left_dist_cyl;
private String left_dist_axis;
private String left_dist_prism;
private String left_dist_base;
private String left_dist_va;
private String left_inter_sph;
private String left_inter_cyl;
private String left_inter_axis;
private String left_inter_prism;
private String left_inter_base;
private String left_inter_va;
private String left_near_sph;
private String left_near_cyl;
private String left_near_axis;
private String left_near_prism;
private String left_near_base;
private String left_near_va;
private String left_addition;
private String ar_ipd;
private String right_B_P_Dist;
private String right_B_P_Inter;
private String right_B_P_Near;
private String left_B_P_Dist;
private String left_B_P_Inter;
private String left_B_P_Near;


private String right_Sale;
private String left_Sale;


//Code added on July 2nd 2010 for special lens details
//Inorder to set the special lens values if its >=0 we make the default values to be -1
private String vmap;
private int bvd=-1;
private int panto=-1;
private int readingdist=-1;
private int faceformangle=-1;
private String nameinit;
private int fwd=-1;
private int nwd=-1;


//End of Code added on July 2nd 2010 for special lens details

//Code added on 26-05-2016 for lens design type details
private String lensDesignType;
private String R_vmap;
private String R_vbd;
private String R_panto;
private String R_nod;
private String R_ztilt;
private String R_fwd;
private String R_nwd;
private String R_etyp;
private String R_ftyp;
private String R_hbox;
private String R_vbox;
private String R_dbl;
private String R_ipd;
private String R_segdht;
private String R_fram;
private String R_corlen;
private String L_vmap;
private String L_vbd;
private String L_panto;
private String L_nod;
private String L_ztilt;
private String L_fwd;
private String L_nwd;
private String L_etyp;
private String L_ftyp;
private String L_hbox;
private String L_vbox;
private String L_dbl;
private String L_ipd;
private String L_segdht;
private String L_fram;
private String L_corlen;


//End of Code added on 26-05-2016 for lens design type details
private String R_L_TintCode;//Added by Balachander V on 30.9.2019 to set TINTCode

private String R_L_engrave;//Added by Balachander V on 10.3.2020 to get Engrave Value

private String R_L_powerType;//Added by Balachander V on 16.5.2022 to get Power type value

    public String getPrescriptionslno() {
        return prescriptionslno;
    }

    public void setPrescriptionslno(String prescriptionslno) {
        this.prescriptionslno = prescriptionslno;
    }

    public String getExaminedby() {
        return examinedby;
    }

    public void setExaminedby(String examinedby) {
        this.examinedby = examinedby;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFollowup() {
        return followup;
    }

    public void setFollowup(String followup) {
        this.followup = followup;
    }

    public int getFollowupdate() {
        return followupdate;
    }

    public void setFollowupdate(int followupdate) {
        this.followupdate = followupdate;
    }

    public String getReferred_to() {
        return referred_to;
    }

    public void setReferred_to(String referred_to) {
        this.referred_to = referred_to;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getDoctor_contactno() {
        return doctor_contactno;
    }

    public void setDoctor_contactno(String doctor_contactno) {
        this.doctor_contactno = doctor_contactno;
    }

    public String getDoctor_area() {
        return doctor_area;
    }

    public void setDoctor_area(String doctor_area) {
        this.doctor_area = doctor_area;
    }

    public String getDoctor_city() {
        return doctor_city;
    }

    public void setDoctor_city(String doctor_city) {
        this.doctor_city = doctor_city;
    }

    public String getOthername() {
        return othername;
    }

    public void setOthername(String othername) {
        this.othername = othername;
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

    public String getPres_verify() {
        return pres_verified;
    }

    public void setPres_verify(String pres_verified) {
        this.pres_verified = pres_verified;
    }

    public String getPres_deviation() {
        return pres_deviation;
    }

    public void setPres_deviation(String pres_deviation) {
        this.pres_deviation = pres_deviation;
    }

    
    public String getRight_dist_sph() {
        return right_dist_sph;
    }

    public void setRight_dist_sph(String right_dist_sph) {
        this.right_dist_sph = right_dist_sph;
    }

    public String getRight_dist_cyl() {
        return right_dist_cyl;
    }

    public void setRight_dist_cyl(String right_dist_cyl) {
        this.right_dist_cyl = right_dist_cyl;
    }

    public String getRight_dist_axis() {
        return right_dist_axis;
    }

    public void setRight_dist_axis(String right_dist_axis) {
        this.right_dist_axis = right_dist_axis;
    }

    public String getRight_dist_prism() {
        return right_dist_prism;
    }

    public void setRight_dist_prism(String right_dist_prism) {
        this.right_dist_prism = right_dist_prism;
    }

    public String getRight_dist_base() {
        return right_dist_base;
    }

    public void setRight_dist_base(String right_dist_base) {
        this.right_dist_base = right_dist_base;
    }

    public String getRight_dist_va() {
        return right_dist_va;
    }

    public void setRight_dist_va(String right_dist_va) {
        this.right_dist_va = right_dist_va;
    }

    public String getRight_inter_sph() {
        return right_inter_sph;
    }

    public void setRight_inter_sph(String right_inter_sph) {
        this.right_inter_sph = right_inter_sph;
    }

    public String getRight_inter_cyl() {
        return right_inter_cyl;
    }

    public void setRight_inter_cyl(String right_inter_cyl) {
        this.right_inter_cyl = right_inter_cyl;
    }

    public String getRight_inter_axis() {
        return right_inter_axis;
    }

    public void setRight_inter_axis(String right_inter_axis) {
        this.right_inter_axis = right_inter_axis;
    }

    public String getRight_inter_prism() {
        return right_inter_prism;
    }

    public void setRight_inter_prism(String right_inter_prism) {
        this.right_inter_prism = right_inter_prism;
    }

    public String getRight_inter_base() {
        return right_inter_base;
    }

    public void setRight_inter_base(String right_inter_base) {
        this.right_inter_base = right_inter_base;
    }

    public String getRight_inter_va() {
        return right_inter_va;
    }

    public void setRight_inter_va(String right_inter_va) {
        this.right_inter_va = right_inter_va;
    }

    public String getRight_near_sph() {
        return right_near_sph;
    }

    public void setRight_near_sph(String right_near_sph) {
        this.right_near_sph = right_near_sph;
    }

    public String getRight_near_cyl() {
        return right_near_cyl;
    }

    public void setRight_near_cyl(String right_near_cyl) {
        this.right_near_cyl = right_near_cyl;
    }

    public String getRight_near_axis() {
        return right_near_axis;
    }

    public void setRight_near_axis(String right_near_axis) {
        this.right_near_axis = right_near_axis;
    }

    public String getRight_near_prism() {
        return right_near_prism;
    }

    public void setRight_near_prism(String right_near_prism) {
        this.right_near_prism = right_near_prism;
    }

    public String getRight_near_base() {
        return right_near_base;
    }

    public void setRight_near_base(String right_near_base) {
        this.right_near_base = right_near_base;
    }

    public String getRight_near_va() {
        return right_near_va;
    }

    public void setRight_near_va(String right_near_va) {
        this.right_near_va = right_near_va;
    }

    public String getRight_addition() {
        return right_addition;
    }

    public void setRight_addition(String right_addition) {
        this.right_addition = right_addition;
    }

    public String getLeft_dist_sph() {
        return left_dist_sph;
    }

    public void setLeft_dist_sph(String left_dist_sph) {
        this.left_dist_sph = left_dist_sph;
    }

    public String getLeft_dist_cyl() {
        return left_dist_cyl;
    }

    public void setLeft_dist_cyl(String left_dist_cyl) {
        this.left_dist_cyl = left_dist_cyl;
    }

    public String getLeft_dist_axis() {
        return left_dist_axis;
    }

    public void setLeft_dist_axis(String left_dist_axis) {
        this.left_dist_axis = left_dist_axis;
    }

    public String getLeft_dist_prism() {
        return left_dist_prism;
    }

    public void setLeft_dist_prism(String left_dist_prism) {
        this.left_dist_prism = left_dist_prism;
    }

    public String getLeft_dist_base() {
        return left_dist_base;
    }

    public void setLeft_dist_base(String left_dist_base) {
        this.left_dist_base = left_dist_base;
    }

    public String getLeft_dist_va() {
        return left_dist_va;
    }

    public void setLeft_dist_va(String left_dist_va) {
        this.left_dist_va = left_dist_va;
    }

    public String getLeft_inter_sph() {
        return left_inter_sph;
    }

    public void setLeft_inter_sph(String left_inter_sph) {
        this.left_inter_sph = left_inter_sph;
    }

    public String getLeft_inter_cyl() {
        return left_inter_cyl;
    }

    public void setLeft_inter_cyl(String left_inter_cyl) {
        this.left_inter_cyl = left_inter_cyl;
    }

    public String getLeft_inter_axis() {
        return left_inter_axis;
    }

    public void setLeft_inter_axis(String left_inter_axis) {
        this.left_inter_axis = left_inter_axis;
    }

    public String getLeft_inter_prism() {
        return left_inter_prism;
    }

    public void setLeft_inter_prism(String left_inter_prism) {
        this.left_inter_prism = left_inter_prism;
    }

    public String getLeft_inter_base() {
        return left_inter_base;
    }

    public void setLeft_inter_base(String left_inter_base) {
        this.left_inter_base = left_inter_base;
    }

    public String getLeft_inter_va() {
        return left_inter_va;
    }

    public void setLeft_inter_va(String left_inter_va) {
        this.left_inter_va = left_inter_va;
    }

    public String getLeft_near_sph() {
        return left_near_sph;
    }

    public void setLeft_near_sph(String left_near_sph) {
        this.left_near_sph = left_near_sph;
    }

    public String getLeft_near_cyl() {
        return left_near_cyl;
    }

    public void setLeft_near_cyl(String left_near_cyl) {
        this.left_near_cyl = left_near_cyl;
    }

    public String getLeft_near_axis() {
        return left_near_axis;
    }

    public void setLeft_near_axis(String left_near_axis) {
        this.left_near_axis = left_near_axis;
    }

    public String getLeft_near_prism() {
        return left_near_prism;
    }

    public void setLeft_near_prism(String left_near_prism) {
        this.left_near_prism = left_near_prism;
    }

    public String getLeft_near_base() {
        return left_near_base;
    }

    public void setLeft_near_base(String left_near_base) {
        this.left_near_base = left_near_base;
    }

    public String getLeft_near_va() {
        return left_near_va;
    }

    public void setLeft_near_va(String left_near_va) {
        this.left_near_va = left_near_va;
    }

    public String getLeft_addition() {
        return left_addition;
    }

    public void setLeft_addition(String left_addition) {
        this.left_addition = left_addition;
    }

    public String getAr_ipd() {
        return ar_ipd;
    }

    public void setAr_ipd(String ar_ipd) {
        this.ar_ipd = ar_ipd;
    }

  

   

    public boolean isPrHasData() {
        return prHasData;
    }

    public void setPrHasData(boolean prHasData) {
        this.prHasData = prHasData;
    }

    public String getRight_B_P_Dist() {
        return right_B_P_Dist;
    }

    public void setRight_B_P_Dist(String right_B_P_Dist) {
        this.right_B_P_Dist = right_B_P_Dist;
    }

    public String getRight_B_P_Inter() {
        return right_B_P_Inter;
    }

    public void setRight_B_P_Inter(String right_B_P_Inter) {
        this.right_B_P_Inter = right_B_P_Inter;
    }

    public String getRight_B_P_Near() {
        return right_B_P_Near;
    }

    public void setRight_B_P_Near(String right_B_P_Near) {
        this.right_B_P_Near = right_B_P_Near;
    }

    public String getRight_Sale() {
        return right_Sale;
    }

    public void setRight_Sale(String right_Sale) {
        this.right_Sale = right_Sale;
    }

    public String getLeft_Sale() {
        return left_Sale;
    }

    public void setLeft_Sale(String left_Sale) {
        this.left_Sale = left_Sale;
    }

    public String getLeft_B_P_Dist() {
        return left_B_P_Dist;
    }

    public void setLeft_B_P_Dist(String left_B_P_Dist) {
        this.left_B_P_Dist = left_B_P_Dist;
    }

    public String getLeft_B_P_Inter() {
        return left_B_P_Inter;
    }

    public void setLeft_B_P_Inter(String left_B_P_Inter) {
        this.left_B_P_Inter = left_B_P_Inter;
    }

    public String getLeft_B_P_Near() {
        return left_B_P_Near;
    }

    public void setLeft_B_P_Near(String left_B_P_Near) {
        this.left_B_P_Near = left_B_P_Near;
    }

    public String getVmap() {
        return vmap;
    }

    public void setVmap(String vmap) {
        this.vmap = vmap;
    }

    public int getBvd() {
        return bvd;
    }

    public void setBvd(int bvd) {
        this.bvd = bvd;
    }

    public int getPanto() {
        return panto;
    }

    public void setPanto(int panto) {
        this.panto = panto;
    }

    public int getReadingdist() {
        return readingdist;
    }

    public void setReadingdist(int readingdist) {
        this.readingdist = readingdist;
    }

    public int getFaceformangle() {
        return faceformangle;
    }

    public void setFaceformangle(int faceformangle) {
        this.faceformangle = faceformangle;
    }

    public String getNameinit() {
        return nameinit;
    }

    public void setNameinit(String nameinit) {
        this.nameinit = nameinit;
    }

    public int getFwd() {
        return fwd;
    }

    public void setFwd(int fwd) {
        this.fwd = fwd;
    }

    public int getNwd() {
        return nwd;
    }

    public void setNwd(int nwd) {
        this.nwd = nwd;
    }

    
    
    public String getLensDesignType() {
        return lensDesignType;
    }

    public void setLensDesignType(String lensDesignType) {
        this.lensDesignType = lensDesignType;
    }

    
    
    public String getR_vmap() {
        return R_vmap;
    }

    public void setR_vmap(String R_vmap) {
        this.R_vmap = R_vmap;
    }

    public String getR_vbd() {
        return R_vbd;
    }

    public void setR_vbd(String R_vbd) {
        this.R_vbd = R_vbd;
    }

    public String getR_panto() {
        return R_panto;
    }

    public void setR_panto(String R_panto) {
        this.R_panto = R_panto;
    }

    public String getR_nod() {
        return R_nod;
    }

    public void setR_nod(String R_nod) {
        this.R_nod = R_nod;
    }

    public String getR_ztilt() {
        return R_ztilt;
    }

    public void setR_ztilt(String R_ztilt) {
        this.R_ztilt = R_ztilt;
    }

    public String getR_fwd() {
        return R_fwd;
    }

    public void setR_fwd(String R_fwd) {
        this.R_fwd = R_fwd;
    }

    public String getR_nwd() {
        return R_nwd;
    }

    public void setR_nwd(String R_nwd) {
        this.R_nwd = R_nwd;
    }

    public String getR_etyp() {
        return R_etyp;
    }

    public void setR_etyp(String R_etyp) {
        this.R_etyp = R_etyp;
    }

    public String getR_ftyp() {
        return R_ftyp;
    }

    public void setR_ftyp(String R_ftyp) {
        this.R_ftyp = R_ftyp;
    }

    public String getR_hbox() {
        return R_hbox;
    }

    public void setR_hbox(String R_hbox) {
        this.R_hbox = R_hbox;
    }

    public String getR_vbox() {
        return R_vbox;
    }

    public void setR_vbox(String R_vbox) {
        this.R_vbox = R_vbox;
    }

    public String getR_dbl() {
        return R_dbl;
    }

    public void setR_dbl(String R_dbl) {
        this.R_dbl = R_dbl;
    }

    public String getR_ipd() {
        return R_ipd;
    }

    public void setR_ipd(String R_ipd) {
        this.R_ipd = R_ipd;
    }

    public String getR_segdht() {
        return R_segdht;
    }

    public void setR_segdht(String R_segdht) {
        this.R_segdht = R_segdht;
    }

    public String getR_fram() {
        return R_fram;
    }

    public void setR_fram(String R_fram) {
        this.R_fram = R_fram;
    }

    public String getL_vmap() {
        return L_vmap;
    }

    public void setL_vmap(String L_vmap) {
        this.L_vmap = L_vmap;
    }

    public String getL_vbd() {
        return L_vbd;
    }

    public void setL_vbd(String L_vbd) {
        this.L_vbd = L_vbd;
    }

    public String getL_panto() {
        return L_panto;
    }

    public void setL_panto(String L_panto) {
        this.L_panto = L_panto;
    }

    public String getL_nod() {
        return L_nod;
    }

    public void setL_nod(String L_nod) {
        this.L_nod = L_nod;
    }

    public String getL_ztilt() {
        return L_ztilt;
    }

    public void setL_ztilt(String L_ztilt) {
        this.L_ztilt = L_ztilt;
    }

    public String getL_fwd() {
        return L_fwd;
    }

    public void setL_fwd(String L_fwd) {
        this.L_fwd = L_fwd;
    }

    public String getL_nwd() {
        return L_nwd;
    }

    public void setL_nwd(String L_nwd) {
        this.L_nwd = L_nwd;
    }

    public String getL_etyp() {
        return L_etyp;
    }

    public void setL_etyp(String L_etyp) {
        this.L_etyp = L_etyp;
    }

    public String getL_ftyp() {
        return L_ftyp;
    }

    public void setL_ftyp(String L_ftyp) {
        this.L_ftyp = L_ftyp;
    }

    public String getL_hbox() {
        return L_hbox;
    }

    public void setL_hbox(String L_hbox) {
        this.L_hbox = L_hbox;
    }

    public String getL_vbox() {
        return L_vbox;
    }

    public void setL_vbox(String L_vbox) {
        this.L_vbox = L_vbox;
    }

    public String getL_dbl() {
        return L_dbl;
    }

    public void setL_dbl(String L_dbl) {
        this.L_dbl = L_dbl;
    }

    public String getL_ipd() {
        return L_ipd;
    }

    public void setL_ipd(String L_ipd) {
        this.L_ipd = L_ipd;
    }

    public String getL_segdht() {
        return L_segdht;
    }

    public void setL_segdht(String L_segdht) {
        this.L_segdht = L_segdht;
    }

    public String getL_fram() {
        return L_fram;
    }

    public void setL_fram(String L_fram) {
        this.L_fram = L_fram;
    }

    public String getR_L_TintCode() {
        return R_L_TintCode;
    }

    public void setR_L_TintCode(String R_L_TintCode) {
        this.R_L_TintCode = R_L_TintCode;
    }

    public String getR_L_engrave() {
        return R_L_engrave;
    }

    public void setR_L_engrave(String R_L_engrave) {
        this.R_L_engrave = R_L_engrave;
    }

    public String getPres_verified() {
        return pres_verified;
    }

    public void setPres_verified(String pres_verified) {
        this.pres_verified = pres_verified;
    }

    public String getR_L_powerType() {
        return R_L_powerType;
    }

    public void setR_L_powerType(String R_L_powerType) {
        this.R_L_powerType = R_L_powerType;
    }

    public String getR_corlen() {
        return R_corlen;
    }

    public void setR_corlen(String R_corlen) {
        this.R_corlen = R_corlen;
    }

    public String getL_corlen() {
        return L_corlen;
    }

    public void setL_corlen(String L_corlen) {
        this.L_corlen = L_corlen;
    }
   

}
