package servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.sql.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.Database;
import model.Transaction;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/General")
public class General extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public General() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String accountID2 = request.getParameter("accountID2");
		String amountString = request.getParameter("amount");
		String currency = request.getParameter("currency");
		if (accountID.isEmpty() || amountString.isEmpty() || (action.equals("transfer") && accountID2.isEmpty())) {
			String message = "Please fill in all fields";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
		}
		BigDecimal amount = null;
		try {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#.##";			
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			amount  = (BigDecimal) decimalFormat.parse(amountString);
	    } catch (NumberFormatException | ParseException ignore) {
	    	String message = "Please type in a valid amount";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
	    }
		
		try {
			Database db = new Database();
			java.util.Date utilDate = new java.util.Date();
		    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			
		    if(action.equals("deposit")){
				db.processTransaction("Deposit", accountID, amount, currency);
				String message = "Deposit completed";
				request.setAttribute("message", message);
				request.getRequestDispatcher("deposit.jsp").forward(request, response);
				
		    } else if (action.equals("withdraw")){
		    	//db.processTransaction("Withdraw", accountID, amount);
		    } else if (action.equals("transfer")){
		    	//db.processTransaction("Transfer", accountID, accountID2, amount);
		    }
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}