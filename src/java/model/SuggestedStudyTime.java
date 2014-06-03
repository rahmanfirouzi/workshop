package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * <code>SuggestedStudyTime</code> Entity class for SuggestedStudyTime
 *
 * @author Rahman Firouzi
 */
@NamedQueries({
    @NamedQuery(name = "SuggestedStudyTime.findById",
            query = "SELECT sst FROM SuggestedStudyTime sst WHERE sst.id LIKE :id"),
    @NamedQuery(name = "SuggestedStudyTime.findByPersonId",
            query = "SELECT sst FROM SuggestedStudyTime sst WHERE sst.person.email LIKE :email"),
    @NamedQuery(name = "SuggestedStudyTime.allEvent",
            query = "SELECT sst FROM SuggestedStudyTime sst")
})

@Entity(name = "SuggestedStudyTime")
public class SuggestedStudyTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String description;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "Person")
    private Person person;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Person getPerson() {
        return person;
    }

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
        if (!(object instanceof SuggestedStudyTime)) {
            return false;
        }
        SuggestedStudyTime other = (SuggestedStudyTime) object;
        return (this.id != null || other.id == null) && (this.id == null || this.id.equals(other.id));
    }

    @Override
    public String toString() {
        return "model.SuggestedStudyTime[ id=" + id + " ]";
    }

}
