package view;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

import controller.UserController;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * <code>RegistrationManager</code> is an request scoped class which handles the
 * registration of a user.
 *
 * @author Rahman Firouzi
 */
@ManagedBean(name = "registration")
@RequestScoped
public class RegistrationManager {

    private Exception exception;
    private String username;
    private String password;
    private String confirmpassword;
    private String firstname;
    private String lastname;

    @EJB
    private UserController controller;

    /**
     * Method registers the user to the workshop
     *
     * @return Returns the redirection url to the current page.
     */
    public String register() {
        String smsg = "Registration was successfull!  Please use login form for signing in";
        if(!isValidEmailAddress(username)){
            MessageFactory.getInstance().addErrorMessage("Please make sure that"
                    + " you use the KTH email adress.");
            return "";
        }
        
        try {
            controller.register(username, password, firstname, lastname);
            MessageFactory.getInstance().addInfoMessage(smsg);
        } catch (Exception e) {
            MessageFactory.getInstance().addErrorMessage(e.getMessage());
        }

        return "";
    }

    /**
     * returns if the Email adress look like a email adress.
     * @param email the email adress that should be tested
     * @return  returns false if the email adress is not valid, returns true if the email adress is valid.
     */
   private boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
       
   return result;
}
    
    
    
    
    /**
     * Method gets the exception of the RegistrationManager
     *
     * @return Returns exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Method gets the username of the RegistrationManager
     *
     * @return Returns username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method sets the username of RegistrationManager
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method gets the password of the RegistrationManager
     *
     * @return Returns password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method sets the password of RegistrationManager
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method gets the confirmpassword of the RegistrationManager
     *
     * @return Returns confirmpassword
     */
    public String getConfirmpassword() {
        return confirmpassword;
    }

    /**
     * Method sets the confirmpassword of RegistrationManager
     *
     * @param confirmpassword confirmpassword
     */
    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    /**
     * Method gets the firstname of the RegistrationManager
     *
     * @return Returns firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Method sets the firstname of RegistrationManager
     *
     * @param firstname firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Method gets the lastname of the RegistrationManager
     *
     * @return Returns lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Method sets the lastname of RegistrationManager
     *
     * @param lastname lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
