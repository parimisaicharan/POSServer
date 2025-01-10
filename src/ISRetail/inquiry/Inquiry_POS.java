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
 * This class file is used to handle the Eye Characteristis for creating inquiry and sales order
 * 
 * 
 * 
 */
package ISRetail.inquiry;

import ISRetail.salesorder.SalesOrderHeaderPOJO;
import ISRetail.utility.validations.Validations;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author enteg
 */
public class Inquiry_POS {
    
    DecimalFormat df = new DecimalFormat("0.00");
    
    public static boolean hasObjectiveRefractionSideHasdata(ClinicalHistoryPOJO cliniPojo, String R_L) {
        boolean sideHasData = false;
        try {
            if (R_L != null) {
                if (R_L.trim().equalsIgnoreCase("R")) {
                    if (Validations.isFieldNotEmpty(cliniPojo.getAuto_refr_right()) || Validations.isFieldNotEmpty(cliniPojo.getRetino_right()) || Validations.isFieldNotEmpty(cliniPojo.getKerato_right()) || Validations.isFieldNotEmpty(cliniPojo.getCyclo_ar_right()) || Validations.isFieldNotEmpty(cliniPojo.getCyclo_refr_right()) || Validations.isFieldNotEmpty(cliniPojo.getFundus_media_right()) || Validations.isFieldNotEmpty(cliniPojo.getIop_right())) {
                        sideHasData = true;
                    }
                } else if (R_L.trim().equalsIgnoreCase("L")) {
                    if (Validations.isFieldNotEmpty(cliniPojo.getAuto_refr_left()) || Validations.isFieldNotEmpty(cliniPojo.getRetino_left()) || Validations.isFieldNotEmpty(cliniPojo.getKerato_left()) || Validations.isFieldNotEmpty(cliniPojo.getCyclo_ar_left()) || Validations.isFieldNotEmpty(cliniPojo.getCyclo_refr_left()) || Validations.isFieldNotEmpty(cliniPojo.getFundus_media_left()) || Validations.isFieldNotEmpty(cliniPojo.getIop_left())) {
                        sideHasData = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sideHasData;
    }
    
    public ArrayList<CharacteresticsPOJO> createCharacteristicPojo(InquiryPOJO inqPojo, SalesOrderHeaderPOJO soHeaderPojo, String scenarioType, ClinicalHistoryPOJO cliniPojo, PrescriptionPOJO specPojo, ContactLensPOJO clensPojo) {
        ArrayList<CharacteresticsPOJO> charPojoList = null;
        try { // Call Web Service Operation

            if (scenarioType != null) {
                charPojoList = new ArrayList<CharacteresticsPOJO>();
                CharacteresticsPOJO charPojo = null;
                String docNumber = null;
                if (inqPojo != null) {
                    docNumber = inqPojo.getInquiryno();
                } else if (soHeaderPojo != null) {
                    docNumber = soHeaderPojo.getSaleorderno();
                }

                /**
                 * **********CH RIGHT************
                 */
                if (cliniPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CH") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("CC"))) {
                    
                    Map CH_RightEyes = getAndsetCHCharacteristics(cliniPojo, "R");
                    if (CH_RightEyes != null) {
                        if (CH_RightEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator chRight = CH_RightEyes.keySet().iterator();
                            if (chRight != null) {
                                while (chRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(chRight.next());
                                        val = String.valueOf(CH_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("CH");
                                            charPojo.setSideindicator("R");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********CH LEFT************
                     */
                    Map CH_LeftEyes = getAndsetCHCharacteristics(cliniPojo, "L");
                    if (CH_LeftEyes != null) {
                        if (CH_LeftEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator chLeft = CH_LeftEyes.keySet().iterator();
                            if (chLeft != null) {
                                while (chLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(chLeft.next());
                                        val = String.valueOf(CH_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("CH");
                                            charPojo.setSideindicator("L");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (specPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("PR") || scenarioType.trim().equals("CP") || scenarioType.trim().equals("PC"))) {

                    /**
                     * **********PR RIGHT************
                     */
                    Map PR_RighttEyes = getAndsetPRCharacteristics(specPojo, soHeaderPojo, "R");
                    if (PR_RighttEyes != null) {
                        if (PR_RighttEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator prRight = PR_RighttEyes.keySet().iterator();
                            if (prRight != null) {
                                while (prRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(prRight.next());
                                        val = String.valueOf(PR_RighttEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("PR");
                                            charPojo.setSideindicator("R");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********PR LEFT************
                     */
                    Map PR_LeftEyes = getAndsetPRCharacteristics(specPojo, soHeaderPojo, "L");
                    if (PR_LeftEyes != null) {
                        if (PR_LeftEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator prLeft = PR_LeftEyes.keySet().iterator();
                            if (prLeft != null) {
                                while (prLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(prLeft.next());
                                        val = String.valueOf(PR_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("PR");
                                            charPojo.setSideindicator("L");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (clensPojo != null && (scenarioType.trim().equals("C3") || scenarioType.trim().equals("CL") || scenarioType.trim().equals("CC") || scenarioType.trim().equals("PC"))) {

                    /**
                     * *******CL RIGHT ****************
                     */
                    Map CL_RightEyes = getAndsetCLCharacteristics(clensPojo, "R");
                    if (CL_RightEyes != null) {
                        if (CL_RightEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator clRight = CL_RightEyes.keySet().iterator();
                            if (clRight != null) {
                                while (clRight.hasNext()) {
                                    try {
                                        desc = String.valueOf(clRight.next());
                                        val = String.valueOf(CL_RightEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("CL");
                                            charPojo.setSideindicator("R");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }

                    /**
                     * **********CL lEFT************
                     */
                    Map CL_LeftEyes = getAndsetCLCharacteristics(clensPojo, "L");
                    if (CL_LeftEyes != null) {
                        if (CL_LeftEyes.keySet() != null) {
                            String desc = null, val = null;
                            Iterator clLeft = CL_LeftEyes.keySet().iterator();
                            if (clLeft != null) {
                                while (clLeft.hasNext()) {
                                    try {
                                        desc = String.valueOf(clLeft.next());
                                        val = String.valueOf(CL_LeftEyes.get(desc));
                                        if (Validations.isFieldNotEmpty(val)) {
                                            charPojo = new CharacteresticsPOJO();
                                            charPojo.setDoctype("CL");
                                            charPojo.setSideindicator("L");
                                            charPojo.setDocnumber(docNumber);
                                            charPojo.setCharname(desc);
                                            charPojo.setCharvalue(val);
                                            charPojoList.add(charPojo);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
                
            }
        } catch (Exception ex) {
            // TODO handle custom exceptions here
            ex.printStackTrace();
        }
        return charPojoList;
    }
    
    public static Map getAndsetCHCharacteristics(ClinicalHistoryPOJO cliniPojo, String R_L) {
        Map CH_RightEyes = new LinkedHashMap();
        String charName = null;
        try {
            if (cliniPojo != null && R_L != null) {
                
                charName = "PRE_GLASS_PRES_DIST_SPH";//1
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_sph());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_sph());
                }//1

                charName = "PRE_GLASS_PRES_NEAR_SPH";//2
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_sph());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_sph());
                }//2

                charName = "PRE_GLASS_PRES_DIST_CYL";//3
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_cyl());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_cyl());
                }//3

                charName = "PRE_GLASS_PRES_NEAR_CYL";//4
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_cyl());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_cyl());
                }//4

                charName = "PRE_GLASS_PRES_DIST_AXIS";//5
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_axis());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_axis());
                } //5

                charName = "PRE_GLASS_PRES_NEAR_AXIS";//6
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_axis());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_axis());
                }//6

                charName = "PRE_GLASS_PRES_DIST_PRISM";//7
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_prism());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_prism());
                }//7

                charName = "PRE_GLASS_PRES_NEAR_PRISM";//8
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_prism());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_prism());
                }//8

                charName = "PRE_GLASS_PRES_DIST_BASE";//9
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_base());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_base());
                }//9

                charName = "PRE_GLASS_PRES_NEAR_BASE";//10
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_base());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_base());
                }//10

                charName = "PRE_GLASS_PRES_DIST_VA";//11
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_dist_va());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_dist_va());
                }//11
                charName = "PRE_GLASS_PRES_NEAR_VA";//12
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_right_near_va());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_gp_left_near_va());
                }//12

                charName = "PRE_CONT_LENS_BRAND";//13
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_brand());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_brand());
                }//13

                charName = "PRE_CONT_LENS_BC";//14
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put("PRE_CONT_LENS_BC", cliniPojo.getPre_cl_right_bc());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put("PRE_CONT_LENS_BC", cliniPojo.getPre_cl_left_bc());
                }//14

