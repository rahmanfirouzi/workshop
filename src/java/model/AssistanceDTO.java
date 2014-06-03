
package model;

import java.io.Serializable;
import java.util.List;

/**
 * <code>Assistance</code> Entity class for Assistance
 * 
 *  @author Rahman Firouzi
 */
public interface AssistanceDTO extends Serializable {
/**
 * Method returns abilities of the given Assistance
 * @return 
 */
    List<Ability> getAbilities();

    Person getPerson();

  

    long getId();

}
