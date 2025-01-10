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
 * POJO for AdvanceReceiptDetails
 * 
 * 
 * 
 */

package ISRetail.paymentdetails;

import java.sql.Date;

/**
 *
 * @author Administrator
 */
public class AdvanceReceiptDetailsPOJO {

    
private String documentno="";
private String storecode="";
private  int fiscalyear=0;
private int lineitemno=0;
private String paymentfrom="";
private  String modeofpayment="";
private  String instrumentno="";
private  int instrumentdate;
private  String authorizationcode="";
private  String bank="";
private  String cardtype="";
private  String branchname="";
private  String GVFrom="";
private  String GVTO="";
private  double Denomination=0;
private  String validationtext="";
private  double creditamount=0;
private int cn_fiscalyear=0;
private  String creditnotereference="";
private  String currencytype="";
private  double exchangerate=0;
private  double noofunits=0;
private  String employeename="";
private  String department="";
private  String letterreference="";
private  String authorizedperson="";
private  double loanamount=0;
private int loyalitypoints=0;
private int asondate;
private  int redeemedpoints;
private  String approvalno="";
private String othercardtype="";
private  double amount=0;
private int tataNeuPoints=0;

  

    public String getModeofpayment() {
        return modeofpayment;
    }

    public void setModeofpayment(String modeofpayment) {
        this.modeofpayment = modeofpayment;
    }

    public String getInstrumentno() {
        return instrumentno;
    }

    public void setInstrumentno(String instrumentno) {
        this.instrumentno = instrumentno;
    }

    public int getInstrumentdate() {
        return instrumentdate;
    }

    public void setInstrumentdate(int instrumentdate) {
        this.instrumentdate = instrumentdate;
    }

    public String getAuthorizationcode() {
        return authorizationcode;
    }

    public void setAuthorizationcode(String authorizationcode) {
        this.authorizationcode = authorizationcode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getGVFrom() {
        return GVFrom;
    }

    public void setGVFrom(String GVFrom) {
        this.GVFrom = GVFrom;
    }

    public String getGVTO() {
        return GVTO;
    }

    public void setGVTO(String GVTO) {
        this.GVTO = GVTO;
    }

   

    public String getValidationtext() {
        return validationtext;
    }

    public void setValidationtext(String validationtext) {
        this.validationtext = validationtext;
    }

    public double getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(double creditamount) {
        this.creditamount = creditamount;
    }

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    public String getCreditnotereference() {
        return creditnotereference;
    }

    public void setCreditnotereference(String creditnotereference) {
        this.creditnotereference = creditnotereference;
    }

    public String getCurrencytype() {
        return currencytype;
    }

    public void setCurrencytype(String currencytype) {
        this.currencytype = currencytype;
    }

    public double getExchangerate() {
        return exchangerate;
    }

    public void setExchangerate(double exchangerate) {
        this.exchangerate = exchangerate;
    }

    public double getNoofunits() {
        return noofunits;
    }

    public void setNoofunits(double noofunits) {
        this.noofunits = noofunits;
    }

    public String getEmployeename() {
        return employeename;
    }

    public void setEmployeename(String employeename) {
        this.employeename = employeename;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLetterreference() {
        return letterreference;
    }

    public void setLetterreference(String letterreference) {
        this.letterreference = letterreference;
    }

    public String getAuthorizedperson() {
        return authorizedperson;
    }

    public void setAuthorizedperson(String authorizedperson) {
        this.authorizedperson = authorizedperson;
    }

    public double getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(double loanamount) {
        this.loanamount = loanamount;
    }

    public int getLoyalitypoints() {
        return loyalitypoints;
    }

    public void setLoyalitypoints(int loyalitypoints) {
        this.loyalitypoints = loyalitypoints;
    }

    public int getAsondate() {
        return asondate;
    }

    public void setAsondate(int asondate) {
        this.asondate = asondate;
    }

    public int getRedeemedpoints() {
        return redeemedpoints;
    }

    public void setRedeemedpoints(int redeemedpoints) {
        this.redeemedpoints = redeemedpoints;
    }

    public String getApprovalno() {
        return approvalno;
    }

    public void setApprovalno(String approvalno) {
        this.approvalno = approvalno;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDocumentno() {
        return documentno;
    }

    public void setDocumentno(String documentno) {
        this.documentno = documentno;
    }

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public int getLineitemno() {
        return lineitemno;
    }

    public void setLineitemno(int lineitemno) {
        this.lineitemno = lineitemno;
    }

    public String getPaymentfrom() {
        return paymentfrom;
    }

    public void setPaymentfrom(String paymentfrom) {
        this.paymentfrom = paymentfrom;
    }

    public int getCn_fiscalyear() {
        return cn_fiscalyear;
    }

    public void setCn_fiscalyear(int cn_fiscalyear) {
        this.cn_fiscalyear = cn_fiscalyear;
    }

    public String getOthercardtype() {
        return othercardtype;
    }

    public void setOthercardtype(String othercardtype) {
        this.othercardtype = othercardtype;
    }

    public double getDenomination() {
        return Denomination;
    }

    public void setDenomination(double Denomination) {
        this.Denomination = Denomination;
    }

    public int getTataNeuPoints() {
        return tataNeuPoints;
    }

    public void setTataNeuPoints(int tataNeuPoints) {
        this.tataNeuPoints = tataNeuPoints;
    }

  

}
