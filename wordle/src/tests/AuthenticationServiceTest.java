package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.AuthenticationService;
import model.UserAccount;

/**
 * This class contains units tests for authentication services.
 * @author umimu
 *
 */
public class AuthenticationServiceTest {

	private AuthenticationService authService;
	private UserAccount existingUser;

	@Before
	public void setUp() throws Exception {
		authService = new AuthenticationService();
		existingUser = new UserAccount("lola", "lola");
	}

	@After
	public void tearDown() throws Exception {
	
	}

	@Test
	public void testLogInWithCorrectCredentials() {
		int result = authService.logIn(existingUser.getUserName(), existingUser.getPassword());
		assertEquals(3, result);
		assertNotNull(authService.getCurrentUser());
		assertEquals(existingUser.getUserName(), authService.getCurrentUser().getUserName());
	}

	@Test
	public void testLogInWithIncorrectPassword() {
		int result = authService.logIn(existingUser.getUserName(), "wrongpassword");
		assertEquals(2, result);
		assertNull(authService.getCurrentUser());
	}

	@Test
	public void testLogInWithNonExistingUser() {
		int result = authService.logIn("bobaboba", "password");
		assertEquals(1, result);
		assertNull(authService.getCurrentUser());
	}

	@Test
	public void testLogInWhileAlreadyLoggedIn() {
		authService.logIn(existingUser.getUserName(), existingUser.getPassword());
		int result = authService.logIn("newuser", "password");
		assertEquals(0, result);
		assertNotNull(authService.getCurrentUser());
		assertEquals(existingUser.getUserName(), authService.getCurrentUser().getUserName());
	}

	@Test
	public void testLogInWithEmptyUsernameOrPassword() {
		int result = authService.logIn("", "");
		assertEquals(4, result);
		assertNull(authService.getCurrentUser());
	}

	@Test
	public void testLogOut() {
		authService.logIn(existingUser.getUserName(), existingUser.getPassword());
		int result = authService.logOut();
		assertEquals(1, result);
		assertNull(authService.getCurrentUser());
	}

	@Test
	public void testLogOutWhenNotLoggedIn() {
		int result = authService.logOut();
		assertEquals(0, result);
		assertNull(authService.getCurrentUser());
	}


	@Test
	public void testCreateAccountWithExistingUsername() {
		int result = authService.createAccount(existingUser.getUserName(), "password");
		assertEquals(0, result);
		assertNull(authService.getCurrentUser());
	}

	@Test
	public void testCreateAccountWithEmptyUsernameOrPassword() {
		int result = authService.createAccount("", "");
		assertEquals(2, result);
		assertNull(authService.getCurrentUser());
	}
}