                charName = "PRE_CONT_LENS_DIA";//15
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_dia());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_dia());
                }//15

                charName = "PRE_CONT_LENS_POW_SPH";//16
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_sph());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_sph());
                }//16

                charName = "PRE_CONT_LENS_POW_CYL";//17
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_cyl());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_cyl());
                }//17

                charName = "PRE_CONT_LENS_POW_AXIS";//18
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_axis());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_axis());
                }//18

                charName = "PRE_CONT_LENS_POW_ADD";//19
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_add());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_add());
                }//19

                charName = "PRE_CONT_LENS_TINT";//20
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_tint());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_tint());
                }//20

                charName = "PRE_CONT_LENS_OZ";//21
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_oz());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_oz());
                }//21

                charName = "PRE_CONT_LENS_VA";//22
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_right_va());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getPre_cl_left_va());
                }//22

                charName = "AUTOREFRACTOMETRY_DRY";//23
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getAuto_refr_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getAuto_refr_left());
                }//23

                charName = "RETINOSCOPY_DRY";//24
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getRetino_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getRetino_left());
                }//24

                charName = "KERATOMETRY";//25
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put("KERATOMETRY", cliniPojo.getKerato_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put("KERATOMETRY", cliniPojo.getKerato_left());
                }//25

                charName = "CYLCLOPLEGIC_AR";//26
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getCyclo_ar_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getCyclo_ar_left());
                }//26

                charName = "CYLCLOPLEGIC_REFRACT";//27
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getCyclo_refr_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getCyclo_refr_left());
                }//27

                charName = "FUNDUS_AND_MEDIA";//28
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getFundus_media_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getFundus_media_left());
                }//28

                charName = "IOP_IN_MM_HG_NCT";//29
                if (R_L.equalsIgnoreCase("R")) {
                    CH_RightEyes.put(charName, cliniPojo.getIop_right());
                } else if (R_L.equalsIgnoreCase("L")) {
                    CH_RightEyes.put(charName, cliniPojo.getIop_left());
                }//29

                if (R_L.equalsIgnoreCase("R")) {
                    if (Validations.isFieldNotEmpty(cliniPojo.getPre_gp_right_dist_sph()) || Validations.isFieldNotEmpty(cliniPojo.getPre_gp_right_near_sph()) || Validations.isFieldNotEmpty(cliniPojo.getPre_cl_right_sph()) || hasObjectiveRefractionSideHasdata(cliniPojo, "R")) {
                        charName = "EOM";//30
                        CH_RightEyes.put(charName, cliniPojo.getEom());
                        charName = "COVERTEST";//31
                        CH_RightEyes.put(charName, cliniPojo.getCovertest());
                        
                    }
                }
                if (R_L.equalsIgnoreCase("L")) {
                    if (Validations.isFieldNotEmpty(cliniPojo.getPre_gp_left_dist_sph()) || Validations.isFieldNotEmpty(cliniPojo.getPre_gp_left_near_sph()) || Validations.isFieldNotEmpty(cliniPojo.getPre_cl_left_sph()) || hasObjectiveRefractionSideHasdata(cliniPojo, "L")) {
                        charName = "EOM";//30
                        CH_RightEyes.put(charName, cliniPojo.getEom());
                        charName = "COVERTEST";//31
                        CH_RightEyes.put(charName, cliniPojo.getCovertest());
                        
                    }
                }//30

                return CH_RightEyes;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ClinicalHistoryPOJO setCHPojoFromCharPojo(Connection con, String docNo, ClinicalHistoryPOJO cliniPojo) {
        try {
            CharacteristicDO charDo = new CharacteristicDO();
            Map charRightMapFrmDb = charDo.getCharacteristicsByDocNo(con, "CH", docNo, "R");
            Map charLeftMapFrmDb = charDo.getCharacteristicsByDocNo(con, "CH", docNo, "L");
            
            String charName = null;
            charName = "PRE_GLASS_PRES_DIST_SPH";//1
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_sph("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_SPH";//2
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_sph("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_DIST_CYL";//3
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_cyl("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_CYL";//4
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_cyl("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_DIST_AXIS";//5
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_axis("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_AXIS";//6
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_axis("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_DIST_PRISM";//7
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_prism("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_prism("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_PRISM";//8
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_prism("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_prism("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_DIST_BASE";//9
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_base("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_base("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_BASE";//10
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_base("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_base("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_DIST_VA";//11
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_dist_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_dist_va("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_GLASS_PRES_NEAR_VA";//12
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_right_near_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_gp_left_near_va("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_CONT_LENS_BRAND";//13
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_brand("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_brand("" + charLeftMapFrmDb.get(charName));
            }
            
            charName = "PRE_CONT_LENS_BC";//14
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_bc("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_bc("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_DIA";//15
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_dia("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_dia("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_POW_SPH";//16
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_POW_CYL";//17
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_POW_AXIS";//18
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_axis("" + charLeftMapFrmDb.get(charName));
            }
            /**
             * 1.08.2008 *******
             */
            charName = "PRE_CONT_LENS_POW_ADD";//19
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_add("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_add("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_TINT";//20
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_tint("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_tint("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_OZ";//21
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_oz("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_oz("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRE_CONT_LENS_VA";//22
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_right_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setPre_cl_left_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "AUTOREFRACTOMETRY_DRY";//23
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setAuto_refr_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setAuto_refr_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "RETINOSCOPY_DRY";//24
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setRetino_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setRetino_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "KERATOMETRY";//25
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setKerato_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setKerato_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CYLCLOPLEGIC_AR";//26
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setCyclo_ar_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setCyclo_ar_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CYLCLOPLEGIC_REFRACT";//27
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setCyclo_refr_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setCyclo_refr_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "FUNDUS_AND_MEDIA";//28
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setFundus_media_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setFundus_media_left("" + charLeftMapFrmDb.get(charName));
            }
            charName = "IOP_IN_MM_HG_NCT";//29
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setIop_right("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setIop_left("" + charLeftMapFrmDb.get(charName));
            }
            /**
             * *****DOUBT ***
             */
            charName = "EOM";//30
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setEom("" + charRightMapFrmDb.get(charName));
            } else if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setEom("" + charLeftMapFrmDb.get(charName));
            }
            charName = "COVERTEST";//31
            if (charRightMapFrmDb.containsKey(charName)) {
                cliniPojo.setCovertest("" + charRightMapFrmDb.get(charName));
            } else if (charLeftMapFrmDb.containsKey(charName)) {
                cliniPojo.setCovertest("" + charLeftMapFrmDb.get(charName));
            }
            /**
             * *****************************************
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliniPojo;
    }
    
    public static Map getAndsetPRCharacteristics(PrescriptionPOJO specPojo, SalesOrderHeaderPOJO soPojo, String R_L) {
        Map PR_LeftEyes = new LinkedHashMap();
        try {
            if (specPojo != null) {
                System.out.println("VMAP---" + specPojo.getVmap());
                String charName = "PRES_DIST_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_sph());//1
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_sph());//1
                }
                charName = "PRES_DIST_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_cyl());//2
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_cyl());//2
                }
                charName = "PRES_DIST_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_axis());//3
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_axis());//3
                }
                charName = "PRES_DIST_PRISM";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_prism());//4
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_prism());//4
                }
                charName = "PRES_DIST_BASE";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_base());
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_base());//5
                }
                charName = "PRES_DIST_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_dist_va());//6
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_dist_va());//6
                }
                charName = "PRES_INTER_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_sph());//7
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_sph());//7
                }
                charName = "PRES_INTER_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_cyl());//8
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_cyl());//8
                }
                charName = "PRES_INTER_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_axis());//9
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_axis());//9
                }
                charName = "PRES_INTER_PRISM";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_prism());//10
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_prism());//10
                }
                charName = "PRES_INTER_BASE";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_base());//11
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_base());//11
                }
                charName = "PRES_INTER_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_inter_va());//12\
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_inter_va());//12\
                }
                charName = "PRES_NEAR_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_sph());//13
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_sph());//13
                }
                charName = "PRES_NEAR_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_cyl());//14
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_cyl());//14
                }
                charName = "PRES_NEAR_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_axis());//15
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_axis());//15
                }
                charName = "PRES_NEAR_PRISM";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_prism());//16
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_prism());//16
                }
                charName = "PRES_NEAR_BASE";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_base());//17
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_base());//17
                }
                charName = "PRES_NEAR_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_near_va());//18
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_near_va());//18
                }
                charName = "PRES_ADDITION_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getRight_addition());//19
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getLeft_addition());//19
                }
                charName = "PRES_TINT";//Added by Balachander V on 30.9.2019 to send TINTCode to SAP
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_TintCode());
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_TintCode());
                }
                charName = "PRES_ENGRAVE";//Added by Balachander V on 10.3.2020 to send Engrave value to SAP
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_engrave());
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_engrave());
                }
                charName = "PRES_PROFILE";//Added by Balachander V on 10.3.2020 to send Prescription Power value to SAP
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_powerType());
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getR_L_powerType());
                }
                charName = "PRES_CORLEN";//Added by Balachander V on 10.3.2020 to send CORLength value to SAP
                if (R_L.equalsIgnoreCase("R")) {
                    PR_LeftEyes.put(charName, specPojo.getR_corlen());
                } else if (R_L.equalsIgnoreCase("L")) {
                    PR_LeftEyes.put(charName, specPojo.getL_corlen());
                }
                if (R_L.equalsIgnoreCase("R")) {
                    if (specPojo.getRight_Sale() != null) {
                        charName = "PRES_SALE";
                        PR_LeftEyes.put(charName, specPojo.getRight_Sale());
                        charName = "PRES_BP";
                        if (specPojo.getRight_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter())) {
                                PR_LeftEyes.put(charName, "DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                PR_LeftEyes.put(charName, "DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getRight_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getRight_B_P_Near())) {
                                PR_LeftEyes.put(charName, "IN");
                            }
                        }//21
                    }
                    
                }
                if (R_L.equalsIgnoreCase("L")) {
                    if (specPojo.getLeft_Sale() != null) {
                        charName = "PRES_SALE";
                        PR_LeftEyes.put(charName, specPojo.getLeft_Sale());
                        charName = "PRES_BP";
                        if (specPojo.getLeft_Sale().trim().equalsIgnoreCase("BP")) {
                            if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter())) {
                                PR_LeftEyes.put(charName, "DI");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Dist()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                PR_LeftEyes.put(charName, "DN");
                            }//21
                            else if (Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Inter()) && Validations.isFieldNotEmpty(specPojo.getLeft_B_P_Near())) {
                                PR_LeftEyes.put(charName, "IN");
                            }
                        }//21
                    }
                    
                }
                charName = "PRES_AR_IPD";
                if (R_L.equalsIgnoreCase("R")) {
                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                        PR_LeftEyes.put(charName, specPojo.getAr_ipd());//22
                    }
                }
                if (R_L.equalsIgnoreCase("L")) {
                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                        PR_LeftEyes.put(charName, specPojo.getAr_ipd());//22

                    }
                }

                //SO POJO
                if (soPojo != null) {
                    charName = "PRES_SEGMENTSIZE";
                    if (R_L.equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getSegmentSize());//23
                        }
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getSegmentSize());//23

                        }
                    }
                    charName = "PRES_SEGMENTHEIGHT";
                    if (R_L.equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getSegmentHeight());//23
                        }
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getSegmentHeight());//23
                        }
                    }
                    
                    charName = "PRES_ED";
                    if (R_L.equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getEd());//23
                        }
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getEd());//23
                        }
                    }
                    charName = "PRES_DIST_PD";
                    if (R_L.equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getDistancepd());//23
                        }
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getDistancepd());//23
                        }
                    }
                    charName = "PRES_NEAR_PD";
                    ;
                    if (R_L.equalsIgnoreCase("R")) {
                        if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getNearpd());//23
                        }
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
                            PR_LeftEyes.put(charName, soPojo.getNearpd());//23
                        }
                    }
                    
                }//sopojo end

                //Code added on July 2nd 2010 for capturing the special lens details
                //Signature
                //code hided by Thangaraj for adding lens design type
//                charName = "PRES_VMAP";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                       if(specPojo.getVmap()!=null && specPojo.getVmap().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getVmap());//23
//                       }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                        if(specPojo.getVmap()!=null && specPojo.getVmap().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getVmap());//23
//                        }
//                    }
//                }
//                 charName = "PRES_BVD";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                         if(specPojo.getBvd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getBvd());//24
//                         }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                         if(specPojo.getBvd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getBvd());//24
//                         }
//                    }
//                }
//                charName = "PRES_PANTO";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                        if(specPojo.getPanto()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getPanto());//25
//                        }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                         if(specPojo.getPanto()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getPanto());//25
//                         }
//                    }
//                }
//                 charName = "PRES_READDIST";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                        if(specPojo.getReadingdist()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getReadingdist());//25
//                        }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                           if(specPojo.getReadingdist()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getReadingdist());//25
//                           }
//                    }
//                }
//                charName = "PRES_FFA";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                           if(specPojo.getFaceformangle()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getFaceformangle());//25
//                           }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                         if(specPojo.getFaceformangle()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getFaceformangle());//25
//                         }
//
//                    }
//                }
//                 charName = "PRES_INITIAL";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                         if(specPojo.getNameinit()!=null && specPojo.getNameinit().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getNameinit());//25
//                         }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                        if(specPojo.getNameinit()!=null && specPojo.getNameinit().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getNameinit());//25
//                        }
//                    }
//                }
                //code hided by Thangaraj for adding lens design type
                //Activ lens
                //code hided by Thangaraj for adding lens design type
//                charName = "PRES_FWD";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                         if(specPojo.getFwd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getFwd());//25
//                         }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                        if(specPojo.getFwd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getFwd());//25
//                        }
//
//                    }
//                }
//                 charName = "PRES_NWD";
//                if (R_L.equalsIgnoreCase("R")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getRight_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getRight_near_sph())) {
//                         if(specPojo.getNwd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getNwd());//25
//                         }
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if (Validations.isFieldNotEmpty(specPojo.getLeft_dist_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_inter_sph()) || Validations.isFieldNotEmpty(specPojo.getLeft_near_sph())) {
//                        if(specPojo.getNwd()>=0){
//                        PR_LeftEyes.put(charName, specPojo.getNwd());//25
//                        }
//
//                    }
//                }
//
//                
                //End of Code added on July 2nd 2010 for capturing the special lens details
//                charName = "PRES_DESIGNTYPE";
//                if (R_L.equalsIgnoreCase("R")) {                    
//                       if(specPojo.getLensDesignType()!=null && specPojo.getLensDesignType().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getVmap());//23                    
//                    }
//                }
//                 charName = "PRES_VMAP";
//                if (R_L.equalsIgnoreCase("R")) {                    
//                       if(specPojo.getVmap()!=null && specPojo.getVmap().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getVmap());//23                    
//                    }
//                }
//                if (R_L.equalsIgnoreCase("L")) {
//                    if(specPojo.getVmap()!=null && specPojo.getVmap().length()>0){
//                        PR_LeftEyes.put(charName, specPojo.getVmap());//23                    
//                    }
//                }
                //Start Code added by Mr Thangaraj on 26-05-2016 for capturing special lens type details
                if (specPojo.getLensDesignType() != null && specPojo.getLensDesignType().length() > 0) {
                    if (R_L.equalsIgnoreCase("R")) {
                        charName = "PRES_DESIGNTYPE";
                        if (Validations.isFieldNotEmpty(specPojo.getLensDesignType())) {
                            PR_LeftEyes.put(charName, specPojo.getLensDesignType());
                        }
                        
                        charName = "PRES_VMAP";
                        if (Validations.isFieldNotEmpty(specPojo.getR_vmap())) {
                            PR_LeftEyes.put(charName, specPojo.getR_vmap());
                            System.out.println("vmap---------" + specPojo.getR_vmap());
                        }
                        charName = "PRES_VBD";
                        if (Validations.isFieldNotEmpty(specPojo.getR_vbd())) {
                            PR_LeftEyes.put(charName, specPojo.getR_vbd());
                        }
                        charName = "PRES_PANTO";
                        if (Validations.isFieldNotEmpty(specPojo.getR_panto())) {
                            PR_LeftEyes.put(charName, specPojo.getR_panto());
                        }
                        charName = "PRES_NOD";
                        if (Validations.isFieldNotEmpty(specPojo.getR_nod())) {
                            PR_LeftEyes.put(charName, specPojo.getR_nod());
                        }
                        charName = "PRES_ZTILT";
                        if (Validations.isFieldNotEmpty(specPojo.getR_ztilt())) {
                            PR_LeftEyes.put(charName, specPojo.getR_ztilt());
                        }
                        charName = "PRES_FWD";
                        if (Validations.isFieldNotEmpty(specPojo.getR_fwd())) {
                            PR_LeftEyes.put(charName, specPojo.getR_fwd());
                        }
                        charName = "PRES_NWD";
                        if (Validations.isFieldNotEmpty(specPojo.getR_nwd())) {
                            PR_LeftEyes.put(charName, specPojo.getR_nwd());
                        }
                        charName = "PRES_ETYP";
                        if (Validations.isFieldNotEmpty(specPojo.getR_etyp())) {
                            PR_LeftEyes.put(charName, specPojo.getR_etyp());
                        }
                        charName = "PRES_FTYP";
                        if (Validations.isFieldNotEmpty(specPojo.getR_ftyp())) {
                            PR_LeftEyes.put(charName, specPojo.getR_ftyp());
                        }
                        charName = "PRES_HBOX";
                        if (Validations.isFieldNotEmpty(specPojo.getR_hbox())) {
                            PR_LeftEyes.put(charName, specPojo.getR_hbox());
                        }
                        charName = "PRES_VBOX";
                        if (Validations.isFieldNotEmpty(specPojo.getR_vbox())) {
                            PR_LeftEyes.put(charName, specPojo.getR_vbox());
                        }
                        charName = "PRES_DBL";
                        if (Validations.isFieldNotEmpty(specPojo.getR_dbl())) {
                            PR_LeftEyes.put(charName, specPojo.getR_dbl());
                        }
                        charName = "PRES_IPD";
                        if (Validations.isFieldNotEmpty(specPojo.getR_ipd())) {
                            PR_LeftEyes.put(charName, specPojo.getR_ipd());
                        }
                        charName = "PRES_SEGDHT";
                        if (Validations.isFieldNotEmpty(specPojo.getR_segdht())) {
                            PR_LeftEyes.put(charName, specPojo.getR_segdht());
                        }
                        charName = "PRES_FRAM";
                        if (Validations.isFieldNotEmpty(specPojo.getR_fram())) {
                            PR_LeftEyes.put(charName, specPojo.getR_fram());
                        }
                        
                    }
                    if (R_L.equalsIgnoreCase("L")) {
                        
                        charName = "PRES_DESIGNTYPE";
                        if (Validations.isFieldNotEmpty(specPojo.getLensDesignType())) {
                            PR_LeftEyes.put(charName, specPojo.getLensDesignType());
                        }
                        charName = "PRES_VMAP";
                        if (Validations.isFieldNotEmpty(specPojo.getL_vmap())) {
                            PR_LeftEyes.put(charName, specPojo.getL_vmap());
                        }
                        charName = "PRES_VBD";
                        if (Validations.isFieldNotEmpty(specPojo.getL_vbd())) {
                            PR_LeftEyes.put(charName, specPojo.getL_vbd());
                        }
                        charName = "PRES_PANTO";
                        if (Validations.isFieldNotEmpty(specPojo.getL_panto())) {
                            PR_LeftEyes.put(charName, specPojo.getL_panto());
                        }
                        charName = "PRES_NOD";
                        if (Validations.isFieldNotEmpty(specPojo.getL_nod())) {
                            PR_LeftEyes.put(charName, specPojo.getL_nod());
                        }
                        charName = "PRES_ZTILT";
                        if (Validations.isFieldNotEmpty(specPojo.getL_ztilt())) {
                            PR_LeftEyes.put(charName, specPojo.getL_ztilt());
                        }
                        charName = "PRES_FWD";
                        if (Validations.isFieldNotEmpty(specPojo.getL_fwd())) {
                            PR_LeftEyes.put(charName, specPojo.getL_fwd());
                        }
                        charName = "PRES_NWD";
                        if (Validations.isFieldNotEmpty(specPojo.getL_nwd())) {
                            PR_LeftEyes.put(charName, specPojo.getL_nwd());
                        }
                        charName = "PRES_ETYP";
                        if (Validations.isFieldNotEmpty(specPojo.getL_etyp())) {
                            PR_LeftEyes.put(charName, specPojo.getL_etyp());
                        }
                        charName = "PRES_FTYP";
                        if (Validations.isFieldNotEmpty(specPojo.getL_ftyp())) {
                            PR_LeftEyes.put(charName, specPojo.getL_ftyp());
                        }
                        charName = "PRES_HBOX";
                        if (Validations.isFieldNotEmpty(specPojo.getL_hbox())) {
                            PR_LeftEyes.put(charName, specPojo.getL_hbox());
                        }
                        charName = "PRES_VBOX";
                        if (Validations.isFieldNotEmpty(specPojo.getL_vbox())) {
                            PR_LeftEyes.put(charName, specPojo.getL_vbox());
                        }
                        charName = "PRES_DBL";
                        if (Validations.isFieldNotEmpty(specPojo.getL_dbl())) {
                            PR_LeftEyes.put(charName, specPojo.getL_dbl());
                        }
                        charName = "PRES_IPD";
                        if (Validations.isFieldNotEmpty(specPojo.getL_ipd())) {
                            PR_LeftEyes.put(charName, specPojo.getL_ipd());
                        }
                        charName = "PRES_SEGDHT";
                        if (Validations.isFieldNotEmpty(specPojo.getL_segdht())) {
                            PR_LeftEyes.put(charName, specPojo.getL_segdht());
                        }
                        charName = "PRES_FRAM";
                        if (Validations.isFieldNotEmpty(specPojo.getL_fram())) {
                            PR_LeftEyes.put(charName, specPojo.getL_fram());
                        }
                        
                    }
                }
                
                return PR_LeftEyes;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PrescriptionPOJO setPRPojoFromCharPojo(Connection con, String docNo, PrescriptionPOJO specPojo) {
        try {
            CharacteristicDO charDo = new CharacteristicDO();
            Map charRightMapFrmDb = charDo.getCharacteristicsByDocNo(con, "PR", docNo, "R");
            Map charLeftMapFrmDb = charDo.getCharacteristicsByDocNo(con, "PR", docNo, "L");
            
            String charName = null;
            charName = "PRES_DIST_SPH";//1
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_DIST_CYL";//2
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_DIST_AXIS";//3
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_DIST_PRISM";//4
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_prism("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_prism("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_DIST_BASE";//5
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_base("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_base("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_DIST_VA";//6
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_dist_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_dist_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_SPH";//7
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_CYL";//8
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_AXIS";//9
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_PRISM";//10
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_prism("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_prism("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_BASE";//11
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_base("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_base("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_INTER_VA";//12
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_inter_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_inter_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_SPH";//13
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_CYL";//14
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_AXIS";//15
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_PRISM";//16
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_prism("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_prism("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_BASE";//17
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_base("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_base("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_NEAR_VA";//18
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_near_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_near_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_ADDITION_SPH";//19
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_addition("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_addition("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_SALE";//20
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setRight_Sale("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setLeft_Sale("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_TINT";//Added by Balachander V on 30.9.2019 to set TINTCode
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_L_TintCode("" + charRightMapFrmDb.get(charName));
            }
            charName = "PRES_ENGRAVE";//Added by Balachander V on 10.3.2020 to Send Engrave values to SAP
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_L_engrave("" + charRightMapFrmDb.get(charName));
            }
            charName = "PRES_PROFILE";//Added by Balachander V on 16.5.2022 to Send POwer values to SAP
            if (charRightMapFrmDb.containsKey(charName)) {
                try {
                   // String val = charRightMapFrmDb.get(charName).toString();
                    specPojo.setR_L_powerType("" + charRightMapFrmDb.get(charName));
                } catch (Exception e) {
                    
                }
                
            }
             charName = "PRES_CORLEN";//Added by Balachander V on 22.6.2022 to Send Corlen values to SAP
            if (charRightMapFrmDb.containsKey(charName)) {
                try {
                    if (charRightMapFrmDb.containsKey(charName)) {
                        specPojo.setR_corlen("" + charRightMapFrmDb.get(charName));
                    }
                    if (charRightMapFrmDb.containsKey(charName)) {
                        specPojo.setL_corlen("" + charLeftMapFrmDb.get(charName));
                    }
                } catch (Exception e) {
                    
                }
                
            }
            charName = "PRES_BP";//20
            if (charRightMapFrmDb.containsKey(charName)) {
                String b_p = String.valueOf(charRightMapFrmDb.get(charName));
                if (Validations.isFieldNotEmpty(b_p)) {
                    if (b_p.equalsIgnoreCase("DI")) {
                        specPojo.setRight_B_P_Dist("X");
                        specPojo.setRight_B_P_Inter("X");
                        specPojo.setRight_B_P_Near(null);
                    } else if (b_p.equalsIgnoreCase("DN")) {
                        specPojo.setRight_B_P_Dist("X");
                        specPojo.setRight_B_P_Inter(null);
                        specPojo.setRight_B_P_Near("X");
                    } else if (b_p.equalsIgnoreCase("IN")) {
                        specPojo.setRight_B_P_Dist(null);
                        specPojo.setRight_B_P_Inter("X");
                        specPojo.setRight_B_P_Near("X");
                    }
                }
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                String b_p = String.valueOf(charLeftMapFrmDb.get(charName));
                if (Validations.isFieldNotEmpty(b_p)) {
                    if (b_p.equalsIgnoreCase("DI")) {
                        specPojo.setLeft_B_P_Dist("X");
                        specPojo.setLeft_B_P_Inter("X");
                        specPojo.setLeft_B_P_Near(null);
                    } else if (b_p.equalsIgnoreCase("DN")) {
                        specPojo.setLeft_B_P_Dist("X");
                        specPojo.setLeft_B_P_Inter(null);
                        specPojo.setLeft_B_P_Near("X");
                    } else if (b_p.equalsIgnoreCase("IN")) {
                        specPojo.setLeft_B_P_Dist(null);
                        specPojo.setLeft_B_P_Inter("X");
                        specPojo.setLeft_B_P_Near("X");
                    }
                }
            }
            
            charName = "PRES_AR_IPD";//21
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setAr_ipd("" + charRightMapFrmDb.get(charName));
            } else if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setAr_ipd("" + charLeftMapFrmDb.get(charName));
            }

//            //Code added on July 2nd for Capturing the special lens details
//            
//             charName = "PRES_VMAP";//22
//            if (charRightMapFrmDb.containsKey(charName)) {
//                System.out.println("VMAP----"+charRightMapFrmDb.get(charName));
//                specPojo.setVmap("" + charRightMapFrmDb.get(charName));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setVmap("" + charLeftMapFrmDb.get(charName));
//            }
//            charName = "PRES_BVD";//23
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setBvd(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setBvd(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//            charName = "PRES_PANTO";//24
//            if (charRightMapFrmDb.containsKey(charName)) {
//                 System.out.println("PANTO----"+Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//                specPojo.setPanto(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setPanto(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//            charName = "PRES_READDIST";//25
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setReadingdist(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setReadingdist(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//             charName = "PRES_FFA";//26
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setFaceformangle(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setFaceformangle(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//             
//               charName = "PRES_INITIAL";//22
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setNameinit("" + charRightMapFrmDb.get(charName));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setNameinit("" + charLeftMapFrmDb.get(charName));
//            }
//
//            //Activ lens
//             
//              charName = "PRES_FWD";//28
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setFwd(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setFwd(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//
//               charName = "PRES_NWD";//29
//            if (charRightMapFrmDb.containsKey(charName)) {
//                specPojo.setNwd(Integer.parseInt(String.valueOf(charRightMapFrmDb.get(charName))));
//            } else if (charLeftMapFrmDb.containsKey(charName)) {
//                specPojo.setNwd(Integer.parseInt(String.valueOf(charLeftMapFrmDb.get(charName))));
//            }
//
//            
//            
//            
//            //End of Code added on July 2nd for Capturing the special lens details
            charName = "PRES_DESIGNTYPE";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setLensDesignType("" + charRightMapFrmDb.get(charName));
            }
            charName = "PRES_VMAP";
            if (charRightMapFrmDb.containsKey(charName)) {
                System.out.println("Rsetvmap : " + charRightMapFrmDb.get(charName));
                specPojo.setR_vmap("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                System.out.println("Lsetvmap : " + charLeftMapFrmDb.get(charName));
                specPojo.setL_vmap("" + charLeftMapFrmDb.get(charName));
            }
            charName = "PRES_VBD";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_vbd((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_vbd((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_PANTO";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_panto((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_panto((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_NOD";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_nod((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_nod((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_ZTILT";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_ztilt((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_ztilt((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_FWD";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_fwd((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_fwd((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_NWD";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_nwd((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_nwd((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_ETYP";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_etyp((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_etyp((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_FTYP";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_ftyp((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_ftyp((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_HBOX";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_hbox((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_hbox((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_VBOX";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_vbox((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_vbox((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_DBL";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_dbl((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_dbl((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_IPD";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_ipd(((String.valueOf(charRightMapFrmDb.get(charName)))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_ipd((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_SEGDHT";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_segdht((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_segdht((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            charName = "PRES_FRAM";
            if (charRightMapFrmDb.containsKey(charName)) {
                specPojo.setR_fram((String.valueOf(charRightMapFrmDb.get(charName))));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                specPojo.setL_fram((String.valueOf(charLeftMapFrmDb.get(charName))));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return specPojo;
    }
    
    public static Map getAndsetCLCharacteristics(ContactLensPOJO clensPojo, String R_L) {
        Map CL_LeftEyes = new LinkedHashMap();
        try {
            if (clensPojo != null) {
                String charName = "CONT_LENS_HVID_MM";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getRight_hvid());//18
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getLeft_hvid());//18
                }
                charName = "OBJ_OVER_REFRAC_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_right_sph());//18
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_left_sph());//18
                }
                
                charName = "OBJ_OVER_REFRAC_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_right_cyl());//3
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_left_cyl());//3
                }
                
                charName = "OBJ_OVER_REFRAC_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_right_axis());//4
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getOor_left_axis());//4
                }
                
                charName = "SUB_OVER_REFRAC_DIST_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_dist_sph());//5
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_dist_sph());//5
                }
                charName = "SUB_OVER_REFRAC_DIST_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_dist_cyl());//6
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_dist_cyl());//6
                }
                
                charName = "SUB_OVER_REFRAC_DIST_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_dist_axis());//7
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_dist_axis());//7
                }
                
                charName = "SUB_OVER_REFRAC_DIST_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_dist_va());//8
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_dist_va());//8
                }
                charName = "SUB_OVER_REFRAC_NEAR_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_near_sph());//9
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_near_sph());//9
                }
                charName = "SUB_OVER_REFRAC_NEAR_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_near_cyl());//10
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_near_cyl());//10
                }
                
                charName = "SUB_OVER_REFRAC_NEAR_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_near_axis());//11
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_near_axis());//11
                }
                
                charName = "SUB_OVER_REFRAC_NEAR_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_right_near_va());//12
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getSor_left_near_va());//12
                }
                charName = "CONT_LENS_FINAL_PRES_BRAND";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_brand());//13
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_brand());//13
                }
                
                charName = "CONT_LENS_FINAL_PRES_BC";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_bc());//14
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_bc());//14
                }
                
                charName = "CONT_LENS_FINAL_PRES_DIA";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_dia());//15
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_dia());//15
                }
                
                charName = "CONT_LENS_FINAL_PRES_POW_SPH";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_sph());//16
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_sph());//16
                }
                charName = "CONT_LENS_FINAL_PRES_POW_CYL";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_cyl());//17
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_cyl());//17
                }
                charName = "CONT_LENS_FINAL_PRES_POW_AXIS";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_axis());//18
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_axis());//18
                }
                
                charName = "CONT_LENS_FINAL_PRES_POW_ADD";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_add());//19
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_add());//19
                }
                charName = "CONT_LENS_FINAL_PRES_TINT";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_tint());//20
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_tint());//20
                }
                
                charName = "CONT_LENS_FINAL_PRES_OZ";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_oz());//21
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_oz());//21
                }
                
                charName = "CONT_LENS_FINAL_PRES_VA";
                if (R_L.equalsIgnoreCase("R")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_right_va());//22
                } else if (R_L.equalsIgnoreCase("L")) {
                    CL_LeftEyes.put(charName, clensPojo.getPower_left_va());//22
                }
                
                return CL_LeftEyes;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public ContactLensPOJO setCLPojoFromCharPojo(Connection con, String docNo, ContactLensPOJO clensPojo) {
        try {
            CharacteristicDO charDo = new CharacteristicDO();
            Map charRightMapFrmDb = charDo.getCharacteristicsByDocNo(con, "CL", docNo, "R");
            Map charLeftMapFrmDb = charDo.getCharacteristicsByDocNo(con, "CL", docNo, "L");
            
            String charName = null;
            charName = "CONT_LENS_HVID_MM";//1
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setRight_hvid("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setLeft_hvid("" + charLeftMapFrmDb.get(charName));
            }
            charName = "OBJ_OVER_REFRAC_SPH";//2
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_right_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_left_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "OBJ_OVER_REFRAC_CYL";//3
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_right_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_left_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "OBJ_OVER_REFRAC_AXIS";//4
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_right_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setOor_left_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_DIST_SPH";//5
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_dist_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_dist_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_DIST_CYL";//6
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_dist_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_dist_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_DIST_AXIS";//7
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_dist_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_dist_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_DIST_VA";//8
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_dist_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_dist_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_NEAR_SPH";//9
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_near_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_near_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_NEAR_CYL";//10
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_near_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_near_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_NEAR_CYL";//10
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_near_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_near_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_NEAR_AXIS";//11
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_near_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_near_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "SUB_OVER_REFRAC_NEAR_VA";//12
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_right_near_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setSor_left_near_va("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_BRAND";//13
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_brand("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_brand("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_BC";//14
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_bc("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_bc("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_DIA";//15
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_dia("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_dia("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_POW_SPH";//16
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_sph("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_sph("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_POW_CYL";//17
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_cyl("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_cyl("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_POW_AXIS";//18
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_axis("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_axis("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_POW_ADD";//19
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_add("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_add("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_TINT";//20
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_tint("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_tint("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_OZ";//21
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_oz("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_oz("" + charLeftMapFrmDb.get(charName));
            }
            charName = "CONT_LENS_FINAL_PRES_VA";//22
            if (charRightMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_right_va("" + charRightMapFrmDb.get(charName));
            }
            if (charLeftMapFrmDb.containsKey(charName)) {
                clensPojo.setPower_left_va("" + charLeftMapFrmDb.get(charName));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clensPojo;
    }
}
