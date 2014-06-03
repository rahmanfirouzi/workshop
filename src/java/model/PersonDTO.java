
package model;

import java.io.Serializable;

/**
 * <code>Person</code> Entity class for Person
 * 
 *  @author Rahman Firouzi
 */
public interface PersonDTO extends Serializable {
    String getLastName();
    String getEmail();
    String getFirstName();
    Long getId();
    String getPassorwd();
    String getFullname();
}
