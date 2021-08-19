<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Registration</title>
<link type="text/css" rel="stylesheet" href="/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
	<div class="container-fluid p-4">
		<div class="row justify-content-center mt-5">
			<div class="col col-12 col-sm-6 col-lg-4 col bg-light d-flex flex-column p-3 border border-dark rounded">
				<h2>Registration</h2>
				<p><form:errors path="user.*"/></p>
			    <form:form method="POST" action="/registration" modelAttribute="user">
		            <form:label class="form-label mt-2" id="label" path="name">Name:</form:label>
		            <form:input class="form-control" path="name"/>
		            <form:label class="form-label mt-2" path="email">Email:</form:label>
		            <form:input class="form-control" path="email"/>
		            <form:label class="form-label mt-2" path="password">Password:</form:label>
		            <form:password class="form-control" path="password"/>
		            <form:label class="form-label mt-2" path="passwordConfirmation">Password Confirmation:</form:label>
		            <form:password class="form-control" path="passwordConfirmation"/>
			        <div class="d-flex justify-content-around mt-3">
			        <input class="btn btn-primary col col-4" type="submit" value="Register"/>
					<a href="/login" class="btn btn-primary col col-4">Login</a>
			        </div>
			    </form:form>
			</div>
		</div>
	</div>
</body>
</html>