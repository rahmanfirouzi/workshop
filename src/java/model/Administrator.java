package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * <code>Administrator</code> Entity class for Administrator
 * 
 *  @author Rahman Firouzi
 */

@NamedQueries({
    @NamedQuery(name = "Administrator.findByEmail",
            query = "SELECT admin FROM Administrator admin WHERE admin.person.email LIKE :email"),
    
    
    @NamedQuery(name = "Administrator.getAll",
            query = "SELECT admins FROM Administrator admins")
})


@Entity(name ="Administrator")
public class Administrator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    
    @OneToOne
    @JoinColumn(name="Person",nullable = false)
    private Person person;
    

    public Person getPerson() {
        return person;
    }
/**
 * Method sets the Administrator Id to a given person
 * @param person    the person to be administrator
 */
    public void setPerson(Person person) {
        this.person = person;
    }

   
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Administrator)) {
            return false;
        }
        Administrator other = (Administrator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Administrator[ id=" + id + " ]";
    }
    
}
