
package model;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;


/**
 * <code>Assistance</code> Entity class for Assistance
 * 
 *  @author Rahman Firouzi
 */
@NamedQueries({
    @NamedQuery(name = "Assistance.findByEmail",
            query = "SELECT assis FROM ASSISTANCE assis WHERE assis.person.email LIKE :assistanceEmail"),
    
    @NamedQuery(name = "Assistance.getAll",
            query = "SELECT assis FROM ASSISTANCE assis")
  
})


@Entity(name= "ASSISTANCE")
public class Assistance implements AssistanceDTO {

    private static final long serialVersionUID = 1L;
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    
    @JoinColumn(name = "EMAIL", nullable = false, unique = true)
    private Person person;
        
    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinTable(name = "ABILITIES",
            joinColumns={ @JoinColumn(name ="EMAIL")},
            inverseJoinColumns ={@JoinColumn(name="ABILITYNAME")})
    private List<Ability> abilities = new ArrayList<>();
    
    
    


    public Assistance() {
    }

    public Assistance(Person person) {
        this.person = person;

    }

    @Override
    public Person getPerson() {
        return person;
    }
/**
 * sets person to the given value
 * @param person the person to persist
 */
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public List<Ability> getAbilities() {
        return abilities;
    }
/**
 * sets abilities to the given ability list
 * @param abilities  a list of ability
 */
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

     

    public void addAbility(Ability abilitiyToAdd) {
        abilities.add(abilitiyToAdd);
    }
    public void removeAbility(Ability abilitiyToremove){
        abilities.remove(abilitiyToremove);
    }
}
