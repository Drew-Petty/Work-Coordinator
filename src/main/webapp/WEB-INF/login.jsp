<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Login</title>
<link type="text/css" rel="stylesheet" href="/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet" >
</head>
<body>
	<div class="container-fluid p-4">
		<div class="row justify-content-center mt-5">
			<div class="col col-12 col-sm-6 col-lg-4 col bg-light d-flex flex-column p-3 border border-dark rounded">
				<a href="/oauth2/authorization/google" class="btn btn-danger btn-outline-danger text-light fw-bolder">Continue with Google</a>
				<c:if test="${logoutMessage!=null}">
			        <h3 class="mt-3"><c:out value="${logoutMessage}"/></h3>
			    </c:if>
			    <c:if test="${errorMessage != null}">
			        <h3 class="mt-3"><c:out value="${errorMessage}"/></h3>
			    </c:if>
			    <form class="mt-3" method="POST" action="/login">
		            <label class="form-label" for="email">Email:</label>
		            <input class="form-control" type="text" id="email" name="email"/>
		            <label class="form-label mt-2" for="password">Password</label>
		            <input class="form-control" type="password" id="password" name="password"/>
			        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			        <div class="d-flex justify-content-around mt-3">
			        <input class="btn btn-primary col col-4" type="submit" value="Login"/>
			        <a href="/registration" class="btn btn-primary col col-4">Register</a>
			        </div>
			    </form>
			</div>
		</div>
	</div>
</body>
</html>