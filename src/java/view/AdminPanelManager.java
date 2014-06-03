/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AdminDBHandler;
import controller.ApplicationController;
import controller.RejectException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import model.Ability;

/**
 * <code>AdminViewManager</code> is a Session scoped class which handles the
 * request from adminpanel.xhtml Calendar.
 *
 * @author Rahman Firouzi
 */
@ManagedBean()
@Named("adminPanelManager")
@SessionScoped
public class AdminPanelManager implements Serializable {

    @EJB
    private AdminDBHandler controller;

    @EJB
    private ApplicationController cont;

    private List<Ability> abilities;
    private List<Ability> choosedAbilities;
    private List<String> assistances;
    private String newAbility;
    private String newAssistanceEmail;
    private List<String> choosedAssistancesEmailes;
    private String newAdminEmail;
    private List<String> choosedAdminEmailes;
    private List<String> admins;

    private ArrayList AbilityToremove;

    private boolean summeryEventMethod = false;

    /**
     * method adds new admin. the email adress of the new admin is given in the newAdminEmail.
     *  @return Returns the redirection url to the current page.
     */
    public String addNewAdmin() {
        if (newAdminEmail == null) {
            MessageFactory.getInstance().addErrorMessage("Please fill in the form.");
            return "";
        }
        try {
            controller.addnewAdmin(newAdminEmail);
            newAdminEmail = null;
        } catch (RejectException e) {
            MessageFactory.getInstance().addErrorMessage(e.getMessage());
        }
        return "";
    }
/**
 * Method removes all the admin which is given in choosedAdminEmailes.
  @return Returns the redirection url to the current page.
 */
    public String removeAdmins() {
        controller.removeAdmin(choosedAdminEmailes);
        
        return "";
    }
/**
 * method changes the 
 */
    public void changeSummeryEventMethod() {
        summeryEventMethod = cont.setStudentSummeryMethod(!summeryEventMethod);
    }
/**
 * method returns all the admins but the supperadmin. 
 * @return all the admins. 
 */
    public List<String> getAdmins() {
        List<String> adms = controller.getEmailAdmins();
        adms.remove("superadmin@kth.se");
        return adms;
    }
/**
 * Method adds new ability into the database
 @return Returns the redirection url to the current page.
 */
    public String addNewAbility() {

        if (newAbility == null) {
            MessageFactory.getInstance().addErrorMessage("Please fill in the form.");
        }
        try {
            controller.addNewAbility(newAbility);
            newAbility = null;
        } catch (RejectException e) {
            MessageFactory.getInstance().addErrorMessage(e.getMessage());
        }
        return "";
    }
/**
 * Method returns all the assistance email address
 * @return a list of all assistance email address.
 */
    public List<String> getAssistances() {

        assistances = controller.getEmailAssistances();

        return assistances;

    }
/**
 * Method adds a new ASSISTANCE into the database. 
 @return Returns the redirection url to the current page.
 */
    public String addNewAssistance() {
        if (newAssistanceEmail == null) {
            MessageFactory.getInstance().addErrorMessage("Please fill in the form.");
        }
        try {
            controller.addnewAssistance(newAssistanceEmail);
            newAssistanceEmail = null;
        } catch (RejectException e) {
            MessageFactory.getInstance().addErrorMessage(e.getMessage());
        }
        return "";
    }
/**
 * Method removes all the assistance given in choosedAssistancesEmailes list from the database.
 @return Returns the redirection url to the current page.
 */
    public String removeAssistance() {

        controller.removeAssistance(choosedAssistancesEmailes);

        return "";
    }
/**
 * method returns all the abilities 
 * @return a list of all the abilities
 */
    public List<Ability> getAbilities() {
        return cont.getAllAbilities();
    }
/**
 * method removes ability give in AbilityToremove.
 @return Returns the redirection url to the current page. 
 */
    public String removeAbilities() {
        controller.removeAbilities(AbilityToremove);
        return "";
    }

///////////////////// Setter and getters ////////////////////////////////
    
    
    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public List<Ability> getChoosedAbilities() {
        return choosedAbilities;
    }

    public String setChoosedAbilities(List<Ability> choosedAbilities) {
        this.choosedAbilities = choosedAbilities;
        return "";
    }

    public boolean isSummeryEventMethod() {
        return summeryEventMethod;
    }

    public ArrayList getAbilityToremove() {
        return AbilityToremove;
    }

    public void setAbilityToremove(ArrayList AbilityToremove) {
        this.AbilityToremove = AbilityToremove;
    }

    public String getNewAbility() {
        return newAbility;
    }

    public void setNewAbility(String newAbility) {
        this.newAbility = newAbility;
    }

    public String getNewAssistanceEmail() {
        return newAssistanceEmail;
    }

    public void setNewAssistanceEmail(String newAssistanceEmail) {
        this.newAssistanceEmail = newAssistanceEmail;
    }

    public List<String> getChoosedAssistancesEmailes() {
        return choosedAssistancesEmailes;
    }

    public void setChoosedAssistancesEmailes(List<String> choosedAssistancesEmailes) {
        this.choosedAssistancesEmailes = choosedAssistancesEmailes;
    }

    public String getNewAdminEmail() {
        return newAdminEmail;
    }

    public void setNewAdminEmail(String newAdminEmail) {
        this.newAdminEmail = newAdminEmail;
    }

    public List<String> getChoosedAdminEmailes() {
        return choosedAdminEmailes;
    }

    public void setChoosedAdminEmailes(List<String> choosedAdminEmailes) {
        this.choosedAdminEmailes = choosedAdminEmailes;
    }

}
