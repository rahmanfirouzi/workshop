package view;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;
import controller.AdminDBHandler;
import controller.ApplicationController;
import controller.Event;
import controller.StudentDBHandler;

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
 * <code>StudentViewManager</code> is a class which handles the Student
 * Calendar.
 *
 * @author Rahman Firouzi
 */
public class StudentViewManager extends DHXEventsManager {

    ApplicationController appcont = lookupApplicationControllerBean();

    AdminDBHandler adminDBHandler = lookupAdminDBHandlerBean();

    StudentDBHandler studentDBHandler = lookupStudentDBHandler();

    private long userId;

    String email;

    /**
     * constructor for the class with in parameter of the type
     * HttpServletRequest
     *
     * @param request the new request
     */
    public StudentViewManager(HttpServletRequest request) {
        super(request);
        UserManager manager = (UserManager) request.getSession().getAttribute("userManager");
        email = manager.getUsername();
        userId = manager.getUserId();
    }

    /**
     * Method returns all the events that should be shown on the calendar. the
     * events are workshop events and students own sujested events.
     *
     * @return an arrayList of Events to be shown on the calendar
     */
    @Override
    public Iterable getEvents() {

        ArrayList<Event> evs = new ArrayList();
        try {
            evs.addAll(studentDBHandler.getAllStudentEvents(email));

        } catch (RejectException ex) {
            System.out.println(ex.getMessage());
        }
        ArrayList<Event> evs2 = appcont.getAllWorkshopEvents();

        for (Event e : evs2) {
            e.setReadonly(true);
        }

        evs.addAll(evs2);

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
            studentDBHandler.updateEvent(start_date, end_date, event.getText(), id);
        } else if (status == DHXStatus.INSERT) {
            String start_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getStart_date());
            String end_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(event.getEnd_date());
            int id;
            try {
                id = studentDBHandler.insertEvent(start_date, end_date, event.getText(), email);
                event.setId(id);
            } catch (RejectException ex) {
                Logger.getLogger(StudentViewManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else if (status == DHXStatus.DELETE) {
            try {
                studentDBHandler.deleteEvent(event.getId());
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

    private StudentDBHandler lookupStudentDBHandler() {
        try {
            Context c = new InitialContext();
            return (StudentDBHandler) c.lookup("java:global/workshop/StudentDBHandler!controller.StudentDBHandler");
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
