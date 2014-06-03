package view;

import controller.AssistanceDBHandler;
import controller.RejectException;
import controller.UserController;
import java.io.Serializable;
import controller.AdminDBHandler;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.PersonDTO;

/**
 * <code>UserManager</code> is an session scoped bean class which handles the
 * session of a signed in user.
 *
 * @author Rahman Firouzi
 */
@ManagedBean(name = "userManager")
@SessionScoped
public class UserManager implements Serializable {

    private PersonDTO current = null;
    private String username;
    private String password;

    @EJB
    private UserController controller;

    @EJB
    private AssistanceDBHandler AssDBH;

    @EJB
    private AdminDBHandler adDBH;

    /**
     * Method gets the user full name.
     *
     * @return Returns user fullname.
     */
    public String getFullname() {
        return current.getFullname();
    }

    /**
     * Method returns the status of the user.
     *
     * @return Returns true if the user is logged in else false is returned.
     */
    public boolean isLoggedIn() {
        if (current == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method returns the userId
     *
     * @return Returns the userID
     */
    public Long getUserId() {

        if (isLoggedIn()) {

            return current.getId();
        } else {
            return null;
        }
    }

    /**
     * Method returns a boolean if the user is a super admin or not
     *
     * @return Returns true if the user is super admin, Returns false if the
     * user is not a super admin
     */
    public boolean isSuperAdmin() {
        if (current == null) {
            return false;
        } else {
            return adDBH.isSuperAdmin(current);
        }

    }

    /**
     * Method returns a boolean if the user is an Assistance or not.
     *
     * @return Returns true if the user is an assistance and Returns false if
     * the user is not an assistance
     */
    public boolean isAssistance() {
        if (current != null) {
            return AssDBH.isAssistance(current);
        } else {
            return false;
        }

    }

    /**
     * Method gets the admin status of the user.
     *
     * @return Returns the admin status of the user.
     */
    public boolean isAdmin() {

        if (current == null) {
            return false;
        }
        return adDBH.isAdmin(current);
    }

    /**
     * Method authenticates the user by its user name and password.
     *
     * @return Returns the redirect url to the index page.
     */
    public String authenticate() {
        if (username == null || password == null || username.equals("")
                || password.equals("")) {
            MessageFactory.getInstance().addErrorMessage(
                    "Please fill all the values correctly.");
        } else {
            try {
                current = controller.authenticate(username, password);

                password = null;
                if (current == null) {
                    MessageFactory.getInstance().addErrorMessage(
                            "Either username or password is wrong.");
                }
            } catch (RejectException e) {
                MessageFactory.getInstance().addErrorMessage(e.getMessage());
            }
        }
        return "index";
    }

    /**
     * Method logs out the user
     *
     * @return Returns the re-direction to the current page.
     */
    public String logout() {
        
        current = null;
        return "index";
    }

    /**
     * Method gets current user.
     *
     * @return Returns the current user.
     */
    public PersonDTO getCurrentUser() {
        return current;
    }

    /**
     * Method gets the username of the UserManager
     *
     * @return Returns username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Method sets the username of UserManager
     *
     * @param username username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Method gets the password of the UserManager
     *
     * @return Returns password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method sets the password of UserManager
     *
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
