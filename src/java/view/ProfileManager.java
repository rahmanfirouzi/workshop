
package view;

import controller.ApplicationController;
import controller.AssistanceDBHandler;
import controller.RejectException;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import model.Ability;
import model.AssistanceDTO;
import model.PersonDTO;

/**
 * <code>ProfileManager</code> is an request scoped class in which handles the
 * the Profile.xhtml pages
 *
 * @author Rahman Firouzi
 */
@ManagedBean(name = "profileManager")
@RequestScoped
public class ProfileManager {
 
    private String name;    
  
    private String email;
   
    private PersonDTO profilePerson;
    private List<Ability> allAbilities;
    private List<Ability> assistanceAbilities;
    private List<Ability> abilitiyToremove;
    private List<Ability> abilitiesToAdd;
    private boolean editable = false;
    private Map<String, String> params;
    private long userId = -10;
    private long profileId = -15;

    @EJB
    AssistanceDBHandler assdbh = new AssistanceDBHandler();

    @EJB
    ApplicationController appcont = new ApplicationController();

    @ManagedProperty(value = "#{userManager}")
    UserManager usermanager;

    /**
     * method will every time the class is created. and update the user info.
     */
    @PostConstruct
    public void getUserId() {

        params = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap();

        if (params.size() > 0 && params != null) {

            profileId = Long.parseLong(params.get("profileId"));

            AssistanceDTO assis = null;
            try {
                assis = appcont.getAssistance(profileId);
            } catch (RejectException e) {
                MessageFactory.getInstance().addErrorMessage(
                        "Error occur while getting assistance.");
            }
            if (assis != null) {
                name = assis.getPerson().getFirstName();
                email = assis.getPerson().getEmail();
                profilePerson = assis.getPerson();
                assistanceAbilities = assis.getAbilities();

                if (usermanager.isAssistance()) {
                    userId = appcont.getAssistance((PersonDTO) usermanager.getCurrentUser()).getId();
                    if (userId == profileId) {
                        editable = true;
                    } else {
                        editable = false;
                    }
                }
            }
        }
    }

    /**
     * Method removes the abilities in which is given in the abilitytoremove.
     *
     * @return Returns the redirection url to the current page.
     */
    public String removeAbilities() {
        try {
            assdbh.removeAbilities(abilitiyToremove, userId);

        } catch (Exception e) {
            MessageFactory.getInstance().addErrorMessage("can not presist ");
        }
        return "";
    }

    /**
     * Method adds new ability into the assistance profile. the ability is given
     * in the abilitiesToAdd.
     *
     * @return Returns the redirection url to the current page.
     */
    public String addAbilities() {

        try {
            assdbh.addAbilities(abilitiesToAdd, userId);

        } catch (Exception e) {
            MessageFactory.getInstance().addErrorMessage("Database Exception.");
        }

        return "";
    }

    /**
     * Method returns the name of the assistance
     *
     * @return returns the name of the assistance
     */
    public String getName() {
        return name;
    }

    /**
     * Method will set the name of the assistance
     *
     * @param name the new name for the assistance
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method returns the email address of the  assistance.
     * @return returns the email address of the assistance
     */
    public String getEmail() {
        return email;
    }
/**
 *  Method sets a new email address to the assistance
 * @param email the new email address to be updated
 */
    public void setEmail(String email) {
        this.email = email;
    }
/**
 * Method returns all the ability that the assistance have.
 * @return  returns all the ability that a assistance have
 */
    public List<Ability> getAllAbilities() {

        allAbilities = appcont.getAllAbilities();
        allAbilities.removeAll(assistanceAbilities);

        return allAbilities;

    }
/**
 * Method returns the id of the assistance in the profile
 * @return returns a long value of the assistance in the profile
 */
    public long getProfileId() {
        return profileId;
    }
/**
 * method sets the profileid to the given value.  
 * @param profileId the profile id 
 */
    public void setProfileId(long profileId) {
        this.profileId = profileId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setAllAbilities(List<Ability> allAbilities) {

        this.allAbilities = allAbilities;

    }

    public List<Ability> getAssistanceAbilities() {
        assistanceAbilities = assdbh.getAssistanceAbilities(email);
        return assistanceAbilities;
    }

    public void setAssistanceAbilities(List<Ability> assistanceAbilities) {
        this.assistanceAbilities = assistanceAbilities;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public List<Ability> getAbilitiyToremove() {
        return abilitiyToremove;
    }

    public void setAbilitiyToremove(List<Ability> abilitiyToremove) {
        this.abilitiyToremove = abilitiyToremove;
    }

    public List<Ability> getAbilitiyToAdd() {
        return abilitiesToAdd;
    }

    public void setAbilitiyToAdd(List<Ability> abilitiesToAdd) {
        this.abilitiesToAdd = abilitiesToAdd;
    }

    public List<Ability> getAbilitiesToAdd() {
        return abilitiesToAdd;
    }

    public void setAbilitiesToAdd(List<Ability> abilitiesToAdd) {
        this.abilitiesToAdd = abilitiesToAdd;
    }

    public UserManager getUsermanager() {
        return usermanager;
    }

    public void setUsermanager(UserManager usermanager) {
        this.usermanager = usermanager;
    }

    public PersonDTO getProfilePerson() {
        return profilePerson;
    }

    public void setProfilePerson(PersonDTO profilePerson) {
        this.profilePerson = profilePerson;
    }

}
