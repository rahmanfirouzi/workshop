
package controller;

import com.dhtmlx.planner.DHXEventsManager;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Ability;
import model.Assistance;
import model.AssistanceDTO;
import model.Person;
import model.PersonDTO;
import model.SuggestedWorkTime;

/**
 * <code>AdminDBHandler</code> class is an controller class which passes view
 * requests to the entity classes for execution. this class should handles all
 * the request for an assistance.
 *
 * @author Rahman Firouzi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AssistanceDBHandler {

    @PersistenceContext(unitName = "workshopPU")
    private EntityManager em;

    public AssistanceDBHandler() {

    }

     /**
     * Method insert an event into database
     *
     * @param startDate the start date and time of which the event begains
     * @param endDate the end date and time of which the event ends.
     * @param description the description of the event
     * @param email the email address of whom wanted to place the event
     * @return returns the ID of the event.
     */
    public Integer insertEvent(String startDate, String endDate, String description, String email) throws RejectException {

        List<Assistance> assistances = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                .setParameter("assistanceEmail", email).getResultList();

        if (assistances.isEmpty()) {
            throw new RejectException("User is not a assistance!");
        } else {
            SuggestedWorkTime st = new SuggestedWorkTime();

            st.setDescription(description);
            st.setEndDate(endDate);
            st.setAssistance(assistances.get(0));
            st.setStartDate(startDate);
            em.persist(st);

            return st.getId().intValue();

        }

    }

    /**
     * change event the info of the given event
     *
     * @param start_date The new start date and time to be set
     * @param end_date the new end date and time to be set
     * @param description the description of the event
     * @param id the id of the event
     * @param email the email address of the user who own the event
     * @throws RejectException throws if the event is not found.
     */
    public void updateEvent(String start_date, String end_date, String description, Long id, String email) throws RejectException {

        List<SuggestedWorkTime> sst = em.createNamedQuery("SuggestedWorkTime.findById", SuggestedWorkTime.class).
                setParameter("id", id).getResultList();
        if (sst.isEmpty()) {
            throw new RejectException("Event not found!");
        }
        String eventEmail = sst.get(0).getAssistance().getPerson().getEmail();
        if (eventEmail.equals(email)) {
            sst.get(0).setDescription(description);
            sst.get(0).setEndDate(end_date);
            sst.get(0).setStartDate(start_date);
        }
    }

    /**
     * Get all the suggested work times by the given assistance email adress
     *
     * @param email the email adress of the assistance
     * @return a list of events of the suggested work time work times by the
     * assistance
     *
     */
    public ArrayList<Event> getSuggestedWorkTimeEventByAssistance(String email) {

        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";
        ArrayList<Event> evs = new ArrayList<>();

        List<Assistance> assistance = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                .setParameter("assistanceEmail", email).getResultList();

        if (assistance == null) {

            DHXEventsManager.date_format = "MM/dd/yyyy HH:mm:ss";
            return evs;

        } else {
            List<SuggestedWorkTime> swt = em.createNamedQuery("SuggestedWorkTime.findByAssistance", SuggestedWorkTime.class).
                    setParameter("assistanceEmail", email).getResultList();

            SuggestedWorkTime s;

            for (int i = swt.size(); i > 0; i--) {
                Event event = new Event();
                s = swt.get(i - 1);
                event.setStart_date(s.getStartDate());
                event.setEnd_date(s.getEndDate());
                event.setId(s.getId().intValue());
                event.setColor(event.SUGGESTED_WORK_TIME);
                event.setText(s.getDescription() + " /" + s.getAssistance().getPerson().getEmail());
                event.setReadonly(false);
                evs.add(event);
            }
        }

        DHXEventsManager.date_format = "MM/dd/yyyy HH:mm";
        return evs;
    }

    /**
     * Method delete the given event from database.
     *
     * @param id the Id of the event to get delete
     * @throws RejectException throws if the event does not found.
     */
    public void deleteEvent(Integer id) throws RejectException {

        List<SuggestedWorkTime> swt = em.createNamedQuery("SuggestedWorkTime.findById", SuggestedWorkTime.class).
                setParameter("id", id).getResultList();

        if (swt.isEmpty()) {
            throw new RejectException("no event found!");
        }

        em.remove(swt.get(0));

    }

    /**
     * Method register a person to assistance
     *
     * @param person the person to be assistance
     */
    public void registerAssistance(Person person) {
        Assistance assistance = new Assistance(person);
        em.persist(assistance);

    }

    /**
     * Method returns a person by the given email address
     *
     * @param email the email address of the person
     * @return returns the person if the method finds it otherwise null will be
     * returned.
     */
    private Person getPerson(String email) {
        List<Person> persons = em.createNamedQuery("Person.findByEmail", Person.class)
                .setParameter("email", email).getResultList();

        if (persons.isEmpty()) {
            return null;
        } else {
            return persons.get(0);
        }
    }

    /**
     * returns the assistanceDTO
     *
     * @param email the email address
     * @return returns the assistance if it was found otherwise it returns null.
     */
    public AssistanceDTO getAssistance(String email) {
        List<Assistance> as;
        try {
            as = em.createNamedQuery("Assistance.findByEmail", Assistance.class).
                    setParameter("assistanceEmail", email).getResultList();

        } catch (Exception e) {
            return null;
        }

        return as.get(0);
    }

    /**
     * Method returns the abilities of the given assistance
     *
     * @param email the email adress of the assistance
     * @return returns a list of abilities if it found the assistance, otherwise
     * it returns null
     */
    public List<Ability> getAssistanceAbilities(String email) {

        AssistanceDTO as = getAssistance(email);

        if (as == null) {
            return null;
        }

        return as.getAbilities();
    }

    /**
     * Removes the abilities from the given assistance
     *
     * @param abilitiyToremove a list of ability to remove from the assistances
     * ability
     * @param assistanceId the id of the assistance
     */
    public void removeAbilities(List<Ability> abilitiyToremove, long assistanceId) {

        Assistance assis = em.find(Assistance.class, assistanceId);
        List<Ability> ab = assis.getAbilities();
        for (int temp = 0; temp < abilitiyToremove.size(); temp++) {
            Ability ability = em.find(Ability.class, abilitiyToremove.get(temp));
            assis.removeAbility(ability);
        }
    }

    /**
     * Checks if the person is an assistance
     *
     * @param person a person to check
     * @return returns true if the person is assistance, otherwise it returns
     * false.
     */
    public boolean isAssistance(PersonDTO person) {
        List<Assistance> assis;
        try {
            assis = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                    .setParameter("assistanceEmail", person.getEmail()).getResultList();
        } catch (java.lang.NullPointerException e) {
            return false;
        }
        return !assis.isEmpty();
    }

    /**
     * Method adds new ability to a assistance
     *
     * @param abilitiesToAdd A list of abilities to add to
     * @param assistanceId the asisstance ID to add the abilities to
     */
    public void addAbilities(List<Ability> abilitiesToAdd, long assistanceId) {

        Assistance assistance = em.find(Assistance.class, assistanceId);
        for (int a = 0; a < abilitiesToAdd.size(); a++) {

            Ability ab = em.find(Ability.class, abilitiesToAdd.get(a));
            assistance.addAbility(ab);
        }

    }
}
