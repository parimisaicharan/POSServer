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
 * Common Interface for Activation of all Webservices
 * Each Business class (DO) should implements this interface and write the 
 * curresponding websevice activation details in the execute method 
 * 
 * 
 */
package ISRetail.Webservices;



public interface Webservice {
    
    
    /**
    * Execute Method for all Web services 
    */   
    public String execute(DataObject obj,String updateType);
    
}
