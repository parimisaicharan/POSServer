/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package VersionManagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class VersionDetailPOJO {

    private String patchId;
    private String patchstatement;
    private String patchdescription;
    private int patchdate;
    private int slno;
    private String status;
    private String statement;
    private ArrayList executelist;
    private String exeresult;

    public String getPatchId() {
        return patchId;
    }

    public void setPatchId(String patchId) {
        this.patchId = patchId;
    }

    public String getPatchstatement() {
        return patchstatement;
    }

    public void setPatchstatement(String patchstatement) {
        this.patchstatement = patchstatement;
    }

    public String getPatchdescription() {
        return patchdescription;
    }

    public void setPatchdescription(String patchdescription) {
        this.patchdescription = patchdescription;
    }

    public int getPatchdate() {
        return patchdate;
    }

    public void setPatchdate(int patchdate) {
        this.patchdate = patchdate;
    }

    public String getExeresult() {
        return exeresult;
    }

    public void setExeresult(String exeresult) {
        this.exeresult = exeresult;
    }

    public int getSlno() {
        return slno;
    }

    public void setSlno(int slno) {
        this.slno = slno;
    }

    public ArrayList getExecutelist() {
        return executelist;
    }

    public void setExecutelist(ArrayList executelist) {
        this.executelist = executelist;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void getExecuteResult(Connection conn) {
        int result = -1;
        try {
            java.sql.Statement stmt = conn.createStatement();
            result = stmt.executeUpdate(getStatement());
            if (result == 0) {
                setStatus("V");
                setExeresult("Verify 0 Statements Updated");
            } else {
                setStatus("S");
                setExeresult(String.valueOf(result) + " Statements Updated");
            }
        } catch (SQLException ex) {
            setStatus("F");
            setExeresult(ex.getMessage());
        } catch (Exception ee) {
            setExeresult(ee.getMessage());
            setStatus("F");
            ee.printStackTrace();
        }




    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
