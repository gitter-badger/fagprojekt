package controller;

import java.io.IOException;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.sql.Date;
import java.util.LinkedList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Database;
import model.Transaction;
import model.User;

@WebServlet("/TransactionActivity")
public class TransactionActivity extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String message ="";
    
    public TransactionActivity() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		String action = request.getParameter("action");
		String accountID = request.getParameter("accountID");
		String accountID2 = request.getParameter("accountID2");
		String transactionName = request.getParameter("transName");
		String amountString = request.getParameter("amount");
		String ISOCode = request.getParameter("ISOCode");
		String cpr = request.getParameter("ID");
		BigDecimal amount = null;
		try {
			DecimalFormatSymbols symbols = new DecimalFormatSymbols();
			symbols.setGroupingSeparator(',');
			symbols.setDecimalSeparator('.');
			String pattern = "#.##";			
			DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
			decimalFormat.setParseBigDecimal(true);
			amount = (BigDecimal) decimalFormat.parse(amountString);
	    } catch (NumberFormatException | ParseException ignore) {
	    	message = "Invalid amount";
			request.setAttribute("message", message);
			request.getRequestDispatcher("deposit.jsp").forward(request, response);
			return;
	    }
		
		try {
			Database db = new Database(request.getSession());
			
		    switch (action) {
		    	case "deposit" :
					message = db.processTransaction("Deposit", accountID, accountID2, amount, ISOCode, transactionName);					
					break;
		    	case "withdraw" :
		    		message = db.processTransaction("Withdraw", accountID, accountID2, amount, ISOCode, transactionName);
		    		break;
		    	case "transfer" :
		    		message = db.processTransaction("Transfer", accountID, accountID2, amount, ISOCode, transactionName);
		    		break;
		    }
    		redirect(response, accountID, message, cpr);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	private void redirect(HttpServletResponse response, String accountID, String message, String cpr)throws ServletException, IOException {
		response.sendRedirect("TransactionActivityRedirect?ID="+cpr+"&accountID="+accountID+"&message="+message);
	}
	public String getMessage(){
		return message;
	}
}
