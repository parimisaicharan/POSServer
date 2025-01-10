/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package posstaging;

import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.serverconsole.ServerConsole;
import java.sql.Connection;

/**
 *
 * @author Administrator
 */
public class ErrorLogPOJO {
private String shortname="";
private String key1="";
private String key2="";
private String key3="";
private String key4="";
private String key5="";
private String key6="";
private int dateoflog=0;
private String timeoflog="";
private String status="";
private String description="";

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }

    public String getKey6() {
        return key6;
    }

    public void setKey6(String key6) {
        this.key6 = key6;
    }

    public int getDateoflog() {
        return dateoflog;
    }

    public void setDateoflog(int dateoflog) {
        this.dateoflog = dateoflog;
    }

    public String getTimeoflog() {
        return timeoflog;
    }

    public void setTimeoflog(String timeoflog) {
        this.timeoflog = timeoflog;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
