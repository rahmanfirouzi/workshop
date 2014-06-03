

package model;

import java.io.Serializable;

/**
 * <code>Ability</code> Entity class for Ability
 * 
 *  @author Rahman Firouzi
 */
public interface AbilityDTO extends Serializable {
    /**
     * method returns the name of the ability
     * @return 
     */
    String getAbilityName();

    @Override
    String toString();
    
    
}
