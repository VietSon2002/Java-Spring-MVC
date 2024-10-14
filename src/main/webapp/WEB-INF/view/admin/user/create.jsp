<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create User</title>
    <!-- Latest compiled and minified CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Latest compiled JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

    <link rel="stylesheet" href="/css/demo.css">
</head>

<body>
<div class="container mt-5">
    <div class="row">
        <div class="col-md-6 col-12 mx-auto">
            <h3>Create a User</h3>
            <hr />
            <form:form method="post" action="/admin/user/create" modelAttribute="newUser">
                <div class="mb-3">
                    <label class="form-label">Email address:</label>
                    <form:input type="email" class="form-control" path="email" />
                    <form:errors cssClass="text-danger mt-3" path="email" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Password:</label>
                    <form:password class="form-control" path="password" />
                    <form:errors cssClass="text-danger mt-3" path="password" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Full name:</label>
                    <form:input type="text" class="form-control" path="fullName" />
                    <form:errors cssClass="text-danger mt-3" path="fullName" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Address:</label>
                    <form:input type="text" class="form-control" path="address" />
                    <form:errors cssClass="text-danger mt-3" path="address" />
                </div>

                <div class="mb-3">
                    <label class="form-label">Phone number:</label>
                    <form:input type="text" class="form-control" path="phone" />
                    <form:errors cssClass="text-danger mt-3" path="phone" />
                </div>

                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="exampleCheck1">
                    <label class="form-check-label" for="exampleCheck1">Check me out</label>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form:form>
        </div>
    </div>
</div>
</body>

</html>
