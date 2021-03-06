//package controller;
//
//import static org.junit.Assert.*;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import java.io.IOException;
//import java.sql.SQLException;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import org.junit.Before;
//import org.junit.Test;
//import model.Database;
//
//public class TestShareAccount {
//	private HttpServletRequest request = mock(HttpServletRequest.class);
//	private HttpServletResponse response = mock(HttpServletResponse.class);
//	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
//	private HttpSession session = mock(HttpSession.class);
//	private AccountActivity accountActivityServlet;
//	private Database db;
//	private String clientCPR = "3112261111";
//	private String accountID = "85327386530334";
//
//	/*
//	 * Precondition: 
//	 * 	Employee: 
//	 * 		CPR: 9876543219 
//	 * 		Password: vanvan
//	 * User 3112261111 is not an owner of account 85327386530334
//	 */
//	@Before
//	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException {
//		// Login
//		String cpr = "9876543219";
//		String password = "vanvan";
//
//		Login login = new Login();
//		when(request.getSession()).thenReturn(session);
//		when(request.getParameter("cpr")).thenReturn(cpr);
//		when(request.getParameter("password")).thenReturn(password);
//		when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
//
//		login.doPost(request, response);
//		when(session.getAttribute("role")).thenReturn(login.getRole());
//		accountActivityServlet = new AccountActivity();
//
//		db = new Database(session);
//	}
//
//	@Test
//	public void testAddRemoveOwnerSuccess() throws Exception {
//		//Add
////		int ownersBefore = db.getOwners(accountID).size();
////		int accountsBefore = db.getAccounts(db.getUser(clientCPR)).size();
////		when(request.getParameter("action")).thenReturn("share");
////		when(request.getParameter("accountID")).thenReturn(accountID);
////		when(request.getParameter("newCPR")).thenReturn(clientCPR);
////		accountActivityServlet.doPost(request, response);
////		
////		assertEquals("Ownership already exists", accountActivityServlet.getMessage());
////		int ownersAfter = db.getOwners(accountID).size();
////		int accountsAfter = db.getAccounts(db.getUser(clientCPR)).size();;
////		assertEquals(1, ownersAfter-ownersBefore);
////		assertEquals(1, accountsAfter-accountsBefore);
//		
//		//Remove
//		when(request.getParameter("action")).thenReturn("deleteowner");
//		when(request.getParameter("accountID")).thenReturn(accountID);
//		when(request.getParameter("newCPR")).thenReturn(clientCPR);
//		accountActivityServlet.doPost(request, response);
//		
//		assertEquals("Owner removed", accountActivityServlet.getMessage());
//		int ownersFinal = db.getOwners(accountID).size();
//		int accountFinal = db.getAccounts(db.getUser(clientCPR)).size();;
////		assertEquals(-1, ownersAfter-ownersBefore);
////		assertEquals(-1, accountsAfter-accountsBefore);
//	}
//}
