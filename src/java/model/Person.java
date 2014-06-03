
package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * <code>Person</code> Entity class for Person
 * 
 *  @author Rahman Firouzi
 */
@SuppressWarnings("serial")
@NamedQueries({
    @NamedQuery(name = "Person.findByEmail",
            query = "SELECT p FROM Person p WHERE p.email LIKE :email")
})

@Entity
@Table(name = "PERSON")
public class Person implements PersonDTO {

    private static final long serialVersionUID = 16247164401L;

    @Column(name = "FIRSTNAME")
    private String firstName;

    @Column(name = "LASTNAME")
    private String lastName;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public Person() {
    }

    public Person(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassorwd() {
        return password;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
	public String getFullname() {
		return new StringBuilder(getFirstLetterUppercase(firstName))
				.append(" ").append(getFirstLetterUppercase(lastName))
				.toString();
	}

	private String getFirstLetterUppercase(String word) {
		return new String(
				new StringBuilder(String.valueOf(word.charAt(0))).append(word
						.substring(1)));
	}
  
}
