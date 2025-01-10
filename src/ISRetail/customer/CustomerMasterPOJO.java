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
package ISRetail.customer;
import java.io.Serializable;
import java.util.ArrayList;

public class CustomerMasterPOJO implements Serializable{
//private int customerslno;
//private Date createdon;
private String storecode;
private String loyalityno;
private String datasheetno;
private String customercode;
private String title;
private String customerfirstname;
private String customerlastname;
private String gender;
private int dob;
private int age;
private String houseno;
private String street;
private String area;
private String city;
private String pincode;
private String state;
private String country;
private String telephoneno;
private String officeno;
private String officenoextn;
private String mobileno;
private String email;
private String faxno;
private String faxextn;
private String preferredcommunication;
private String reference_others;
private String education;
private String occuapation;
private String customercategory;
private String comments;
private String updateonoffer;
private String sendmeupdateson;
private String company;
private String designation;
private String officebuildingno;
private String officestreet;
private String officearea;
private String officecity;
private String officepincode;
private String officestate;
private String officecountry;
private String mariagestatus;
private String marrigeanniversary;
private int noofpeople;
private String hobbies;
private String favoritebrand;
private String restaurant;
private String shoppingdestination;
private String holidayspot;
private String preferredairline;
private String favouritecar;
private String admiredperson;
private String freefield1;
private String freefield2;
private String freefield3;
private String freefield4;
private String freefield5;
private String freefield6;
private String freefield7;
private String freefield8;
private String freefield9;
private String freefield10;
//private String createdby;
private String priority_telephoneno;
private String priority_officeno;
private String priority_mobileno;
private String priority_email;
private String priority_faxno;
private String reference_tv;
private String reference_hoarding;
private String reference_friends;
private String reference_newspaper;
private String reference_leaflet;
private String cust_ref_no;
private String relationship;
private ArrayList<FamilyDetailPOJO> familydetails;
private String createdBy;
private String modifiedBy;
private String createdTime;
private int createdDate;
private int fiscalYear;
private String modifiedTime;
private int modifiedDate;
private ArrayList<String> companyCodes;
//Start - GSTN NO. Code added by Thangaraj 25.05.2017
private String gstn_no;
//End - GSTN NO. Code added by Thangaraj 25.05.2017
private String tabid;//Added by Balachander V on 5.9.2019 to send SFDC
private String enrollmentOtp;

    public String getStorecode() {
        return storecode;
    }

    public void setStorecode(String storecode) {
        this.storecode = storecode;
    }

    public String getLoyalityno() {
        return loyalityno;
    }

    public void setLoyalityno(String loyalityno) {
        this.loyalityno = loyalityno;
    }

    public String getDatasheetno() {
        return datasheetno;
    }

    public void setDatasheetno(String datasheetno) {
        this.datasheetno = datasheetno;
    }

    public String getCustomercode() {
        return customercode;
    }

