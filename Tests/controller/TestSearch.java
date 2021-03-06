package controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;

import model.Database;
import model.User;

public class TestSearch {
	private HttpServletRequest request = mock(HttpServletRequest.class);  
	private HttpServletResponse response = mock(HttpServletResponse.class);
	private RequestDispatcher dispatcher = mock(RequestDispatcher.class);
	private HttpSession session = mock(HttpSession.class);
	private UserActivity userActivity;
	private Search searchServlet = new Search();
	private Database db;
	
	/*Precondition:
	 * Employee:
	 * 		CPR: 9876543219	
	 * 		Password: vanvan
	 * No client has name "Buzz"
	 * One user has name name "Marc Jacobs"
	 */
	@Before
	public void login() throws ServletException, IOException, ClassNotFoundException, SQLException{
		String cpr = "9876543219";
		String password = "vanvan";

		Login login = new Login();
	    when(request.getSession()).thenReturn(session);
	    when(request.getParameter("cpr")).thenReturn(cpr);
	    when(request.getParameter("password")).thenReturn(password);
	    when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

	    login.doPost(request, response);
	    when(session.getAttribute("role")).thenReturn(login.getRole());
	    userActivity = new UserActivity();
		
	    db = new Database(session);
	}
	@Test
	public void testSearchSuccess() throws Exception {
		when(request.getParameter("searchfield")).thenReturn("Buzz");	
	    searchServlet.doPost(request, response);
	    assertEquals(0, searchServlet.getResults().size());
	    
		when(request.getParameter("searchfield")).thenReturn("Marc Jacobs");
	    searchServlet.doPost(request, response);
	    assertEquals(1, searchServlet.getResults().size());
	}
	
	@Test
	public void testSearchNotLoggedIn() throws Exception {
	    when(request.getSession()).thenReturn(null);
		when(request.getParameter("searchfield")).thenReturn("Buzz");
		
	    searchServlet.doPost(request, response);
	    assertNull(searchServlet.getResults());
	}
}
