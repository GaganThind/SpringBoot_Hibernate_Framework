/*
 * SpringBoot_Hibernate_Framework
 * 
 * Copyright (C) 2020-2022  Gagan Thind

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package in.gagan.base.framework.dao.user;

import java.util.List;
import java.util.Optional;

import javax.persistence.Query;

import in.gagan.base.framework.dao.base.AbstractBaseDAO;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import in.gagan.base.framework.entity.user.User;

/**
 * This class provides CRUD operations on the USERS table using DAO pattern.
 * 
 * @author gaganthind
 *
 */
@Repository
public class UserDAOImpl extends AbstractBaseDAO<User, Long> implements UserDAO {
	
	private static final String WHERE_EMAIL_CLAUSE = " where email = :email ";
	private static final String WHERE_EMAIL_IN_CLAUSE = " where email in (:emails) ";
	private static final String SET_ACTIVE_N_CLAUSE = " set activeSw = 'N' ";
	private static final String SET_ACCOUNT_LOCKED_CLAUSE = " set accountLocked = 'N' ";
	private static final String EMAIL = "email";
	private static final String EMAILS = "emails";
	
	/**
	 * Method used to fetch a user by email.
	 * 
	 * @param email - email of user
	 * @return Optional<User> - User object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Optional<User> findUserByEmail(String email) {
		String selectQuery = new StringBuilder(LITERAL_FROM).append(getTableName()).append(WHERE_EMAIL_CLAUSE).toString();
		Query query = entityManager.createQuery(selectQuery);
		query.setParameter(EMAIL, email);
		List<User> users = (List<User>) query.getResultList();
		return !CollectionUtils.isEmpty(users) ? Optional.of(users.get(0)) : Optional.empty();
	}
	
	/**
	 * Method used to unlock account of multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	@Override
	public void unlockUsers(List<String> emails) {
		String softDeleteQuery = new StringBuilder(LITERAL_UPDATE).append(getTableName()).append(SET_ACCOUNT_LOCKED_CLAUSE)
				.append(WHERE_EMAIL_IN_CLAUSE).toString();
		Query query = entityManager.createQuery(softDeleteQuery);
		query.setParameter(EMAILS, emails);
		query.executeUpdate();
	}

	/**
	 * Method used to soft delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	@Override
	public void deleteUsers(List<String> emails) {
		String softDeleteQuery = new StringBuilder(LITERAL_UPDATE).append(getTableName()).append(SET_ACTIVE_N_CLAUSE)
				.append(WHERE_EMAIL_IN_CLAUSE).toString();
		Query query = entityManager.createQuery(softDeleteQuery);
		query.setParameter(EMAILS, emails);
		query.executeUpdate();
	}

	/**
	 * Method used to hard delete multiple users by email(s).
	 * 
	 * @param emails - email of user(s)
	 */
	@Override
	public void hardDeleteUsers(List<String> emails) {
		String deleteQuery = new StringBuilder(LITERAL_DELETE).append(getTableName()).append(WHERE_EMAIL_IN_CLAUSE).toString();
		Query query = entityManager.createQuery(deleteQuery);
		query.setParameter(EMAILS, emails);
		query.executeUpdate();
	}
	
}