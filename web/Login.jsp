<%-- 
    Document   : Login
    Created on : Nov 7, 2023, 5:47:13 AM
    Author     : 84354
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Log In</title>
        <link rel="stylesheet" href="style.css"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <script src="script.js"></script>
    </head>
    <body>
        <div class="cotn_principal">
            <div class="cont_centrar">

                <div class="cont_login">
                    <div class="cont_info_log_sign_up">
                        <div class="col_md_login">
                            <div class="cont_ba_opcitiy">

                                <h2>LOG IN</h2>
                                <p>Welcome to my web !!!</p>
                                <button class="btn_login" onclick="change_to_login()">LOGIN</button>
                            </div>
                            <div style="color:red; margin: 20px; text-align: center; font-size: 14px">${loginError}</div>
                        </div>
                        <div class="col_md_sign_up">
                            <div class="cont_ba_opcitiy">
                                <h2>SIGN UP</h2>

                                <p>Join with me</p>

                                <button class="btn_sign_up" onclick="change_to_sign_up()">SIGN UP</button>
                            </div>
                            <div style="color:red; margin: 20px; text-align: center; font-size: 14px">
                                <c:forEach items="${errors}" var="item">
                                    <p>${item}</p>
                                </c:forEach>
                            </div>
                        </div>
                    </div>

                    <div class="cont_back_info">
                        <div class="cont_img_back_grey">
                            <img src="https://media.vogue.in/wp-content/uploads/2018/04/Your-ultimate-guide-to-Tokyo-Japan1.jpg" alt="" />
                        </div>

                    </div>
                    <div class="cont_forms">
                        <div class="cont_img_back_">
                            <img src="https://media.vogue.in/wp-content/uploads/2018/04/Your-ultimate-guide-to-Tokyo-Japan1.jpg" alt="" />
                        </div>
                        <form id="login" action="login" method="POST">
                            <div class="cont_form_login">
                                <a href="#" onclick="hidden_login_and_sign_up()"><i class="material-icons">&#xE5C4;</i></a>
                                <h2>LOGIN</h2>

                                <input type="text" name="account" placeholder="Account" />
                                <input type="password" name="password" placeholder="Password" />
                                <button class="btn_login" onclick="submit('login')">LOGIN</button>
                            </div>
                        </form>
                        <form id="signup" action="signup" method="POST"> 
                            <div class="cont_form_sign_up">
                                <a href="#" onclick="hidden_login_and_sign_up()"><i class="material-icons">&#xE5C4;</i></a>
                                <h2>SIGN UP</h2>                           
                                <input type="text" name="name" placeholder="UserName" />
                                <input type="text" name="account" placeholder="Account" />
                                <input type="password" name="password" placeholder="Password" />
                                <input type="password" name="repassword" placeholder="Confirm Password" />
                                <button class="btn_sign_up" onclick="submit('signup')">SIGN UP</button>
                            </div>
                        </form>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <script src="script.js"></script>
</body>
</html>


