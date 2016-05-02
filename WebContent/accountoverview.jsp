<!DOCTYPE HTML><%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="model.Transaction"%>
<%@ page import="java.math.BigDecimal" %>
<html>
<div class="container">
	<%@include file="employeeheader.jsp"%>
	<div class="main"><div class="pagetitle">Activity for ${accountID}</div>
		<hr width="95%" noshade>
		<div align="center">
			<div class="content">
			<p style="text-align:left;">
				<font size="5"><font color="#01405b">Balance
				<span style="float:right;"><%=request.getAttribute("balance")%> DKK</span></font></font>
				<br><br>
				<a href="deposit.jsp?accountID=${accountID}">Deposit Money</a>
				<span style="float:right;">
				<a href="Activity?ID=${cpr}&action=closeaccount&accountID=${accountID}">Close Account</a>
				</span>
				<br><a href="withdraw.jsp?accountID=${accountID}">Withdraw Money</a>
				<span style="float:right;">
				<a href="Activity?ID=${cpr}&action=editaccount&accountID=${accountID}">Edit Account</a>
				</span>
				<br><a href="transfer.jsp?accountID=${accountID}">Transfer Money</a>
			</p>
		</div>
		
			<table>
			    <col width="20%">
		  		<col width="40%">
		  		<col width="20%">
		  		<col width="20%">
				<tr>
					<th>Date</th>
					<th>Transaction name</th>
					<th>Amount</th>
					<th>Balance</th>
				</tr>
				<%
				String color;
				LinkedList<Transaction> trans = (LinkedList<Transaction>) request.getAttribute("transactions");
				for (int i = trans.size()-1 ; i >= 0; i--) {
					if (trans.get(i).getAmount().compareTo(BigDecimal.ZERO) > 0){
						color = "green";
					} else {
						color = "red";
					}%>
				<tr>
					<td> <%= trans.get(i).getDate() %></td>
					<td> <%= trans.get(i).getName() %></td>
					<td> <font color="<%=color%>"><b><%= trans.get(i).getAmount() %></b></font></td>
					<td> <b><%=trans.get(i).getBalance() %></b> </td>
				</tr>
				<%}%>
			</table>
		</div>
	</div>
</div>
<%@include file="footer.jsp"%>
</html>