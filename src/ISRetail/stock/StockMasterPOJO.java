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
package ISRetail.stock;

/**
 *
 * @author Administrator
 */
public class StockMasterPOJO {

    private String storecode;
    private String storageloc;
    private String materialcode;
    private int quantity;
    private String updatestatus;
    private String batch;
    private String gvSerialNo;
    private int validFrom;
    private int validTo;
    private String gvStatus;
    private String recordStatus;

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getStorageloc() {
        return storageloc;
    }

    public void setStorageloc(String storageloc) {
        this.storageloc = storageloc;
    }

    public String getMaterialcode() {
        return materialcode;
    }

    public void setMaterialcode(String materialcode) {
        this.materialcode = materialcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getGvSerialNo() {
        return gvSerialNo;
    }

    public void setGvSerialNo(String gvSerialNo) {
        this.gvSerialNo = gvSerialNo;
    }

    public int getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(int validFrom) {
        this.validFrom = validFrom;
    }

    public int getValidTo() {
        return validTo;
    }

    public void setValidTo(int validTo) {
        this.validTo = validTo;
    }

    public String getGvStatus() {
        return gvStatus;
    }

    public void setGvStatus(String gvStatus) {
        this.gvStatus = gvStatus;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
}
