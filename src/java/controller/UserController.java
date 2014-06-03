package controller;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Person;
import model.PersonDTO;

/**
 * <code>UserController</code> handles the view requests which contains
 * registration and login.
 *
 * @author Rahman Firouzi
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class UserController {

    @PersistenceContext(unitName = "workshopPU")
    private EntityManager em;

    /**
     * Method registers the user to the database.
     *
     * @param email
     * @param password - Password of the user
     * @param firstname - First name of the user
     * @param lastname - Last name of the user
     * @throws RejectException An exception is thrown if the user name is taken.
     */
    public void register(String email, String password, String firstname,
            String lastname)
            throws RejectException {

        List<Person> persons = em.createNamedQuery("Person.findByEmail",
                Person.class).setParameter("email", email).getResultList();
        if (!persons.isEmpty()) {
            throw new RejectException("Username is already taken.");
        }

        // Populate user object
        Person per = populateUserObject(email, password, firstname, lastname);

        em.persist(per);
    }

    /**
     * Method authenticates the user by matching it´s user name and password
     * against the users available in the database.
     *
     * @param username - The user name of the user
     * @param password - The password of the user
     * @return Returns either null or an user data transfer object which
     * contains information about the user.
     * @throws RejectException An exception is thrown if the user is not found
     * or the user is banned.
     */
    public PersonDTO authenticate(String username, String password)
            throws RejectException {

        String error = "Either username or password is wrong";
        List<Person> persons = em.createNamedQuery("Person.findByEmail", Person.class).setParameter("email", username).getResultList();

        if (persons.isEmpty()) {
            throw new RejectException(error);
        }
        try {
            password = getEncryptedPassword(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RejectException(
                    "Server fatal error: MD5 algorithm not found");
        } catch (UnsupportedEncodingException e) {
            throw new RejectException(
                    "Server fatal error: UTF-8 encoding is not supported on server!");
        }
        if (persons.get(0).getPassword().equals(password)) {
            return persons.get(0);
        }
        return null;
    }
/**
 * Method decrypt the password
 * @param password  the string to decrypt
 * @return  returns the decrypted string
 * @throws NoSuchAlgorithmException 
 * @throws UnsupportedEncodingException 
 */
    private String getEncryptedPassword(String password)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update(password.getBytes(), 0, password.length());
        String md5 = new BigInteger(1, digest.digest()).toString(16);

        return md5;
    }
/**
 * Method make a person object out of the given data
 * @param email the email address of the person
 * @param password  the password of the Person
 * @param firstname the Firstname of the Person
 * @param lastname  the lastname of the Person
 * @return
 * @throws RejectException 
 */
    private Person populateUserObject(String email, String password,
            String firstname, String lastname) throws RejectException {
        Person per;
        per = new Person();
        per.setEmail(email);
        try {
            per.setPassword(getEncryptedPassword(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RejectException(
                    "Server fatal error: MD5 algorithm not found");
        } catch (UnsupportedEncodingException e) {
            throw new RejectException(
                    "Server fatal error: UTF-8 encoding is not supported on server!");
        }
        per.setFirstName(firstname);
        per.setLastName(lastname);
        return per;
    }

}
