
package model;

import java.util.Objects;
import javax.persistence.*;

/**
 * <code>Ability</code> Entity class for Ability
 * 
 *  @author Rahman Firouzi
 */

@NamedQueries({
    
    @NamedQuery(name = "Ability.getall", 
            query = "SELECT a FROM ABILITY a")
})

@Entity(name = "ABILITY")
public class Ability implements AbilityDTO  {

    @Id
    @Column(name="ABILITYNAME")
    private String abilityName;

    @Override
    public String getAbilityName() {
        return abilityName;
    }
/**
 * method sets the name of the ability
 * @param abilityName 
 */
    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    @Override
    public String toString() {
        return abilityName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.abilityName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ability other = (Ability) obj;
        if (!Objects.equals(this.abilityName, other.abilityName)) {
            return false;
        }
        return true;
    }
    
    

}
