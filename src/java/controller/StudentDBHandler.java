
package controller;

import com.dhtmlx.planner.DHXEventsManager;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.*;

/**
 * <code>StudentDBHandler</code> class is an controller class which passes view
 * requests to the entity classes for execution. this class should handles all
 * the request for an student.
 *
 * @author Rahman Firouzi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class StudentDBHandler {

    @PersistenceContext(unitName = "workshopPU")
    private EntityManager em;

    public StudentDBHandler() {

    }
/**
 * Method inserts an event into database
 * 
 * @param startDate the date when an event started
 * @param endDate   the date when the event ends
 * @param description   description in the event
 * @param email         email adress of whom wanted to insert an event
 * @return          returns the integer of the method ID 
 * @throws RejectException throws if student could not be found.
 */
    public Integer insertEvent(String startDate, String endDate, String description, String email) throws RejectException {

        Person person = null;
        List<Person> persons = em.createNamedQuery("Person.findByEmail", Person.class).
                setParameter("email", email).getResultList();

        if (persons.isEmpty()) {
            throw new RejectException("Student not found");
        } else {
            person = persons.get(0);
        
         SuggestedStudyTime st = new SuggestedStudyTime();

        st.setDescription(description);
        st.setEndDate(endDate);
        st.setPerson(person);
        st.setStartDate(startDate);
        em.persist(st);
        return st.getId().intValue();
        
        }
    }
 /**
     * Method update the information of an event
     *
     * @param start_date the new date and time for the start date and time value
     * @param end_date the new date and time for the ends date and time value.
     * @param description the new description to be replace.
     * @param id the ID of the event in which to change the info.
     */
    public void updateEvent(String start_date, String end_date, String description, Long id) {

        List<SuggestedStudyTime> sst = em.createNamedQuery("SuggestedStudyTime.findById", SuggestedStudyTime.class).
                setParameter("id", id).getResultList();

        if (sst.isEmpty()) {
            try {
                throw new RejectException("no event founded!");
            } catch (RejectException ex) {
                Logger.getLogger("couldnt found the Event in SuggestedSttudy Table!");
            }
            return;
        }

        sst.get(0).setDescription(description);
        sst.get(0).setEndDate(end_date);
        sst.get(0).setStartDate(start_date);

    }

    /**
     * Method returns all the suggested study events buy all students  
     * @return all the suggested study events buy all students
     
    */
    public ArrayList<Event> getAllEvents() {
        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
        ArrayList<Event> evs = new ArrayList();

        List<SuggestedStudyTime> swt = em.createNamedQuery("SuggestedStudyTime.allEvent", SuggestedStudyTime.class).
                getResultList();
        SuggestedStudyTime s;

        for (int i = swt.size(); i > 0; i--) {
            Event e = new Event();
            s = swt.get(i - 1);
            e.setStart_date(s.getStartDate());
            e.setEnd_date(s.getEndDate());
            e.setId(s.getId().intValue());
            e.setText(s.getDescription());
            evs.add(e);
        }
        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";
        return evs;
    }
/**
 * method returns all the suggested study events by the give user
 * @param email the email adress of the user
 * @return      returns a list of event
 * @throws RejectException  throws if the user does not found.
 */
    public ArrayList<Event> getAllStudentEvents(String email) throws RejectException {

        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
        Event event;

        ArrayList<Event> evs = new ArrayList<>();
       
        List<Person> p = em.createNamedQuery("Person.findByEmail", Person.class).
                setParameter("email", email).getResultList();
        if (p.isEmpty()) {
            throw new RejectException("Studen not found!");
        } else {
            
            List<SuggestedStudyTime> sst = em.createNamedQuery("SuggestedStudyTime.findByPersonId", SuggestedStudyTime.class).
                    setParameter("email", p.get(0).getEmail()).getResultList();
            SuggestedStudyTime st;

            for (int i = sst.size(); i > 0; i--) {
                
                event = new Event();
                st = sst.get(i - 1);
                event.setStart_date(st.getStartDate());
                event.setEnd_date(st.getEndDate());
                event.setId(st.getId().intValue());
                event.setText(st.getDescription());
                event.setColor(event.SUGGESTED_STUDY_TIME);
                evs.add(event);
            }
        }
        
        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";
        return evs;
    }
/**
     * Method delete an event from database.
     *
     * @param id the ID of the event to delete
     *
     */
    public void deleteEvent(Integer id) throws RejectException {

        List<SuggestedStudyTime> sst = em.createNamedQuery("SuggestedStudyTime.findById", SuggestedStudyTime.class).
                setParameter("id", id).getResultList();
        em.remove(sst.get(0));

    }

    
    /**
     * Method returns the person 
     * @param email the email adress of the person
     * @return  returns the person if it found.
     * @throws RejectException 
     */
    private Person getPerson(String email)throws RejectException{
        List<Person> p = em.createNamedQuery("Person.findByEmail", Person.class).
                setParameter("email", email).getResultList();
        
        if(p.isEmpty()){
            throw new RejectException("Studen not found!");
        }else{
             return p.get(0);
        }
       
    }
}
