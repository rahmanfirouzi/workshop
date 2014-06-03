package view;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * <code>MessageFactory</code> is an singleton class which adds error
 * messages into facescontext
 * 
 * @author Rahman Firouzi
 */
public class MessageFactory {

	private static MessageFactory factory = new MessageFactory();

	/**
	 * Method gets the instance of the factory.
	 * 
	 * @return Returns the instance of the factory.
	 */
	public static MessageFactory getInstance() {
		return factory;
	}

	/**
	 * Method adds the error message into the faces context for h:messages
	 * display
	 * 
	 * @param message
	 *            - The message itself.
	 */
	public void addErrorMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, message));
	}

	/**
	 * Method adds success message into the faces context.
	 * 
	 * @param message
	 *            - The message itself.
	 */
	public void addInfoMessage(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, null, message));
	}
}
