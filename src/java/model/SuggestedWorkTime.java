package model;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * <code>SuggestedStudyTime</code> Entity class for SuggestedStudyTime
 *
 * @author Rahman Firouzi
 */
@NamedQueries({
    @NamedQuery(name = "SuggestedWorkTime.findById",
            query = "SELECT swt FROM SuggestedWorkTime swt WHERE swt.id LIKE :id"),
    @NamedQuery(name = "SuggestedWorkTime.findByAssistance",
            query = "SELECT stt FROM SuggestedWorkTime stt WHERE stt.assistance.person.email LIKE :assistanceEmail"),
    @NamedQuery(name = "SuggestedWorkTime.getAll",
            query = "SELECT stt FROM SuggestedWorkTime stt")
})

@Entity
@Table(name = "SUGGESTED_WORK_TIME")
public class SuggestedWorkTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String startDate;
    private String endDate;

    @OneToOne(cascade = CascadeType.REMOVE)
    
    @JoinColumn(name = "Assistance", nullable = false)
    private Assistance assistance;

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

    public Assistance getAssistance() {
        return assistance;
    }

    public void setAssistance(Assistance assistance) {
        this.assistance = assistance;
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
