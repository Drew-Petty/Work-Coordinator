<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:base>
	<jsp:body>
		<div class="container-fluid p-3 mt-3 row">
		
			<div class="col col-12 col-md-6 col-lg-4 bg-light border border-dark border-5 rounded mb-3">
				<h3>Name: <c:out value="${user.name}"/></h3>
				<h4 class="text-break">Email: <c:out value="${user.email}"/></h4>
				<h3>Crew: <c:if test="${user.crew != null}"><a href="/crew/${user.crew.id}"><c:out value="${user.crew.name}"/></a></c:if>
				<c:if test="${user.crew ==null }">unassigned at this time.</c:if></h3>
			</div>
			<div class="col col-12 col-md-6 col-lg-4">
				<table class="table table-dark table-striped table-bordered table-hover">
					<thead>
					<tr>
						<th>File Name</th>
							<c:if test="${admin||profile}">
						<th>Actions</th>
							</c:if>
					</tr>
					</thead>
					<tbody>
					<c:forEach items="${user.documents}" var="document">
						<tr>
							<td><c:out value="${document.name}"/></td>
							<c:if test="${admin||profile}">
							<td class="d-flex">
							<form:form action="/delete/${document.id}/user/${user.id}" method="post" >
							<input type="hidden" name="_method" value="delete">
							<input class="btn btn-danger" type="submit" value="Delete">
							</form:form>
							<form action="/document/${document.id}" method="post">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<input class="ms-2 btn btn-primary" type="submit" value="Download">
							</form>
							</td>
							</c:if>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</div>
			<c:if test="${admin||profile}">
			<form method="POST" action="/file/user/${user.id}" enctype="multipart/form-data" class="col col-12 col-md-6 col-lg-4 bg-light border border-dark border-5 rounded p-2">
				<p><label>Add File to <c:out value="${user.name}"/></label>
				<input class="form-control" type="file" name="file">
				</p>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input class="btn btn-primary" type="submit" value="Add File">
			</form>
			</c:if>
		</div>
	</jsp:body>
</t:base>
