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
 * POJO
 * 
 */

package ISRetail.salesreturns;

public class AcknowledgementHeaderPOJO {
private String  storecode;
private int  fiscalyear;
private String  acknumber;

private int  acknowledgementdate;

private String  ackstatus;
private int  revertdate;
private String  refstorecode;

private String  refsaleorderno;

private String  refposinvoiceno;


private int  refposinvoicedate;

private String  customercode;

private double  totalnetamount;
private String  visibledefects;

private String  comments;

private String  approvedby;

private int  approveddate;

private String  returntocustomerreason;
private String  createdby;
private int  createddate;
private String  createdtime;
private String  modifiedby;
private int  modifieddate;
private String  modifiedtime;
private String  staffresp; //code added on 21 Jun 2011 for name capture in Sale Return
private String selectedStore;
private String frombatch;//Added by Balachander V on 27.12.2018 to send frombatch during Sales return
private String otherchannelname; //Added by Balachander V on 09.11.2020 to save the Zopper Insurance Id to cancel Insurance.

private String insuranceId;
private String sfcomplaintid;
        
    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public int getFiscalyear() {
        return fiscalyear;
    }

    public void setFiscalyear(int fiscalyear) {
        this.fiscalyear = fiscalyear;
    }

    public String getAcknumber() {
        return acknumber;
    }

    public void setAcknumber(String acknumber) {
        this.acknumber = acknumber;
    }

    public int getAcknowledgementdate() {
        return acknowledgementdate;
    }

    public void setAcknowledgementdate(int acknowledgementdate) {
        this.acknowledgementdate = acknowledgementdate;
    }

    public String getAckstatus() {
        return ackstatus;
    }

    public void setAckstatus(String ackstatus) {
        this.ackstatus = ackstatus;
    }

    public int getRevertdate() {
        return revertdate;
    }

    public void setRevertdate(int revertdate) {
        this.revertdate = revertdate;
    }

    public String getRefstorecode() {
        return refstorecode;
    }

    public void setRefstorecode(String refstorecode) {
        this.refstorecode = refstorecode;
    }

    public String getRefsaleorderno() {
        return refsaleorderno;
    }

    public void setRefsaleorderno(String refsaleorderno) {
        this.refsaleorderno = refsaleorderno;
    }

    public String getRefposinvoiceno() {
        return refposinvoiceno;
    }

    public void setRefposinvoiceno(String refposinvoiceno) {
        this.refposinvoiceno = refposinvoiceno;
    }

    public int getRefposinvoicedate() {
        return refposinvoicedate;
    }

    public void setRefposinvoicedate(int refposinvoicedate) {
        this.refposinvoicedate = refposinvoicedate;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public double getTotalnetamount() {
        return totalnetamount;
    }

    public void setTotalnetamount(double totalnetamount) {
        this.totalnetamount = totalnetamount;
    }

    public String getVisibledefects() {
        return visibledefects;
    }

    public void setVisibledefects(String visibledefects) {
        this.visibledefects = visibledefects;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getApprovedby() {
        return approvedby;
    }

    public void setApprovedby(String approvedby) {
        this.approvedby = approvedby;
    }

    public int getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(int approveddate) {
        this.approveddate = approveddate;
    }

    public String getReturntocustomerreason() {
        return returntocustomerreason;
    }

    public void setReturntocustomerreason(String returntocustomerreason) {
        this.returntocustomerreason = returntocustomerreason;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public int getCreateddate() {
        return createddate;
    }

    public void setCreateddate(int createddate) {
        this.createddate = createddate;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public void setCreatedtime(String createdtime) {
        this.createdtime = createdtime;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public int getModifieddate() {
        return modifieddate;
    }

    public void setModifieddate(int modifieddate) {
        this.modifieddate = modifieddate;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public void setModifiedtime(String modifiedtime) {
        this.modifiedtime = modifiedtime;
    }


    /**
     * @return the staffresp
     */
    public String getStaffresp() {
        return staffresp;
    }

    /**
     * @param staffresp the staffresp to set
     */
    public void setStaffresp(String staffresp) {
        this.staffresp = staffresp;
    }

    public String getSelectedStore() {
        return selectedStore;
    }

    public void setSelectedStore(String selecttedStore) {
        this.selectedStore = selecttedStore;
    }
//Added by Balachander V on 27.12.2018 to send frombatch during Sales return
    public String getFrombatch() {
        return frombatch;
    }

    public void setFrombatch(String frombatch) {
        this.frombatch = frombatch;
    }
//Ended by Balachander V on 27.12.2018 to send frombatch during Sales return

    public String getOtherchannelname() {
        return otherchannelname;
    }

    public void setOtherchannelname(String otherchannelname) {
        this.otherchannelname = otherchannelname;
    }

    public String getInsuranceId() {
        return insuranceId;
    }

    public void setInsuranceId(String insuranceId) {
        this.insuranceId = insuranceId;
    }
    
    public String getSFComplaintID() {
        return sfcomplaintid;
    }

    public void setSFComplaintID(String sfcomplaintid) {
        this.sfcomplaintid = sfcomplaintid;
    }
}
