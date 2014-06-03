package controller;

import com.dhtmlx.planner.DHXEventsManager;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.*;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Ability;
import model.Assistance;
import model.SuggestedStudyTime;
import model.SuggestedWorkTime;
import model.AssistanceDTO;
import model.Person;
import model.PersonDTO;
import model.WorkshopTime;

/**
 * <code>ApplicationController</code> class is an controller class which passes
 * view requests to the entity classes for execution. this class should handles
 * non user specific requests
 *
 * @author Rahman Firouzi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class ApplicationController {

    @PersistenceContext(unitName = "workshopPU")
    private EntityManager em;

    /**
     * returns all the suggested workshop events by assistance
     *
     * @return a list of events
     */
    public ArrayList<Event> getAllSuggestedWorkTime() {
        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
        ArrayList<Event> evs = new ArrayList<>();
        List<SuggestedWorkTime> swt = em.createNamedQuery("SuggestedWorkTime.getAll", SuggestedWorkTime.class).
                getResultList();

        SuggestedWorkTime s;

        for (int i = swt.size(); i > 0; i--) {
            Event event = new Event();
            s = swt.get(i - 1);
            event.setStart_date(s.getStartDate());
            event.setEnd_date(s.getEndDate());
            event.setId(s.getId().intValue());
            event.setColor(event.SUGGESTED_WORK_TIME);
            event.setText(s.getDescription() + " /" + s.getAssistance().getPerson().getEmail());
            event.setReadonly(true);
            evs.add(event);
        }

        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";

        return evs;

    }

    /**
     * Method will return all the suggested events by students
     *
     * @return an Arraylist of events with all the suggested events of students
     */
    public ArrayList<Event> getSuggestedWorkshop() {
        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";

        List<SuggestedStudyTime> sst = em.createNamedQuery("SuggestedStudyTime.allEvent", SuggestedStudyTime.class).
                getResultList();
        ArrayList<Event> evs = new ArrayList();
        SuggestedStudyTime s;
        Event event;

        for (int i = 0; i < sst.size(); i++) {
            event = new Event();
            s = sst.get(i);
            event.setStart_date(s.getStartDate());
            event.setEnd_date(s.getEndDate());
            event.setText(s.getDescription() + " / " + s.getPerson().getEmail());
            event.setReadonly(true);
            event.setId(s.getId().intValue());
            evs.add(event);
        }

        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";
        return evs;
    }

    /**
     * Method will return all the abilities in database
     *
     * @return all the abilities in database
     */
    public List<Ability> getAllAbilities() {

        List<Ability> b = new ArrayList<>();

        try {
            b = em.createNamedQuery("Ability.getall", Ability.class).getResultList();
        } catch (NullPointerException e) {
        }
        return b;
    }

    /**
     * Method returns the assistanceDTO of the given person
     *
     * @param person the person whos assistancDTO is wanted
     * @return returns the assistanceDTO of the person
     */
    public AssistanceDTO getAssistance(PersonDTO person) {
        List<Assistance> as = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                .setParameter("assistanceEmail", person.getEmail()).getResultList();
        if (as.isEmpty()) {
            return null;

        } else {
            return as.get(0);
        }

    }

    /**
     * Method returns the assistance which the id is given
     *
     * @param assistanceId the ID of the assistance
     * @return an object of type AssistanceDTO
     * @throws RejectException throws if the assistance does not found
     */
    public AssistanceDTO getAssistance(long assistanceId) throws RejectException {
        AssistanceDTO p;
        try {
            p = em.find(Assistance.class, assistanceId);
        } catch (NullPointerException e) {
            throw new RejectException("person Not found!");
        }
        return p;
    }

    /**
     * method returns all the assistances in database
     *
     * @return all the assistances in database
     */
    public List<? extends AssistanceDTO> getAssistances() {
        List<? extends AssistanceDTO> ass;
        ass = em.createNamedQuery("Assistance.getAll", Assistance.class).getResultList();
        return ass;

    }

    /**
     * not implementet method
     *
     * @param b
     * @return
     */
    public boolean setStudentSummeryMethod(boolean b) {
        return b;

    }

    /**
     * method return the a personDTO of the given userID
     *
     * @param userId the userId of the person
     * @return return an object of type PersonDTO
     * @throws RejectException throws if the person is not founded
     */
    public PersonDTO getPerson(long userId) throws RejectException {
        PersonDTO ass;
        ass = em.find(Person.class, userId);
        return ass;
    }

    /**
     * returns all the workshop events
     *
     * @return an arraylist of events
     */
    public ArrayList<Event> getAllWorkshopEvents() {
        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";

        ArrayList<Event> evs = new ArrayList<>();
        List<WorkshopTime> swt = em.createNamedQuery("WorkshopTime.allEvent", WorkshopTime.class).
                getResultList();
        WorkshopTime s;
        Event event;
        for (int i = swt.size(); i > 0; i--) {
            event = new Event();
            s = swt.get(i - 1);
            event.setStart_date(s.getStartDate());
            event.setEnd_date(s.getEndDate());
            event.setId(s.getId().intValue());
            event.setText(s.getDescription());
            event.setColor(event.WORKSHOP_TIME);
            event.setReadonly(true);
            evs.add(event);
        }

        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";
        return evs;
    }
    /**
     *
     * @param email
     * @return      *
     * public ArrayList<Event> getAllEvents(String email) {
     *
     * DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss"; ArrayList<Event>
     * evs = new ArrayList<>();
     *
     * List<Assistance> assistance =
     * em.createNamedQuery("Assistance.findByEmail", Assistance.class)
     * .setParameter("assistanceEmail", email).getResultList();
     *
     * if (assistance == null) {
     *
     * DHXEventsManager.date_format = "MM/dd/yyyy HH:mm:ss"; return evs;
     *
     * } else { List<SuggestedWorkTime> swt =
     * em.createNamedQuery("SuggestedWorkTime.findByAssistance",
     * SuggestedWorkTime.class). setParameter("assistanceEmail",
     * email).getResultList();
     *
     * SuggestedWorkTime s;
     *
     * for (int i = swt.size(); i > 0; i--) { Event event = new Event(); s =
     * swt.get(i - 1); event.setStart_date(s.getStartDate());
     * event.setEnd_date(s.getEndDate()); event.setId(s.getId().intValue());
     * event.setColor(event.SUGGESTED_WORK_TIME);
     * event.setText(s.getDescription() + " /" +
     * s.getAssistance().getPerson().getEmail()); evs.add(event); } }
     *
     * DHXEventsManager.date_format = "MM/dd/yyyy HH:mm"; return evs; }
     *
     *
     */
}
