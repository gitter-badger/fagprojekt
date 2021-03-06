package redirect;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Database;

@WebServlet("/TransactionActivityRedirect")
public class TransactionActivityRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TransactionActivityRedirect() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Database db = new Database(request.getSession());
			String accountID = request.getParameter("accountID");
			String message = request.getParameter("message");
			String cpr = request.getParameter("ID");
			switch (message) {
		    	case "Deposit Invalid Account" :
					request.setAttribute("errormessage", "Account does not exist");
					request.getRequestDispatcher("deposit.jsp").forward(request, response);
					break;
		    	case "Withdraw Invalid Account" :
		    		request.setAttribute("errormessage", "Account does not exist");
					request.getRequestDispatcher("withdraw.jsp").forward(request, response);
					break;
		    	case "Withdraw Insufficient Balance" :
		    		request.setAttribute("errormessage", "Insufficient Balance");
		    		request.getRequestDispatcher("withdraw.jsp").forward(request,  response);
		    		break;
		    	case "Transfer Insufficient Balance" : 
		    		request.setAttribute("errormessage", "Insufficient Balance");
		    		request.getRequestDispatcher("transfer.jsp").forward(request,  response);
		    		break;
		    	case "Transfer Invalid Account" :
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.setAttribute("errormessage", "Receiving account does not exist");
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.setAttribute("errormessage", "One of the accounts does not exist");
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	case "Same account failure" :
		    		request.setAttribute("errormessage", "Sending and receiving account is the same");
		    		if (request.getSession().getAttribute("role").equals("c")){
		    			request.getRequestDispatcher("ctransfer.jsp").forward(request, response);
		    		} else {
		    			request.getRequestDispatcher("transfer.jsp").forward(request, response);
		    		}
		    		break;
		    	default :
		    		request.setAttribute("message", message);
					request.setAttribute("cpr", cpr);
					request.setAttribute("toast", true);
					request.setAttribute("accountID", accountID);
					request.setAttribute("transactions", db.getTransactions(accountID));
					request.setAttribute("balance", db.getTransactions(accountID).getLast().getBalanceString());
					request.setAttribute("accountName", db.getAccount(accountID).getName());
					request.setAttribute("ISOCode", db.getAccount(accountID).getISOCode());
					request.getRequestDispatcher("accountoverview.jsp").forward(request, response);
					break;
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
