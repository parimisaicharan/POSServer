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
 * Class for storing the values of the POS Status for all Data transactions of the POS Staging Table
 * 
 * 
 * 
 */

package ISRetail.Webservices;

import java.sql.Date;

public class PosStatusBean {
    
    private int id;
    private Date execuionDate;
    private String messageType;
    private String pkKeyValue1; 
    private String pkKeyValue2; 
    private String actionType;
    private String  status;

    public

    int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getExecuionDate() {
        return execuionDate;
    }

    public void setExecuionDate(Date execuionDate) {
        this.execuionDate = execuionDate;
    }


    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }


    public String getPkKeyValue1() {
        return pkKeyValue1;
    }

    public void setPkKeyValue1(String pkKeyValue1) {
        this.pkKeyValue1 = pkKeyValue1;
    }


    public String getPkKeyValue2() {
        return pkKeyValue2;
    }

    public void setPkKeyValue2(String pkKeyValue2) {
        this.pkKeyValue2 = pkKeyValue2;
    }


    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    

}