    public void setCustomercode(String customercode) {
        this.customercode = customercode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomerfirstname() {
        return customerfirstname;
    }

    public void setCustomerfirstname(String customerfirstname) {
        this.customerfirstname = customerfirstname;
    }

    public String getCustomerlastname() {
        return customerlastname;
    }

    public void setCustomerlastname(String customerlastname) {
        this.customerlastname = customerlastname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getDob() {
        return dob;
    }

    public void setDob(int dob) {
        this.dob = dob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getHouseno() {
        return houseno;
    }

    public void setHouseno(String houseno) {
        this.houseno = houseno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTelephoneno() {
        return telephoneno;
    }

    public void setTelephoneno(String telephoneno) {
        this.telephoneno = telephoneno;
    }

    public String getOfficeno() {
        return officeno;
    }

    public void setOfficeno(String officeno) {
        this.officeno = officeno;
    }

    public String getOfficenoextn() {
        return officenoextn;
    }

    public void setOfficenoextn(String officenoextn) {
        this.officenoextn = officenoextn;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaxno() {
        return faxno;
    }

    public void setFaxno(String faxno) {
        this.faxno = faxno;
    }

    public String getFaxextn() {
        return faxextn;
    }

    public void setFaxextn(String faxextn) {
        this.faxextn = faxextn;
    }

    public String getPreferredcommunication() {
        return preferredcommunication;
    }

    public void setPreferredcommunication(String preferredcommunication) {
        this.preferredcommunication = preferredcommunication;
    }

    public String getReference_others() {
        return reference_others;
    }

    public void setReference_others(String reference) {
        this.reference_others = reference;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getOccuapation() {
        return occuapation;
    }

    public void setOccuapation(String occuapation) {
        this.occuapation = occuapation;
    }

    public String getCustomercategory() {
        return customercategory;
    }

    public void setCustomercategory(String customercategory) {
        this.customercategory = customercategory;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUpdateonoffer() {
        return updateonoffer;
    }

    public void setUpdateonoffer(String updateonoffer) {
        this.updateonoffer = updateonoffer;
    }

    public String getSendmeupdateson() {
        return sendmeupdateson;
    }

    public void setSendmeupdateson(String sendmeupdateson) {
        this.sendmeupdateson = sendmeupdateson;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getOfficebuildingno() {
        return officebuildingno;
    }

    public void setOfficebuildingno(String officebuildingno) {
        this.officebuildingno = officebuildingno;
    }

    public String getOfficestreet() {
        return officestreet;
    }

    public void setOfficestreet(String officestreet) {
        this.officestreet = officestreet;
    }

    public String getOfficearea() {
        return officearea;
    }

    public void setOfficearea(String officearea) {
        this.officearea = officearea;
    }

    public String getOfficecity() {
        return officecity;
    }

    public void setOfficecity(String officecity) {
        this.officecity = officecity;
    }

    public String getOfficepincode() {
        return officepincode;
    }

    public void setOfficepincode(String officepincode) {
        this.officepincode = officepincode;
    }

    public String getOfficestate() {
        return officestate;
    }

    public void setOfficestate(String officestate) {
        this.officestate = officestate;
    }

    public String getOfficecountry() {
        return officecountry;
    }

    public void setOfficecountry(String officecountry) {
        this.officecountry = officecountry;
    }

    public String getMariagestatus() {
        return mariagestatus;
    }

    public void setMariagestatus(String mariagestatus) {
        this.mariagestatus = mariagestatus;
    }

    public String getMarrigeanniversary() {
        return marrigeanniversary;
    }

    public void setMarrigeanniversary(String marrigeanniversary) {
        this.marrigeanniversary = marrigeanniversary;
    }

    public int getNoofpeople() {
        return noofpeople;
    }

    public void setNoofpeople(int noofpeople) {
        this.noofpeople = noofpeople;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getFavoritebrand() {
        return favoritebrand;
    }

    public void setFavoritebrand(String favoritebrand) {
        this.favoritebrand = favoritebrand;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getShoppingdestination() {
        return shoppingdestination;
    }

    public void setShoppingdestination(String shoppingdestination) {
        this.shoppingdestination = shoppingdestination;
    }

    public String getHolidayspot() {
        return holidayspot;
    }

    public void setHolidayspot(String holidayspot) {
        this.holidayspot = holidayspot;
    }

    public String getPreferredairline() {
        return preferredairline;
    }

    public void setPreferredairline(String preferredairline) {
        this.preferredairline = preferredairline;
    }

    public String getFavouritecar() {
        return favouritecar;
    }

    public void setFavouritecar(String favouritecar) {
        this.favouritecar = favouritecar;
    }

    public String getAdmiredperson() {
        return admiredperson;
    }

    public void setAdmiredperson(String admiredperson) {
        this.admiredperson = admiredperson;
    }

    public String getFreefield1() {
        return freefield1;
    }

    public void setFreefield1(String freefield1) {
        this.freefield1 = freefield1;
    }

    public String getFreefield2() {
        return freefield2;
    }

    public void setFreefield2(String freefield2) {
        this.freefield2 = freefield2;
    }

    public String getFreefield3() {
        return freefield3;
    }

    public void setFreefield3(String freefield3) {
        this.freefield3 = freefield3;
    }

    public String getFreefield4() {
        return freefield4;
    }

    public void setFreefield4(String freefield4) {
        this.freefield4 = freefield4;
    }

    public String getFreefield5() {
        return freefield5;
    }

    public void setFreefield5(String freefield5) {
        this.freefield5 = freefield5;
    }

    public String getFreefield6() {
        return freefield6;
    }

    public void setFreefield6(String freefield6) {
        this.freefield6 = freefield6;
    }

    public String getFreefield7() {
        return freefield7;
    }

    public void setFreefield7(String freefield7) {
        this.freefield7 = freefield7;
    }

    public String getFreefield8() {
        return freefield8;
    }

    public void setFreefield8(String freefield8) {
        this.freefield8 = freefield8;
    }

    public String getFreefield9() {
        return freefield9;
    }

    public void setFreefield9(String freefield9) {
        this.freefield9 = freefield9;
    }

    public String getFreefield10() {
        return freefield10;
    }

    public void setFreefield10(String freefield10) {
        this.freefield10 = freefield10;
    }

    public String getPriority_telephoneno() {
        return priority_telephoneno;
    }

    public void setPriority_telephoneno(String priority_telephoneno) {
        this.priority_telephoneno = priority_telephoneno;
    }

    public String getPriority_officeno() {
        return priority_officeno;
    }

    public void setPriority_officeno(String priority_officeno) {
        this.priority_officeno = priority_officeno;
    }

    public String getPriority_mobileno() {
        return priority_mobileno;
    }

    public void setPriority_mobileno(String priority_mobileno) {
        this.priority_mobileno = priority_mobileno;
    }

    public String getPriority_email() {
        return priority_email;
    }

    public void setPriority_email(String priority_emailno) {
        this.priority_email = priority_emailno;
    }

    public String getPriority_faxno() {
        return priority_faxno;
    }

    public void setPriority_faxno(String priority_faxno) {
        this.priority_faxno = priority_faxno;
    }

    public String getReference_tv() {
        return reference_tv;
    }

    public void setReference_tv(String reference_tv) {
        this.reference_tv = reference_tv;
    }

    public String getReference_hoarding() {
        return reference_hoarding;
    }

    public void setReference_hoarding(String reference_hoarding) {
        this.reference_hoarding = reference_hoarding;
    }

    public String getReference_friends() {
        return reference_friends;
    }

    public void setReference_friends(String reference_friends) {
        this.reference_friends = reference_friends;
    }

    public String getReference_newspaper() {
        return reference_newspaper;
    }

    public void setReference_newspaper(String reference_newspaper) {
        this.reference_newspaper = reference_newspaper;
    }

    public String getReference_leaflet() {
        return reference_leaflet;
    }

    public void setReference_leaflet(String reference_leaflet) {
        this.reference_leaflet = reference_leaflet;
    }

    public String getCust_ref_no() {
        return cust_ref_no;
    }

    public void setCust_ref_no(String cust_ref_no) {
        this.cust_ref_no = cust_ref_no;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public ArrayList<FamilyDetailPOJO> getFamilydetails() {
        return familydetails;
    }

    public void setFamilydetails(ArrayList<FamilyDetailPOJO> familydetails) {
        this.familydetails = familydetails;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public int getFiscalYear() {
        return fiscalYear;
    }

    public void setFiscalYear(int fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    public String getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(int modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ArrayList<String> getCompanyCode() {
        return companyCodes;
    }

    public void setCompanyCode(ArrayList<String> companyCodes) {
        this.companyCodes = companyCodes;
    }
//Start - GSTN NO. Code added by Thangaraj 25.05.2017
    public String getGstn_no() {
        return gstn_no;
    }

    public void setGstn_no(String gstn_no) {
        this.gstn_no = gstn_no;
    }
//End - GSTN NO. Code added by Thangaraj 25.05.2017

    //Added by Balachander V on 5.9.2019 to send SFDC
    public String getTabid() {
        return tabid;
    }

    public void setTabid(String tabid) {
        this.tabid = tabid;
    }
  //Ended by Balachander V on 5.9.2019 to send SFDC

    public String getEnrollmentOtp() {
        return enrollmentOtp;
    }

    public void setEnrollmentOtp(String enrollmentOtp) {
        this.enrollmentOtp = enrollmentOtp;
    }
    
}
