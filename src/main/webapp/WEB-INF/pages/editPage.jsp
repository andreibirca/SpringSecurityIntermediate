<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <style type="text/css">
        <%@include file="../resources/style.css"%>
    </style>
</head>

<body>
<div align="center">
    <div style="width: 300px; height: 500px;">
        <form:form method="POST" action="/updateSimpleUser" modelAttribute="user">
            <div class="imgcontainer">
                <%--<img src="../resources/incognito.jpg" alt="Avatar" class="avatar">--%>
                <%--<h2 align="center" style="color:maroon;">${msg}</h2></br>--%>
                <h2 align="center" style="color:maroon;">Personal Edit Page</h2></br>
            </div>

            <div class="container" align="left">

                <label>Name</label>
                <%--<input type="text" name="name" required="required"/>--%>
                <form:input path="name" required="required"/>

                <label>Surname</label>
                <form:input path="surname" required="required"/>

                <form:label path="date">Date of Birth</form:label><br>
                <form:errors path="date" cssClass="error"/>
                <form:input path="date" type="date"/>

                <label>Username</label><br>
                <p align="center" class="error">${msgExist}</p>
                <form:errors path="username" cssClass="error"/>
                <form:input path="username" required="required"/>

                <%--<input type="password" name="password" required="required"/>--%>
                <label>Password</label><br>
                <form:errors path="password" cssClass="error"/>
                <form:input path="password" type="text" required = "required"/>


                <label>Password Confrimation</label>
                <span class="error">${pswnotequal}</span>
                <form:input path="passwConfirm" type="text" placeholder="Confirm the password"/>

                <label>Email</label><br>
                <form:errors path="email" cssClass="error"/>
                <form:input path="email" required="required"/>

                <label>Gender</label>
                <p>
                   <form:radiobutton path="gender" value="MALE" checked="checked"/>Male
                   <form:radiobutton path="gender" value="FEMALE" />Female<br>
                </p>
                <button type="submit">Update</button>

            </div>
        </form:form>
    </div>
</div>
</body>
</html>
