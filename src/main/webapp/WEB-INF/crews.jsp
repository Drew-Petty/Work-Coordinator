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
					<th>Lead</th>
					<th>Size</th>
					<th>Actions</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${crews}" var="crew">
				<tr>
					<td><a href="/crew/${crew.id}"><c:out value="${crew.name}"/></a></td>
					<td><c:out value="${crew.lead.name}"/></td>
					<td><c:out value="${crew.users.size()}"></c:out></td>
					<td>
						<c:if test="${admin}">
						<form:form action="/delete/crew/${crew.id}" method="post">
							<input type="hidden" name="_method" value="delete">
							<input class="btn btn-danger" type="submit" value="Delete">
						</form:form>
						</c:if>						
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<c:set var="name" scope="session" value="${Crews}"/>
		<t:Pagination/>
		</div>
		<c:if test="${admin}">
		<div class="col col-12 col-sm-6 col-lg-4">
			<form class="bg-secondary p-3" action="/addCrew" method="POST">
				<p>
					<label for="name">Name:</label>
					<input class="form-control" type="text" name="name">
				</p>
				<div class="row">
					<label class="col col-6" for="color">Color:</label>
					<div class="col col-6">
					<input class="form-control" type="color" name="color">
					</div>
				</div>
				<p>
					<label for="members">Members unassigned to a crew:</label>
					<select class="form-select" size="4" multiple="multiple" name="members">
						<c:forEach items="${crewless}" var="user">
							<option value="${user.id}"><c:out value="${user.name}"/></option>
						</c:forEach>
					</select>
				</p>
			
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input class="btn btn-primary" type="submit" value="Create Crew">
			</form>
		</div>
		</c:if>
		
		</div>
		</div>
	</jsp:body>
</t:base>