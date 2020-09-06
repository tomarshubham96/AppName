<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en"
xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="utf-8">
      <title>Forgot Password</title>

      <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
      <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
  </head>

  <body>

    <div class="container">

        <form th:if="!${invalidToken}" class="m-t" id="passwordForm" role="form" action="#"
       th:object="${setPassword}" th:action="@{/resetPassword}" method="POST" modelAttribute="userForm">
       
       <h2 class="form-signin-heading">Reset Password</h2>

        <div class="form-group input-group">
          <span class="input-group-addon">
            <span class="glyphicon glyphicon-lock"></span>
          </span>          
          <input name="password" type="password" id="password"  
            placeholder="Password" class="form-control" required />
          <span class="glyphicon"
            aria-hidden="true"></span>
        </div>

        <div class="form-group input-group has-feedback">
          <span class="input-group-addon">
            <span class="glyphicon glyphicon-lock"></span>
          </span>
         <input type="password" class="form-control" id="signup-password-confirm" placeholder="Confirm Password" name="ConfirmPassword" data-fv-notempty="true"
                                       data-fv-notempty-message="Please confirm password"
                                       data-fv-identical="true"
                                       data-fv-identical-field="password"
                                       data-fv-identical-message="Both passwords must be identical" />
                   
          <div class="help-block with-errors"></div>
        </div>


		
        <button type="submit"
          class="btn btn-primary block full-width m-b">Save
        </button>

      </form>

    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  </body>
</html>