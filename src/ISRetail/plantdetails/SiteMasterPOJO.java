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
 * POJO for SiteMaster
 * 
 * 
 * 
 */
package ISRetail.plantdetails;

/**
 *
 * @author Administrator
 */
public class SiteMasterPOJO {

    private String siteid;
    private String sitedescription;
    private String siteaddress_housenumber;
    private String siteaddress_street;
    private String siteaddress_area;
    private String siteaddress_city;
    private String siteaddress_postalcode;
    private String siteaddress_state;
    private String siteaddress_country;
    private String site_contact_telephone;
    private String site_contact_email;
    private String companycode;
    private String transaction_pwd;
    private int passwordvaliditydate;
    private String passwordvalidityindicator;
    private int inaugurationdate;
    private String num_rangelogic;
    private String cstno;
    private String vat_tin_no;
    private String zone;
    private String corresponding_cfa_id;
    private String cfa_contact_telephone;
    private String site_contact_fax;
    private String cfa_contact_email;
    private String tax_calculation_indicator;
    private double cash_payout_limit;
    private String corresponding_regoffice_name;
    private String regoff_contact_telephone;
    private String regoff_contact_email;
    private int postingdate;
    private int systemdate;
    private String quotationvalidityperiod;
    private String quotationvalidityindicator;
    private String creditnotevalidityduration;
    private String creditnotevalidityindicator;
    private String minadvancepercentage;
    private String promotionordiscount;
    private String ipaddress;   
    private String posversion;    
    private String pospatchversion;
    private String fiscalyear;
    private double roundoffvalue;
    private String regdoffaddress;
    private String marquee;
    private String pan_no;
    private String postingindicator;   
    private String currency;
    private String deletionInd;
    private String lbt_no;
    private String store_revw_link;
private String taxFlowIndicator;//Added by Balachander V on 04.05.2019 to enable or disable tax flow based on indicator value for all billings
    public String getSiteid() {
        return siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    public String getSitedescription() {
        return sitedescription;
    }

    public void setSitedescription(String sitedescription) {
        this.sitedescription = sitedescription;
    }

    public String getSiteaddress_housenumber() {
        return siteaddress_housenumber;
    }

    public void setSiteaddress_housenumber(String siteaddress_housenumber) {
        this.siteaddress_housenumber = siteaddress_housenumber;
    }

    public String getSiteaddress_street() {
        return siteaddress_street;
    }

    public void setSiteaddress_street(String siteaddress_street) {
        this.siteaddress_street = siteaddress_street;
    }

    public String getSiteaddress_area() {
        return siteaddress_area;
    }

    public void setSiteaddress_area(String siteaddress_area) {
        this.siteaddress_area = siteaddress_area;
    }

    public String getSiteaddress_city() {
        return siteaddress_city;
    }

    public void setSiteaddress_city(String siteaddress_city) {
        this.siteaddress_city = siteaddress_city;
    }

    public String getSiteaddress_postalcode() {
        return siteaddress_postalcode;
    }

    public void setSiteaddress_postalcode(String siteaddress_postalcode) {
        this.siteaddress_postalcode = siteaddress_postalcode;
    }

    public String getSiteaddress_state() {
        return siteaddress_state;
    }

    public void setSiteaddress_state(String siteaddress_state) {
        this.siteaddress_state = siteaddress_state;
    }

    public String getSiteaddress_country() {
        return siteaddress_country;
    }

    public void setSiteaddress_country(String siteaddress_country) {
        this.siteaddress_country = siteaddress_country;
    }

    public String getSite_contact_telephone() {
        return site_contact_telephone;
    }

    public void setSite_contact_telephone(String site_contact_telephone) {
        this.site_contact_telephone = site_contact_telephone;
    }

    public String getSite_contact_email() {
        return site_contact_email;
    }

    public void setSite_contact_email(String site_contact_email) {
        this.site_contact_email = site_contact_email;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getTransaction_pwd() {
        return transaction_pwd;
    }

    public void setTransaction_pwd(String transaction_pwd) {
        this.transaction_pwd = transaction_pwd;
    }

    public int getPasswordvaliditydate() {
        return passwordvaliditydate;
    }

    public void setPasswordvaliditydate(int passwordvaliditydate) {
        this.passwordvaliditydate = passwordvaliditydate;
    }

    public String getPasswordvalidityindicator() {
        return passwordvalidityindicator;
    }

    public void setPasswordvalidityindicator(String passwordvalidityindicator) {
        this.passwordvalidityindicator = passwordvalidityindicator;
    }

    public int getInaugurationdate() {
        return inaugurationdate;
    }

    public void setInaugurationdate(int inaugurationdate) {
        this.inaugurationdate = inaugurationdate;
    }

    public String getNum_rangelogic() {
        return num_rangelogic;
    }

    public void setNum_rangelogic(String num_rangelogic) {
        this.num_rangelogic = num_rangelogic;
    }

    public String getCstno() {
        return cstno;
    }

    public void setCstno(String cstno) {
        this.cstno = cstno;
    }

    public String getVat_tin_no() {
        return vat_tin_no;
    }

    public void setVat_tin_no(String vat_tin_no) {
        this.vat_tin_no = vat_tin_no;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCorresponding_cfa_id() {
        return corresponding_cfa_id;
    }

    public void setCorresponding_cfa_id(String corresponding_cfa_id) {
        this.corresponding_cfa_id = corresponding_cfa_id;
    }

    public String getCfa_contact_telephone() {
        return cfa_contact_telephone;
    }

    public void setCfa_contact_telephone(String cfa_contact_telephone) {
        this.cfa_contact_telephone = cfa_contact_telephone;
    }

    public String getCfa_contact_email() {
        return cfa_contact_email;
    }

    public void setCfa_contact_email(String cfa_contact_email) {
        this.cfa_contact_email = cfa_contact_email;
    }

    public String getTax_calculation_indicator() {
        return tax_calculation_indicator;
    }

    public void setTax_calculation_indicator(String tax_calculation_indicator) {
        this.tax_calculation_indicator = tax_calculation_indicator;
    }

    public double getCash_payout_limit() {
        return cash_payout_limit;
    }

    public void setCash_payout_limit(double cash_payout_limit) {
        this.cash_payout_limit = cash_payout_limit;
    }

    public String getCorresponding_regoffice_name() {
        return corresponding_regoffice_name;
    }

    public void setCorresponding_regoffice_name(String corresponding_regoffice_name) {
        this.corresponding_regoffice_name = corresponding_regoffice_name;
    }

    public String getRegoff_contact_telephone() {
        return regoff_contact_telephone;
    }

    public void setRegoff_contact_telephone(String regoff_contact_telephone) {
        this.regoff_contact_telephone = regoff_contact_telephone;
    }

    public String getRegoff_contact_email() {
        return regoff_contact_email;
    }

    public void setRegoff_contact_email(String regoff_contact_email) {
        this.regoff_contact_email = regoff_contact_email;
    }

    public int getPostingdate() {
        return postingdate;
    }

    public void setPostingdate(int postingdate) {
        this.postingdate = postingdate;
    }

    public int getSystemdate() {
        return systemdate;
    }

    public void setSystemdate(int systemdate) {
        this.systemdate = systemdate;
    }

    public String getQuotationvalidityperiod() {
        return quotationvalidityperiod;
    }

    public void setQuotationvalidityperiod(String quotationvalidityperiod) {
        this.quotationvalidityperiod = quotationvalidityperiod;
    }

    public String getQuotationvalidityindicator() {
        return quotationvalidityindicator;
    }

    public void setQuotationvalidityindicator(String quotationvalidityindicator) {
        this.quotationvalidityindicator = quotationvalidityindicator;
    }

    public String getCreditnotevalidityduration() {
        return creditnotevalidityduration;
    }

    public void setCreditnotevalidityduration(String creditnotevalidityduration) {
        this.creditnotevalidityduration = creditnotevalidityduration;
    }

    public String getCreditnotevalidityindicator() {
        return creditnotevalidityindicator;
    }

    public void setCreditnotevalidityindicator(String creditnotevalidityindicator) {
        this.creditnotevalidityindicator = creditnotevalidityindicator;
    }

    public String getMinadvancepercentage() {
        return minadvancepercentage;
    }

    public void setMinadvancepercentage(String minadvancepercentage) {
        this.minadvancepercentage = minadvancepercentage;
    }

    public String getPromotionordiscount() {
        return promotionordiscount;
    }

    public void setPromotionordiscount(String promotionordiscount) {
        this.promotionordiscount = promotionordiscount;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getPosversion() {
        return posversion;
    }

    public void setPosversion(String posversion) {
        this.posversion = posversion;
    }

    public String getPospatchversion() {
        return pospatchversion;
    }

    public void setPospatchversion(String pospatchversion) {
        this.pospatchversion = pospatchversion;
    }

    public String getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(String fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    public double getRoundoffvalue() {
        return roundoffvalue;
    }

    public void setRoundoffvalue(double roundoffvalue) {
        this.roundoffvalue = roundoffvalue;
    }

    public String getRegdoffaddress() {
        return regdoffaddress;
    }

    public void setRegdoffaddress(String regdoffaddress) {
        this.regdoffaddress = regdoffaddress;
    }

    public String getMarquee() {
        return marquee;
    }

    public void setMarquee(String marquee) {
        this.marquee = marquee;
    }

    public String getPan_no() {
        return pan_no;
    }

    public void setPan_no(String pan_no) {
        this.pan_no = pan_no;
    }

    public String getPostingindicator() {
        return postingindicator;
    }

    public void setPostingindicator(String postingindicator) {
        this.postingindicator = postingindicator;
    }

   
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSite_contact_fax() {
        return site_contact_fax;
    }

    public void setSite_contact_fax(String site_contact_fax) {
        this.site_contact_fax = site_contact_fax;
    }

    public String getDeletionInd() {
        return deletionInd;
    }

    public void setDeletionInd(String deletionInd) {
        this.deletionInd = deletionInd;
    }

    /**
     * @return the lbt_no
     */
    public String getLbt_no() {
        return lbt_no;
    }

    /**
     * @param lbt_no the lbt_no to set
     */
    public void setLbt_no(String lbt_no) {
        this.lbt_no = lbt_no;
    }

    public String getStore_revw_link() {
        return store_revw_link;
    }

    public void setStore_revw_link(String store_revw_link) {
        this.store_revw_link = store_revw_link;
    }

    public String getTaxFlowIndicator() {//Added by Balachander V on 04.05.2019 to enable or disable tax flow based on indicator value for all billings
        return taxFlowIndicator;
    }

    public void setTaxFlowIndicator(String taxFlowIndicator) {
        this.taxFlowIndicator = taxFlowIndicator;
    }//Ended by Balachander V on 04.05.2019 to enable or disable tax flow based on indicator value for all billings
    


   
}
