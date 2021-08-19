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
					<h3>Name: <c:out value="${job.name}"/></h3>
					<h3>Location: <c:out value="${job.location}"/></h3>
					<h3>Crew: <c:if test="${job.crew != null}"><a href="/crew/${job.crew.id}"><c:out value="${job.crew.name}"/></a></c:if>
					<c:if test="${job.crew == null}">Unassigned at this time.</c:if></h3>
					<h3>Start Date: <c:out value="${job.startDate.get(2)+1}"/>/<c:out value="${job.startDate.get(5)}"/>/<c:out value="${job.startDate.get(1)}"/></h3>
					<h3>End Date: <c:out value="${job.endDate.get(2)+1}"/>/<c:out value="${job.endDate.get(5)}"/>/<c:out value="${job.endDate.get(1)}"/></h3>
				</div>
			</div>
			<div class="col col-12 col-md-6 col-lg-4">
				<table class="table table-dark table-striped table-bordered table-hover">
					<thead>
						<tr>
							<th>File Name</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${job.documents}" var="document">
							<tr>
								<td><c:out value="${document.name}"/></td>
								<td class="d-flex">
									<c:if test="${admin}">
										<form:form action="/delete/${document.id}/job/${job.id}" method="post">
											<input type="hidden" name="_method" value="delete">
											<input class="btn btn-danger" type="submit" value="Delete">
										</form:form>
									</c:if>
									<form action="/document/${document.id}" method="post">
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
										<input class="ms-2 btn btn-primary" type="submit" value="Download">
									</form>
								</td>
							</tr>
						
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="col col-12 col-md-6 col-lg-4 bg-light border border-dark border-5 rounded p-2">
			<form method="POST" action="/file/job/${job.id}" enctype="multipart/form-data" >
				<p><label>Add File to <c:out value="${job.name}"/></label>
				<input class="form-control" type="file" name="file">
				</p>
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				<input class="btn btn-primary" type="submit" value="Add File">
			</form>
			<c:if test="${admin}">
			<c:forEach items="${errors}" var="error">
				<p><c:out value="${error}"/></p>
			</c:forEach>
			<form:form action="/update/job/${job.id}" method="post" modelAttribute="job">
				
				<p>
					<form:label path="name">Name:</form:label>
					<form:input class="form-control" path="name"/>
				</p>
				<p>
					<form:label path="location">Location:</form:label>
					<form:input class="form-control" path="location"/>
				</p>
				<p>
					<form:label path="startDate">Start Date</form:label>
					<form:input class="form-control" type="date" path="startDate"/>
				</p>
				<p>
					<form:label path="endDate">End Date</form:label>
					<form:input class="form-control" type="date" path="endDate"/>
				</p>
				<input class="btn btn-primary" type="submit" value="Update Job">
			</form:form>
			<form action="/assignCrew/job/${job.id}" method="POST">
			    <p>
			        <label for="crew">Assign a Crew:</label>
			        <select class="form-select" size="4" name="crew">
			            <c:forEach items="${availableCrews}" var="crew">
			                <option value="${crew.id}"><c:out value="${crew.name}"/></option>
			            </c:forEach>
			        </select>
			    </p>
			    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			    <input class="btn btn-primary" type="submit" value="Assign Job">
			</form>
			</c:if>			
			</div>
		</div>
	</jsp:body>
</t:base>