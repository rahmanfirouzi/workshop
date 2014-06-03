package controller;

import com.dhtmlx.planner.DHXEventsManager;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.*;

/**
 * <code>AdminDBHandler</code> class is an controller class which passes view
 * requests to the entity classes for execution. this class should handles all
 * the request for an admin.
 *
 * @author Rahman Firouzi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AdminDBHandler {
    @EJB
    private AssistanceDBHandler assistanceDBHandler;

    @PersistenceContext(unitName = "workshopPU")
    private EntityManager em;
    /**
     * Method insert an event into database
     *
     * @param startDate the start date and time of which the event begains
     * @param endDate the end date and time of which the event ends.
     * @param description the description of the event
     * @param email the email address of whom wanted to place the event
     * @return returns the ID of the event.
     */
    public Integer insertEvent(String startDate, String endDate, String description, String email) {

        WorkshopTime st = new WorkshopTime();

        st.setDescription(description);
        st.setEndDate(endDate);
        st.setStartDate(startDate);
        em.persist(st);

        return st.getId().intValue();

    }

    /**
     * Method update the information of an event
     *
     * @param start_date the new date and time for the start date and time value
     * @param end_date the new date and time for the ends date and time value.
     * @param description the new description to be replace.
     * @param id the ID of the event in which to change the info.
     * @throws RejectException in the event is not founded.
     */
    public void updateEvent(String start_date, String end_date, String description, Long id) throws RejectException {

        List<WorkshopTime> sst = em.createNamedQuery("WorkshopTime.findById", WorkshopTime.class).
                setParameter("id", id).getResultList();

        if (sst.isEmpty()) {
            throw new RejectException("no event founded!");
        }
        sst.get(0).setDescription(description);
        sst.get(0).setEndDate(end_date);
        sst.get(0).setStartDate(start_date);
    }
