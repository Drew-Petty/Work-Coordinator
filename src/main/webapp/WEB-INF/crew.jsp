<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:base>
	<jsp:body>
		<div class="container-fluid p-3 mt-3 row">
		<div class="col col-12 col-md-6 col-lg-4">
		<div class="row bg-light border border-dark border-5 rounded mb-3">
			<h3>Name: <c:out value="${crew.name}"/></h3>
			<h3>Lead: <c:if test="${crew.lead != null}"><a href="/user/${crew.lead.id}"><c:out value="${crew.lead.name}"/></a></c:if>
			<c:if test="${crew.lead == null}">unassigned at this time.</c:if></h3>
			<h3>Size: <c:out value="${crew.users.size()}"/></h3>
			<h3 style="color: ${crew.color}">Color: <c:out value="${crew.color}"/></h3>
		</div>
		</div>
		<div class="col col-12 col-md-6 col-lg-4">
			<table class="table table-dark table-striped table-bordered table-hover">
				<thead>
				<tr>
				<th>Member Name</th>
				<th>Documents</th>
				<th>Actions</th>
				</tr>
				</thead>
				<tbody>
					<c:forEach items="${crew.users}" var="user">
						<tr>
							<td><a href="/user/${user.id}"><c:out value="${user.name}"/></a></td>
							<td>
								<c:forEach items="${user.documents}" var="document">
									<c:out value="${document.name}"/>
								</c:forEach>
							</td>
							<td>
							<c:if test="${admin}">
							<form action="/remove/${user.id}/crew/${crew.id}" method="post">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input class="btn btn-danger" type="submit" value="Remove">
							</form>
							</c:if>							
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<c:if test="${admin}">
		<form action="/update/crew/${crew.id}" method="post" class="col col-12 col-md-6 col-lg-4 bg-light border border-dark border-5 rounded p-2">
			<h3>Update <c:out value="${crew.name}"/></h3>
			<p>
				<label for="name">Name:</label>
				<input class="form-control" type="text" name="name" value="${crew.name}">
			</p>
			<div class="row">
				<label class="col col-6" for="color">Color:</label>
				<div class="col col-6">
				<input class="form-control" type="color" name="color" value="${crew.color}">
				</div>
			</div>
			<p>
				<label for="members">add members unassigned to a crew:</label>
				<select class="form-select" size="4" multiple="multiple" name="members" >
					<option value="0" selected="selected"></option>
					<c:forEach items="${crewless}" var="user">
						<option value="${user.id}"><c:out value="${user.name}"/></option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label for="lead">Assign a Crew Lead:</label>
				<select class="form-select" size="4" name="lead"  >
					<c:forEach items="${crew.users}" var="user">
						<option value="${user.id}"><c:out value="${user.name}"/></option>
					</c:forEach>
				</select>
			</p>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input class="btn btn-primary" type="submit" value="Update Crew">
		</form>
		</c:if>
		</div>
	</jsp:body>
</t:base>