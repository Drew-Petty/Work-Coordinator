<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
	<jsp:body>
		<div class="container-fluid p-3">
			<div class="row">
				<div class="col col-12 col-lg-8 table-responsive">
					<table class="table table-dark table-striped table-bordered table-hover table-sm text-nowrap">
						<thead>
							<tr>
								<td>Name:</td>
								<td>Location:</td>
								<td>Crew Assigned:</td>
								<td>Files</td>
								<td>Start Date:</td>
								<td>End Date:</td>
								<td>Actions</td>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${jobs}" var="job">
							<tr>
								<td><a href="/job/${job.id}"><c:out value="${job.name}"/></a></td>
								<td><c:out value="${job.location}"/></td>
								<td><c:if test="${job.crew != null}"><a href="/crew/${job.crew.id}"><c:out value="${job.crew.name}"/></a></c:if></td>
								<td><c:forEach items="${job.documents}" var="document">
								<c:out value="${document.name}"/>
								</c:forEach></td>
								<td><c:out value="${job.startDate.get(2)+1}"/>/<c:out value="${job.startDate.get(5)}"/>/<c:out value="${job.startDate.get(1)}"/></td>
								<td><c:out value="${job.endDate.get(2)+1}"/>/<c:out value="${job.endDate.get(5)}"/>/<c:out value="${job.endDate.get(1)}"/></td>
								<td>
								<c:if test="${admin}">
								<form:form action="/delete/job/${job.id}" method="post">
									<input type="hidden" name="_method" value="delete">
									<input class="btn btn-danger" type="submit" value="Delete">
								</form:form>
								</c:if>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</table>
					<c:set var="name" scope="session" value="${'Jobs'}"/>
					<t:Pagination/>
				</div>
				<c:if test="${admin}">
				<div class="col col-12 col-md-6 col-lg-4 bg-light border border-dark border-5 rounded p-2">
				
					<c:forEach items="${errors}" var="error">
						<p><c:out value="${error}"/></p>
					</c:forEach>
					
					<form:form action="/addJob" modelAttribute="job" method="POST" >				
					<p>
			            <form:label path="name">Name:</form:label>
			            <form:input class="form-control" path="name"/>
			        </p>
					<p>
			            <form:label path="location">Location:</form:label>
			            <form:input class="form-control" path="location"/>
			        </p>
			        <p>
			            <form:label path="startDate">Start Date:</form:label>
			            <form:input class="form-control" type="date" path="startDate"/>
			        </p>
			        <p>
			            <form:label path="endDate">End Date:</form:label>
			            <form:input class="form-control" type="date" path="endDate"/>
			        </p>
					<input class="btn btn-primary" type="submit" value="Add Job"/>
				</form:form>
				</div>
				</c:if>				
			</div>
		</div>
	</jsp:body>
</t:base>