package view;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;
import controller.*;

import controller.RejectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import model.AssistanceDTO;

/**
 * <code>AssistanceViewManager</code> is class which handles the assistance
 * calendar.
 *
 *
 * @author Rahman Firouzi
 */
public class AssistanceViewManager extends DHXEventsManager {

    ApplicationController applicationController = lookupApplicationControllerBean();
    AdminDBHandler adminDBHandler = lookupAdminDBHandlerBean();
    AssistanceDBHandler assistanceDBHandler = lookupAssistanceDBHandlerBean();

    String email;

    private AssistanceDTO ass;
    private String namn;
    private List<String> abilities;

    /**
     * Constructor that take a request and create the assistance calendar AND it
     * altso gets the username of the person who is online from the sessions of
     * the request.
     *
     * @param request the request which is send to the page.
     */
    public AssistanceViewManager(HttpServletRequest request) {
        super(request);
        UserManager manager = (UserManager) request.getSession().getAttribute("userManager");
        email = manager.getUsername();

    }

    /**
     * Method returns all the events that can be shown for a assistance. which
     * are all suggestedworkshopevent by students , suggestedworktime by
     * assistance and all the workshopevents.
     *
     * @return an arraylist of the events that should be shown
     */
    @Override
    public Iterable getEvents() {

        ArrayList<Event> evs = new ArrayList();

        evs.addAll(applicationController.getAllWorkshopEvents());
        evs.addAll(applicationController.getSuggestedWorkshop());
        evs.addAll(applicationController.getAllSuggestedWorkTime());
        evs.addAll(assistanceDBHandler.getSuggestedWorkTimeEventByAssistance(email));

        return evs;
    }

    /**
     * Method update/insert/delete a event
     *
     * @param event the event that should be update/insert/delete
     * @param status the action that should be done.
     * @return returns the status back
     */
    @Override
    public DHXStatus saveEvent(DHXEv event, DHXStatus status) {

        if (status == DHXStatus.UPDATE) {
            String start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getStart_date());
            String end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEnd_date());

            Long id = event.getId().longValue();

            try {
                assistanceDBHandler.updateEvent(start_date, end_date, event.getText(), id, email);
            } catch (RejectException ex) {
                Logger.getLogger(StudentViewManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (status == DHXStatus.INSERT) {
            String start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getStart_date());
            String end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEnd_date());

            int id;
            try {
                id = assistanceDBHandler.insertEvent(start_date, end_date, event.getText(), email);
                event.setId(id);
            } catch (RejectException ex) {
                Logger.getLogger(AssistanceViewManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (status == DHXStatus.DELETE) {
            try {
                assistanceDBHandler.deleteEvent(event.getId());
            } catch (RejectException ex) {
                Logger.getLogger(StudentViewManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return status;
    }

    /**
     * method is called by the DHTMLX java planner when new event is creates.
     *
     * @param id the ID of the event
     * @param status the status of the Event
     * @return return a event
     */
    @Override
    public DHXEv createEvent(String id, DHXStatus status) {
        return new DHXEvent();
    }

    private AssistanceDBHandler lookupAssistanceDBHandlerBean() {
        try {
            Context c = new InitialContext();
            return (AssistanceDBHandler) c.lookup("java:global/workshop/AssistanceDBHandler!controller.AssistanceDBHandler");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AdminDBHandler lookupAdminDBHandlerBean() {
        try {
            Context c = new InitialContext();
            return (AdminDBHandler) c.lookup("java:global/workshop/AdminDBHandler!controller.AdminDBHandler");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ApplicationController lookupApplicationControllerBean() {
        try {
            Context c = new InitialContext();
            return (ApplicationController) c.lookup("java:global/workshop/ApplicationController!controller.ApplicationController");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
