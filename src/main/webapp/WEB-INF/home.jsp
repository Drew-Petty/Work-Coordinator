<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:base>
	<jsp:body>
		<div class="container-fluid p-2 p-md-5 mt-3" >
			<div class="bg-light border border-dark rounded-pill p-3 d-flex justify-content-between">
				<a href="/previous">Previous</a>
				<h4><c:out value="${month}"/><c:out value="${calendar.get(1)}"/></h4>
				<a href="/next">Next Month</a>
			</div>
			<div class="bg-light mt-2 gridcss">
				<div class="border border-dark box">Mon</div>
				<div class="border border-dark box">Tue</div>
				<div class="border border-dark box">Wed</div>
				<div class="border border-dark box">Thur</div>
				<div class="border border-dark box">Fri</div>
				<div class="border border-dark box">Sat</div>
				<div class="border border-dark box">Sun</div>
			</div>
			<div class="bg-light gridcss">
				<c:forEach items="${layout}" var="week">
					<c:forEach items="${week.calendarList}" var="calendar">
						<div class="border ${today.equals(calendar)?'bg-secondary border-secondary text-light':'border-dark border-bottom-0'}"><c:out value="${calendar.get(5)}"/></div>
					</c:forEach>
					<c:forEach items="${week.crewLayoutList}" var="crewLayout">
						<c:forEach items="${crewLayout.jobLayoutList}" var="jobLayout">
							<c:if test="${jobLayout.job != null}">
								<a href="/job/${jobLayout.job.id}" class="border border-dark text-light text-center text-decoration-none overflow-hidden" style="grid-column:${jobLayout.gridStart}/${jobLayout.gridEnd}; background-color:${jobLayout.job.crew.color}; height:25px;">
									<c:out value="${jobLayout.job.name}"/>
								</a>
							</c:if>
							<c:if test="${jobLayout.job == null}">
								<div class="border ${today.equals(jobLayout.calendar)?'bg-secondary border-secondary border-top border-bottom':'border-dark border-top-0 border-bottom-0'}" style="grid-column:${jobLayout.gridStart}/${jobLayout.gridEnd};"></div>
							</c:if>
						</c:forEach>			
					</c:forEach>
					<c:forEach items="${week.calendarList}" var="calendar">
						<div style="height: ${Math.max(75-(25*week.crewLayoutList.size()),5)}px;" class="border ${today.equals(calendar)?'border-secondary bg-secondary':'border-dark border-top-0'}"></div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
	</jsp:body>
</t:base>