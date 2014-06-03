package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * <code>WorkshopTime</code> Entity class for WorkshopTime
 *
 * @author Rahman Firouzi
 */
@NamedQueries({
    @NamedQuery(name = "WorkshopTime.findById",
            query = "SELECT sst FROM WorkshopTime sst WHERE sst.id LIKE :id"),

    @NamedQuery(name = "WorkshopTime.allEvent",
            query = "SELECT sst FROM WorkshopTime sst")
})

@Entity(name = "WorkshopTime")
public class WorkshopTime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;
    private String startDate;
    private String endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkshopTime)) {
            return false;
        }
        WorkshopTime other = (WorkshopTime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.workshopTime[ id=" + id + " ]";
    }

}
