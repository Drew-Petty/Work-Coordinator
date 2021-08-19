<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:base>
	<jsp:body>
		<div class="container-fluid p-3">
		<div class="row">
		<div class="col col-12 col-lg-8 table-responsive">
		<table class="table table-dark table-striped table-bordered table-hover table-sm text-nowrap">
			<thead>
			<tr>
				<th>Name</th>
				<th>Email</th>
				<th>Crew</th>
				<th>Documents</th>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td><a href="/user/${user.id}"><c:out value="${user.name}"/></a></td>
					<td><c:out value="${user.email}"/></td>
					<td><c:out value="${user.crew.name}"/></td>
					<td>
						<c:forEach items="${user.documents}" var="document">
							<c:out value="${document.name}"/>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="name" scope="session" value="${'Users'}"/>
		<t:Pagination/>
		</div>
		<c:if test="${admin}">
		<div class="col col-12 col-sm-6 col-lg-4">
		<form:form class="bg-secondary p-3" method="POST" action="/addUser" modelAttribute="user">

			<p class="fw-bolder"><c:out value="${error}"/></p>
			
	        <p>
	            <form:label path="name">Name:</form:label>
	            <form:input class="form-control" path="name"/>
	        </p>
	        <p>
	            <form:label path="email">Email:</form:label>
	            <form:input class="form-control" path="email"/>
	        </p>
	        <p>
	            <form:label path="password">Password:</form:label>
	            <form:password class="form-control" path="password"/>
	        </p>
	        <p>
	            <form:label path="passwordConfirmation">Password Confirmation:</form:label>
	            <form:password class="form-control" path="passwordConfirmation"/>
	        </p>
	        <input class="btn btn-primary" type="submit" value="Add User"/>
	    </form:form>
	    
		</div>
		</c:if>
		</div>
		</div>
	</jsp:body>
</t:base>