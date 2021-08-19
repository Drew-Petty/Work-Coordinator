<%@ tag description="base template" language="java" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Work Manager</title>
<link type="text/css" rel="stylesheet" href="/css/style.css">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="bg-secondary">
	<nav class="navbar navbar-expand-md navbar-light bg-light sticky-top border-bottom border-dark border-5">
		<div class="container-fluid">
			<h1>Hello ${currentUser.name},</h1>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarResponsive">
			<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse nav justify-content-end" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active">
						<a class="nav-link" href="/home">Home</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/user/${currentUser.id}">Profile</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/users">Users</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/crews">Crews</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="/jobs">Jobs</a>
					</li>
					<li class="nav-item">
						<form id="logoutForm" method="POST" action="/logout">
					        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					        <input class="nav-link border-0 bg-light" type="submit" value="Logout" />
					    </form>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<jsp:doBody/>
</body>
</html>