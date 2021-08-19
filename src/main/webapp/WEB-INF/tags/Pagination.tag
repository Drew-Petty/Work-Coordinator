<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${totalItems>0}">
	<div class="row">
		<div class="col col-10 col-sm-8 col-md-6 m-3 bg-secondary d-flex flex-column align-items-center border border-dark border-5 rounded">
			<h4>Showing <c:out value="${name}"/> <c:out value="${startCount}"/> to <c:out value="${endCount}"/> of <c:out value="${totalItems}"></c:out>.</h4>
			<nav>
				<ul class="pagination">
				
					<li class="${currentPage == 1?'page-item disabled':'page-item'}"><a href="/users/page/1" class="page-link bg-light">First</a>
					<li class="${currentPage == 1?'page-item disabled':'page-item'}"><a href="/users/page/${currentPage-1}" class="page-link bg-light">Previous</a>
					<li class="${currentPage == totalPages?'page-item disabled':'page-item'}"><a href="/users/page/${currentPage+1}" class="page-link bg-light">Next</a>
					<li class="${currentPage == totalPages?'page-item disabled':'page-item'}"><a href="/users/page/${totalPages}" class="page-link bg-light">Last</a>
			
				</ul>
			</nav>
		</div>		
	</div>
</c:if>