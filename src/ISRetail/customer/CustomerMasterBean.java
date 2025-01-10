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
 * This class file is used to encapsulate the Customer Master Bean
 * This represent the Customer Master Object that is to be send across the Webservice
 * There is a set of Getter and Setter Methods
 * 
 * 
 */

package ISRetail.customer;
import ISRetail.Webservices.DataObject;        
import java.util.Collection;

public class CustomerMasterBean  implements DataObject {
    
    private CustomerMasterPOJO customer ; 
    private Collection<FamilyDetailPOJO> family;

    public CustomerMasterPOJO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerMasterPOJO customer) {
        this.customer = customer;
    }

    public Collection<FamilyDetailPOJO> getFamily() {
        return family;
    }

    public void setFamily(Collection<FamilyDetailPOJO> family) {
        this.family = family;
    }
}
