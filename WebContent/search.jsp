<!DOCTYPE html>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="model.User" %>
<html>
<div class="container">
	<%@include file="sessioncheck.jsp" %>
	<div class="main">
		<form id=searchbar action="Search" method="post" target="_self">
			<input id=searchbutton type="submit" value="Search"	style="float: right" />
			<div style="overflow: hidden">
				<input id="searchfield" type="text" name="searchfield"
					placeholder="Search by name, cpr or phone"
					style="width: 100%" />
			</div>
		</form>
		<div class="pagetitle"><font size="4"><font color="grey">${message}</font></font></div><br>
            <div align="center">
            <table class="clickable">
            	<col width="25%">
		  		<col width="30%">
		  		<col width="30%">
		  		<col width="15%">
            	<tr>
                	<th>CPR</th>
                	<th>Name</th>
                	<th>Address</th>
               		<th>Phone</th>
           		</tr>
           		<% 
           		ArrayList<User> users = (ArrayList<User>) request.getAttribute("resultlist");
				if(users!= null){
					for (User user : users) {%>
           			<tr onclick="document.location = 'UserActivity?ID=<%=user.getCPR()%>&action=viewuser';">
               		<td> <%= user.getCPR() %></td>
               		<td> <%= user.getName() %></td>
               		<td> <%= user.getAddress() %></td>
               		<td> <%= user.getPhone() %></td>
           		<tr/>
        		<% } 
        	}%>
       		</table></div>
		</div>
	</div>
	<%@include file="footer.jsp"%>
</html>

