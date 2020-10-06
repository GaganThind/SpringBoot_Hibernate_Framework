package in.gagan.base.framework.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.util.UserHelperUtil;

/**
 * Entity representing the User table 
 * 
 * @author gaganthind
 *
 */
@Entity
@Table(name="USERS")
public class User extends AbstractBaseEntity {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -2144175799559542686L;
	
	public User() { super(); }
	
	public User(String firstName, String lastName, String email, LocalDate dob, char gender) {
		super(ApplicationConstants.CHAR_Y);
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
		this.gender = gender;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_ID", nullable = false, unique = true, length = 10)
	private long userId;
	
	@Column(name = "FIRST_NAME", nullable = false, length = 20)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false, length = 20)
	private String lastName;
	
	@Column(name = "EMAIL", nullable = false, unique = true, length = 50)
	private String email;
	
	@Column(name = "DOB", nullable = true)
	private LocalDate dob;
	
	@Column(name = "GENDER", nullable = true)
	private char gender;
	
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
	
	public int getAge() {
		return UserHelperUtil.calculateAge(getDob());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (userId ^ (userId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		} else if(obj == null) {
			return false;
		} else if(getClass() != obj.getClass()) {
			return false;
		}
		
		User other = (User) obj;
		
		return userId == other.userId;
	}
}
