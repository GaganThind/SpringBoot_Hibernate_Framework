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

package in.gagan.base.framework.service.user;

import org.springframework.security.core.Authentication;

/**
 * This Service interface provides the operations for Login Attempt functionality.
 * 
 * @author gaganthind
 *
 */
public interface LoginAttemptService {
	
	/**
	 * This method is used to perform some operation when user authentication fails.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	void loginFailed(Authentication auth);

	/**
	 * This method is used to perform some operation when user authentication succeeds.
	 * 
	 * @param auth - Authentication object containing user info
	 */
	void loginSucceeded(Authentication auth);

}