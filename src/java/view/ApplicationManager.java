/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ApplicationController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.AssistanceDTO;
import model.Person;
import model.PersonDTO;

/**
 * <code>ApplicationManager</code> is an session scoped class in which handles the
 * user.
 * @author Rahman Firouzi
 */
@ManagedBean(name = "appManager")
@SessionScoped
public class ApplicationManager implements Serializable{

    @EJB
    private ApplicationController controller;

    /**
     * method returns all the assistances in database
     * @return returns a DTO of all the assistance in database.
     */
    public List<? extends AssistanceDTO> getAssistances() {
        List<? extends AssistanceDTO> assistances = controller.getAssistances();
                
        List<PersonDTO> persons = new ArrayList();
                
            for (int j = 0; j < assistances.size(); j++) {
                PersonDTO p;
                p =assistances.get(j).getPerson();
            persons.add(p);
        }     
        return assistances;
    }

}
