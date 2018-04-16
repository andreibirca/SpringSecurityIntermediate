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
        <form:form method="POST" action="/registration" modelAttribute="user">
            <div class="imgcontainer">
                <img src="../resources/incognito.jpg" alt="Avatar" class="avatar">
                <h2 align="center" style="color:maroon;">${msg}</h2></br>
            </div>
            <%--<h3 align="center" style="color:maroon;">${msg}</h3>--%>


            <div class="container" align="left">

                <label>Name</label>
                <input type="text" name="name" required="required"/>

                <label>Surname</label>
                <form:input path="surname" />
                <%--<input type="text" name="surname" required="required"/>--%>

                <%--<label>Date of Birth</label>--%>
                <%--<input type="text" name="date" value="yyyy-mm-dd">--%>

                <form:label path="date">Date of Birth</form:label><br>
                <form:errors path="date" cssClass="error"/>
                <form:input path="date" type="date"/>

                <label>Username</label><br>
                <p align="center" class="error">${msgExist}</p>
                <form:input path="username" required="required"/>

                <%--<input type="password" name="password" required="required"/>--%>
                <label>Password</label><br>
                <form:errors path="password" cssClass="error"/>
                <form:input path="password" required = "required"/>


                <label>Password Confrimation</label>
                <p align="left" class="error">${pswnotequal}</p>
                <input type="text" name="passwConfirm" placeholder ="some text" required="required"/>

                <label>Email</label><br>
                <form:errors path="email" cssClass="error"/>
                <form:input path="email" required="required"/>

                <label>Gender</label>
                <p>
                <input type="radio" name="gender" value="MALE" checked>Male
                <input type="radio" name="gender" value="FEMALE">Female<br></p>
                <button type="submit">Register</button>
                <button type="submit"><a href="/login" style="color: ghostwhite">TO Login</a></button>

            </div>
        </form:form>
    </div>
</div>
</body>
</html>
