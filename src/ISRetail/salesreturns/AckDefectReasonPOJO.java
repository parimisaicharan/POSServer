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

/**
 *
 * @author enteg
 */
public class AckDefectReasonPOJO {
private String  storecode;
private int  fiscalyear;
private String  refacknumber;

private int  reflineitemno;
private int  refackdate;
private String  defectreasonID;
private String  defectreason;
private String defOtherReasonDesc;

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

    public String getRefacknumber() {
        return refacknumber;
    }

    public void setRefacknumber(String refacknumber) {
        this.refacknumber = refacknumber;
    }

    public int getReflineitemno() {
        return reflineitemno;
    }

    public void setReflineitemno(int reflineitemno) {
        this.reflineitemno = reflineitemno;
    }

    public int getRefackdate() {
        return refackdate;
    }

    public void setRefackdate(int refackdate) {
        this.refackdate = refackdate;
    }

    public String getDefectreason() {
        return defectreason;
    }

    public void setDefectreason(String defectreason) {
        this.defectreason = defectreason;
    }

    public String getDefectreasonID() {
        return defectreasonID;
    }

    public void setDefectreasonID(String defectreasonID) {
        this.defectreasonID = defectreasonID;
    }

    public String getDefOtherReasonDesc() {
        return defOtherReasonDesc;
    }

    public void setDefOtherReasonDesc(String defOtherReasonDesc) {
        this.defOtherReasonDesc = defOtherReasonDesc;
    }

}
