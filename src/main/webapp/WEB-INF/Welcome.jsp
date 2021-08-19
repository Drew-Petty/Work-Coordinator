<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:base>
	<jsp:body>
	<div>
		<h1>Register!</h1>
    
	    <p><form:errors path="user.*"/></p>
	    
	    <form:form method="POST" action="/registration" modelAttribute="user">
	        <p>
	            <form:label id="label" path="name">Name:</form:label>
	            <form:input path="name"/>
	        </p>
	        <p>
	            <form:label path="email">Email:</form:label>
	            <form:input path="email"/>
	        </p>
	        <p>
	            <form:label path="password">Password:</form:label>
	            <form:password path="password"/>
	        </p>
	        <p>
	            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
	            <form:password path="passwordConfirmation"/>
	        </p>
	        <input type="submit" value="Register!"/>
	    </form:form>
	</div>
	<a href="/oauth2/authorization/google" class="btn btn-lg btn-secondary btn-outline-dark">Continue with Google</a>
	
	<div>
	<c:if test="${logoutMessage!=null}">
        <h3><c:out value="${logoutMessage}"/></h3>
    </c:if>
    
    <c:if test="${errorMessage != null}">
        <h3><c:out value="${errorMessage}"/></h3>
    </c:if>
		<h1>Login</h1>
	    <form method="POST" action="/login">
	        <p>
	            <label for="email">Email</label>
	            <input type="text" id="email" name="email"/>
	        </p>
	        <p>
	            <label for="password">Password</label>
	            <input type="password" id="password" name="password"/>
	        </p>
	        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	        <input type="submit" value="Login!"/>
	    </form>
	</div>
	</jsp:body>
</t:base>