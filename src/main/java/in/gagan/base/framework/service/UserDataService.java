package in.gagan.base.framework.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.gagan.base.framework.component.PasswordSettings;
import in.gagan.base.framework.dao.UserDAO;
import in.gagan.base.framework.entity.User;
import in.gagan.base.framework.util.ExceptionHelperUtil;

@Transactional
@Service
public class UserDataService {
	
	private final UserDAO userDAO;
	
	private final PasswordEncoder passwordEncoder;
	
	private final PasswordSettings passwordSettings;
	
	@Autowired
	public UserDataService(UserDAO userDAO, PasswordEncoder passwordEncoder, PasswordSettings passwordSettings) {
		this.userDAO = userDAO;
		this.passwordEncoder = passwordEncoder;
		this.passwordSettings = passwordSettings;
	}
	
	public User fetchUserByEmail(String email) throws UsernameNotFoundException {
		User user = this.userDAO.findUserByEmail(email)
								.orElseThrow(() -> ExceptionHelperUtil.throwUserNotFound(email));
		return user;
	}
	
	public void saveUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if (null == user.getPasswordExpireDate()) {
			user.setPasswordExpireDate(LocalDateTime.now().plusDays(this.passwordSettings.getPasswordExpireDays()));
		}
		this.userDAO.save(user);
	}
	
	public void updateUser(User user) {
		this.userDAO.save(user);
	}
	
	public void deleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.delete(user);
	}
	
	public void hardDeleteUser(String email) {
		User user = fetchUserByEmail(email);
		this.userDAO.hardDelete(user);
	}
	
	public boolean isUserPresent(String email) {
		return this.userDAO.findUserByEmail(email).isPresent();
	}
	
}