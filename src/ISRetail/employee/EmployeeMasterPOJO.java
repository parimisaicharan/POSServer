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
 * 
 * POJO Class for Customer Master which will store the details of the CustomerMaster
 * 
 * 
 */
package ISRetail.employee;

public class EmployeeMasterPOJO {
    private String empid;
    private String storecode;
    private int fiscalyear;
    private String title ;
    private String firstName;
    private String lastName;
    private String designation;
    private String userId;
    private String status;
    private int passWordFiscalYear;
    private String passWord;
    private int passWordValidto;
    private int roleIdAssigned; 

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPassWordFiscalYear() {
        return passWordFiscalYear;
    }

    public void setPassWordFiscalYear(int passWordFiscalYear) {
        this.passWordFiscalYear = passWordFiscalYear;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getPassWordValidto() {
        return passWordValidto;
    }

    public void setPassWordValidto(int passWordValidto) {
        this.passWordValidto = passWordValidto;
    }

    public int getRoleIdAssigned() {
        return roleIdAssigned;
    }

    public void setRoleIdAssigned(int roleIdAssigned) {
        this.roleIdAssigned = roleIdAssigned;
    }

}
