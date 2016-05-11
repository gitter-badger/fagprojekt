<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Account" %>
<html>
<div class="container">
	<%@include file="clientheader.jsp"%>
	<div class="main">
		<div class="pagetitle">Transfer money</div>
		<hr width="95%" noshade>
			<form class="form-inline" action="Transactions?action=transfer" method="post" target="_self">
				<label class="control-label col-sm-5">Transaction name:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="transName" placeholder="Enter transaction name"/></div>
				<br><br>
				<label class="control-label col-sm-5">Sending Account:</label>
				
				<div class="col-sm-5"><select class="form-control" name="accountID">
				<%ArrayList<Account> accounts = (ArrayList<Account>) request.getAttribute("accounts");
				if(!accounts.isEmpty()){%>
					<option value="<%=accounts.get(0).getAccountID()%>" selected><%=accounts.get(0).getAccountID()%></option>
					<%for (int i = 1; i< accounts.size(); i++){%>
						<option value="<%=accounts.get(i).getAccountID()%>"><%=accounts.get(i).getAccountID()%></option>
				<%	}
				}%>
  				</select>
  				</div>
  				
				<br><br>
				<label class="control-label col-sm-5">Receiving Account:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="accountID2" placeholder="Enter account ID"/></div>
				<br><br>
				<label class="control-label col-sm-5">Amount:</label>
				<div class="col-sm-5"><input type="text" class="form-control" name="amount" placeholder="Enter amount"/>
				<select class="form-control" name="currency">
    				<option value="DKK" selected>DKK</option>
    				<option value="USD">USD</option>
    				<option value="EUR">EUR</option> 
    				<option value="SEK">SEK</option>
    				<option value="NOK">NOK</option>  
    				<option value="GBP">GBP</option>
    				<option value="AED">AED</option>
    				<option value="AUD">AUD</option>
    				<option value="BGN">BGN</option>
    				<option value="CAD">CAD</option>
    				<option value="CHF">CHF</option>
    				<option value="CZK">CZK</option> 
    				<option value="EGP">EGP</option>
    				<option value="HKD">HKD</option>
    				<option value="HRK">HRK</option>
    				<option value="HUF">HUF</option>
    				<option value="ILS">ILS</option>
    				<option value="JPY">JPY</option>    				
    				<option value="NZD">NCD</option>
    				<option value="PLN">PLN</option>
    				<option value="RON">RON</option>
    				<option value="RUB">RUB</option>
    				<option value="SAR">SAR</option>
    				<option value="SGD">SGD</option>
    				<option value="THB">THB</option>
    				<option value="TRY">TRY</option>
    				<option value="ZAR">ZAR</option>
  				</select>
  				</div>
				<div class="col-sm-offset-5 col-sm-5"><font size="2"><font color="red">${message}</font></font><br>
				<input type="submit" class="btn btn-default" name="transferButton" value="Transfer"></div>
			</form>		
	</div>
</div>
<%@include file="footer.jsp"%>
</html>