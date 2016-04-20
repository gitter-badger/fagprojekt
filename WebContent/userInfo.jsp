<!DOCTYPE HTML><%@page language="java"
	contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<html>
    <head>
        <title>EditCostumer</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
    </head>
    <body>
    	<%@include file="header.jsp"%>
        <div class="container" align="center">
            <h2>Edit Customer Information</h2>
            <form action="Activity" method="get" class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="control-label col-sm-2" for="email">Email:</label>
                    <div class="col-sm-4">
                    <%String email = (String) request.getAttribute("email");%>
                        <input type="email" class="form-control" id="email" placeholder="Enter email" value="<%=email%>">
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="pwd">Password:</label>
                    <div class="col-sm-4">          
                        <input type="password" class="form-control" id="pwd" placeholder="Enter password" value=${password}>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="fullname">Full Name:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="name" placeholder="Enter full name" value=${fullname}>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="address">Address:</label>
                    <div class="col-sm-4">          
                        <input type="text" class="form-control" id="address" placeholder="Enter address" value=${address}>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-2" for="address">Zipcode:</label>
                    <div class="col-sm-2">          
                        <input type="number" class="form-control" id="zipcode" placeholder="Enter zipcode" value=${postcode}>
                    </div>
                </div>    
                <div class="form-group">    
                    <label class="control-label col-sm-2">City:</label>
                    <div class="col-sm-4">
                        <input type="text" class="form-control" id="city" placeholder="Enter city">
                    </div>
                </div>
                
                <div>        
                    <div class="col-sm-offset-2 col-sm-8">
                      <button type="submit" class="btn btn-default">Save Changes</button>
                    </div>
                </div>
            </form>
        </div>
        <br>
        <br>
        <br>
        <div>
        	<%@include file="footer.jsp"%>
        </div>
    </body>
</html>