/**
 * returns all the workshop events
 * @return an list of events
 */
    public ArrayList<Event> getAllEvents() {
        DHXEventsManager.date_format = "yyyy-MM-dd HH:mm:ss";

        ArrayList<Event> evs = new ArrayList<>();
        List<WorkshopTime> swt = em.createNamedQuery("WorkshopTime.allEvent", WorkshopTime.class).
                getResultList();
        WorkshopTime s;

        for (int i = swt.size(); i > 0; i--) {
            Event event = new Event();
            s = swt.get(i - 1);
            event.setStart_date(s.getStartDate());
            event.setEnd_date(s.getEndDate());
            event.setId(s.getId().intValue());
            event.setText(s.getDescription());
            event.setColor(event.WORKSHOP_TIME);
            evs.add(event);
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
    public void deleteEvent(Integer id) {

        List<WorkshopTime> sst = em.createNamedQuery("WorkshopTime.findById", WorkshopTime.class).
                setParameter("id", id).getResultList();
        em.remove(sst.get(0));

    }
    
    /**
     * Method returns all the email address if the assistances
     *
     * @return returns a list of assistances email address
     */
    public List<String> getEmailAssistances() {

        List<Assistance> as = em.createNamedQuery("Assistance.getAll", Assistance.class)
                .getResultList();
        List<String> ret = new ArrayList();
        for (Assistance a : as) {
            ret.add(a.getPerson().getEmail());

        }
        return ret;
    }

    /**
     * method adds a new assistance into database
     *
     * @param newAssistanceEmail the email address of the user
     * @throws RejectException if the user does not found.
     */
    public void addnewAssistance(String newAssistanceEmail) throws RejectException {

        List<Person> prs = em.createNamedQuery("Person.findByEmail", Person.class)
                .setParameter("email", newAssistanceEmail).getResultList();

        int l = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                .setParameter("assistanceEmail", newAssistanceEmail).getResultList().size();

        if (l > 0) {
            throw new RejectException("Duplicate entry");
        }
        try {
            Assistance as = new Assistance();
            as.setPerson(prs.get(0));
            em.persist(as);
        } catch (Exception e) {
            throw new RejectException("DatabaseException");
        }

    }

    /**
     * method removes the give assistances
     *
     * @param choosedAssistancesEmailes a list of assistances email adress that
     * should remove.
     */
    public void removeAssistance(List<String> choosedAssistancesEmailes) {

       
        List<SuggestedWorkTime>  evs;
        for (String email : choosedAssistancesEmailes) {

            Assistance s = em.createNamedQuery("Assistance.findByEmail", Assistance.class)
                    .setParameter("assistanceEmail", email).getSingleResult();
                    
           evs = em.createNamedQuery("SuggestedWorkTime.findByAssistance", SuggestedWorkTime.class)
                    .setParameter("assistanceEmail", email).getResultList();
                    for(SuggestedWorkTime e: evs){
                        em.remove(e);
                    }

            em.remove(s);
        }

    }

    /**
     * returns a boolean value whether a user is admin or not.
     *
     * @param person the person to be checked
     * @return returns true if the person is admin, returns false if not.
     */
    public boolean isAdmin(PersonDTO person) {

        List<Administrator> ads = em.createNamedQuery("Administrator.findByEmail", Administrator.class).
                setParameter("email", person.getEmail()).getResultList();
        
        return !ads.isEmpty();

    }

    /**
     * Removes the abilities given
     *
     * @param abilities a list of abilities to remove
     */
    public void removeAbilities(List<Ability> abilities) {

        for (int i = 0; abilities.size() > i; i++) {
            Ability a = em.
                    find(Ability.class, abilities.get(i));

            em.remove(a);
        }

    }

    /**
     * method adds new ability to the database
     *
     * @param newAbility the name of the new ability to add
     * @throws RejectException throws if the ability already exist.
     */
    public void addNewAbility(String newAbility) throws RejectException {

        Ability a = em.find(Ability.class, newAbility);
        if (a != null) {
            throw new RejectException("Duplicate entry");
        }
        try {
            a = new Ability();
            a.setAbilityName(newAbility);
            em.persist(a);
        } catch (Exception e) {
            throw new RejectException("database Exception");
        }
    }

    /**
     * Method checks if the person is super admin or not.
     *
     * @param person the person to check
     * @return true if the user is admin, othervise returns false
     */
    public boolean isSuperAdmin(PersonDTO person) {
        List<Administrator> ad = em.createNamedQuery("Administrator.findByEmail", Administrator.class).
                setParameter("email", person.getEmail()).getResultList();
        if (ad.isEmpty()) {
            System.out.println("current is not an admin");
            return false;
        } else if ("superadmin@kth.se".equals(ad.get(0).getPerson().getEmail().toLowerCase())
                && "super".equals(ad.get(0).getPerson().getFirstName().toLowerCase())
                && "admin".equals(ad.get(0).getPerson().getLastName().toLowerCase())) {

            return true;
        } else {
            return false;

        }

    }

    /**
     * Method adds new admin to the database
     *
     * @param newAdminEmail the new admin email address
     * @throws RejectException throws if the user is already an admin
     */
    public void addnewAdmin(String newAdminEmail) throws RejectException {

        List<Person> prs = em.createNamedQuery("Person.findByEmail", Person.class)
                .setParameter("email", newAdminEmail).getResultList();

        int l = em.createNamedQuery("Administrator.findByEmail", Assistance.class)
                .setParameter("email", newAdminEmail).getResultList().size();

        if (l > 0) {
            throw new RejectException("Duplicate entry");
        }
        try {
            Administrator admin = new Administrator();
            admin.setPerson(prs.get(0));
            em.persist(admin);
        } catch (Exception e) {
            throw new RejectException("DatabaseException");
        }

    }

    /**
     * Method removes admins
     *
     * @param choosedAdminEmailes a list of the email address of the admin to
     * remove
     */
    public void removeAdmin(List<String> choosedAdminEmailes) {

        for (String email : choosedAdminEmailes) {

            Administrator s = em.createNamedQuery("Administrator.findByEmail", Administrator.class)
                    .setParameter("email", email).getSingleResult();

            em.remove(s);
        }

    }

    /**
     * Method returns the email address of all admins in database.
     *
     * @return returns a list of email addresses of all the admins in database
     */
    public List<String> getEmailAdmins() {

        List<Administrator> as = em.createNamedQuery("Administrator.getAll", Administrator.class)
                .getResultList();
        List<String> ret = new ArrayList();
        for (Administrator a : as) {
            ret.add(a.getPerson().getEmail());

        }
        return ret;

    }

}
