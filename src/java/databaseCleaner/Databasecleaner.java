package databaseCleaner;

import com.dhtmlx.planner.DHXEventsManager;
import controller.Event;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.*;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import model.SuggestedWorkTime;

public class Databasecleaner implements ServletContextListener {
Cleaner thread;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        
/*
        while (true) {

            thread = new Cleaner();
            thread.start();

            try {
                //sleep for 1 day
               // Thread.sleep(24 * 60 * 60 * 1000);
                Thread.sleep( 1000);
            } catch (InterruptedException ex) {
                thread.interrupt();

            }

        }
*/
        
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
   
    thread.interrupt();
    
    }

    public class Cleaner extends Thread {

        @PersistenceContext(unitName = "workshopPU")
        private EntityManager em;

      
        
        @Override
        public void run() {

            //here I will clean the database!
            /*
            SimpleDateFormat frmt = new SimpleDateFormat("yyyy.MM.dd HH:mm");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            cal.add(Calendar.YEAR, -1);
            cal.add(Calendar.DAY_OF_MONTH, -1);

            List<SuggestedWorkTime> swt = em.createNamedQuery("SuggestedWorkTime.getAll", SuggestedWorkTime.class).
                    getResultList();

            SuggestedWorkTime s;

            for (int i = swt.size(); i > 0; i--) {

                String startDateString = swt.get(i).getEndDate();

                Date endDate = null;

                try {
                    endDate = frmt.parse(startDateString);
                } catch (ParseException ex) {
                }
                String newDateString = frmt.format(endDate);

            }

            System.err.println(frmt.format(cal.getTime()));
*/
            
            
            System.out.println("Im here to clean the database !");
        }

    }

}
