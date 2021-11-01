package in.gagan.base.framework.filter;

import static in.gagan.base.framework.constant.JWTSecurityConstants.HEADER_STRING;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.gagan.base.framework.component.JWTProps;
import in.gagan.base.framework.constant.ApplicationConstants;
import in.gagan.base.framework.dto.UsernamePasswordAuthDTO;
import in.gagan.base.framework.service.JWTService;

/**
 * This filter class is used to provide authentication of credentials and creation of JWT token.
 * 
 * @author gaganthind
 *
 */
public class JWTUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
	
	private final AuthenticationManager authenticationManager;
	private final JWTProps jwtProps;
	private final JWTService jwtSvc;
	
	@Autowired
	public JWTUsernamePasswordAuthFilter(AuthenticationManager authenticationManager, JWTProps jwtProps, JWTService jwtSvc) {
		this.authenticationManager = authenticationManager;
		this.jwtProps = jwtProps;
		this.jwtSvc = jwtSvc;
	}
	
	/**
	 * This method captures the username and password details and perform the authentication of the user.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @throws AuthenticationException - If the user is not authenticated
	 * 
	 */
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		UsernamePasswordAuthDTO usernamePasswordAuth = getUsernamePasswordAuthDTO(request);
		
		String username = usernamePasswordAuth.getUsername();
		String password = usernamePasswordAuth.getPassword();
		
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authenticateUser = this.authenticationManager.authenticate(auth);
		
		return authenticateUser;
		
	}

	/**
	 * This method is called on the successful authentication of the user credentials and then generates a unique JWT token.
	 * 
	 * @param request - contains request parameters
	 * @param response - contains response parameters
	 * @param chain - filter chain to execute other filters
	 * @param authResult - Authentication object containing user details
	 * @exception IOException - IO exception
	 * @exception ServletException - Servlet exception
	 *  
	 */
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		String ipAddress = StringUtils.defaultIfBlank(request.getHeader("X-FORWARDED-FOR"), request.getRemoteAddr());
		String userAgent = StringUtils.defaultIfBlank(request.getHeader("User-Agent"), ApplicationConstants.BLANK);
		
		if (StringUtils.isBlank(ipAddress)) {
			throw new ServletException("Bad Request!! IP address not present");
		}
		
		if (ApplicationConstants.BLANK.equals(userAgent)) {
			throw new ServletException("Bad Request!! User-Agent not provided");
		}
		
		String token = this.jwtSvc.buildToken(authResult, ipAddress, userAgent);
		String tokenPrefix = this.jwtProps.getTokenPrefix();
		String headerString = new StringBuilder(tokenPrefix).append(token).toString();
		
		response.addHeader(HEADER_STRING, headerString);
		
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try (PrintWriter writer = response.getWriter()) {	
            writer.write("{\"" + HEADER_STRING + "\":\"" + headerString + "\", \"ExpiresIn\":\"" + this.jwtSvc.getExpirationDate(token).getTime() + "\"}");
			writer.flush();
        } catch (IOException ie) {
            // Nothing
        }
        
	}

	/**
	 * This method gets the username and password form request object and sets in UsernamePasswordAuthDTO. 
	 * 
	 * @param request - contains request parameters
	 * @return UsernamePasswordAuthDTO - Username and Password in DTO object
	 */
	private UsernamePasswordAuthDTO getUsernamePasswordAuthDTO(HttpServletRequest request) {
		UsernamePasswordAuthDTO usernamePasswordAuth = null;
		try {
			usernamePasswordAuth = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthDTO.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return usernamePasswordAuth;
	}
	
}
