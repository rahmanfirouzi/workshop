package view;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;
import controller.AdminDBHandler;
import controller.ApplicationController;
import controller.Event;

import controller.RejectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.servlet.http.HttpServletRequest;

/**
 * <code>AdminViewManager</code> is a class which handles the administration
 * Calendar.
 *
 * @author Rahman Firouzi
 */
public class AdminViewManager extends DHXEventsManager {

    ApplicationController applicationController = lookupApplicationControllerBean();
    AdminDBHandler adminDBHandler = lookupAdminDBHandlerBean();

    String email;

    /**
     * Constructor for the class with in parameter of the type
     * HttpServletRequest
     *
     * @param request the new request
     */
    public AdminViewManager(HttpServletRequest request) {

        super(request);

        UserManager manager = (UserManager) request.getSession().getAttribute("userManager");
        email = manager.getUsername();

    }

    /**
     * Method returns all the events that should be shown on the calendar. which
     * are all the workshop events, all the student suggested workshop events
     * and all the assistance work time events.
     *
     * @return an arrayList of Events to be shown on the calendar
     */
    @Override
    public Iterable getEvents() {

        ArrayList<Event> evs = new ArrayList();

        evs.addAll(adminDBHandler.getAllEvents());
        evs.addAll(applicationController.getAllSuggestedWorkTime());
        evs.addAll(applicationController.getSuggestedWorkshop());
        return evs;

    }

    /**
     * Method will change or save the event depending on what the status is.
     *
     * @param event the event that should be save
     * @param status the action (UPDATE, DELETE, INSERT) that the method should
     * do with the event
     *
     * @return returns the status back
     */
    @Override
    public DHXStatus saveEvent(DHXEv event, DHXStatus status) {

        if (status == DHXStatus.UPDATE) {
            String start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getStart_date());
            String end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEnd_date());
            Long id = event.getId().longValue();
            try {
                adminDBHandler.updateEvent(start_date, end_date, event.getText(), id);
            } catch (RejectException ex) {
                Logger.getLogger(AdminViewManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (status == DHXStatus.INSERT) {
            String start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getStart_date());
            String end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEnd_date());
            int id = adminDBHandler.insertEvent(start_date, end_date, event.getText(), email);
            event.setId(id);
        } else if (status == DHXStatus.DELETE) {
            
                adminDBHandler.deleteEvent(event.getId());
           
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
