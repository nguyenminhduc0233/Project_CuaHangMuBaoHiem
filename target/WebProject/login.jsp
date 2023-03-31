<%@ page import="vn.edu.hcmuaf.fit.controller.DoRegister" %><%--
Created by IntelliJ IDEA.
User: ACER
Date: 11/6/2022
Time: 9:13 PM
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <script async defer crossorigin="anonymous"
            src="https://connect.facebook.net/vi_VN/sdk.js#xfbml=1&version=v16.0&appId=1378561832905145&autoLogAppEvents=1"
            nonce="zxQjrN1B"></script>
    <meta charset="utf-8">
    <title>HelmetsShop</title>
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="Free HTML Templates" name="keywords">
    <meta content="Free HTML Templates" name="description">
    <!-- Favicon -->
    <link href="img/favicon.ico" rel="icon">

    <!-- Google Web Fonts -->
    <link rel="preconnect" href="https://fonts.gstatic.com">
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap" rel="stylesheet">

    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">

    <!-- Libraries Stylesheet -->
    <link href="lib/animate/animate.min.css" rel="stylesheet">
    <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

    <!-- Customized Bootstrap Stylesheet -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="css/login.css">
    <script src="https://accounts.google.com/gsi/client" async defer></script>

</head>

<body>
<!-- Header Start -->
<%@include file="header.jsp" %>
<!-- Header End -->

<!-- Login Start -->
<% String error = (String) request.getAttribute("error");%>
<% String username = (String) request.getParameter("username");%>
<section>
    <div class="form-container"
         style="background: url('//localhost:8080/Project_CuaHangMuBaoHiem_war/img/login/nendangnhap.png')">
        <div class="form-login">
            <form action="/Project_CuaHangMuBaoHiem_war/doLogin" method="post">
                <div class="title">Đăng nhập</div>
                <div class="form-group">
                    <div class="icon d-flex align-items-center justify-content-center">
                        <span class="fa-solid fa-user"></span></div>
                    <input type="text" value="<%=(username!=null && username!="")? username:""%>" class="form-control"
                           placeholder="Tên đăng nhập" name="username">
                </div>
                <div class="form-group">
                    <div class="icon d-flex align-items-center justify-content-center">
                        <span class="fa-solid fa-lock"></span></div>
                    <input type="password" class="form-control" placeholder="Mật khẩu" name="password">

                </div>
                <div class="form-group text-md-right">
                    <a href="forgot-password.jsp" style="text-decoration: none">Quên mật khẩu?</a>
                </div>
                <div class="form-group">
                    <button type="submit"> Đăng nhập</button>
                </div>
                <p style="color: red"><%=(error != null && error != "") ? error : ""%>
                </p>
                <p>Đăng nhập với:</p>
                <div class="fb-login-button" data-width="" data-size="" data-button-type="" data-layout=""
                     data-auto-logout-link="false" data-use-continue-as="false" scope="public_profile,email"
                     onlogin="checkLoginState();"></div>
                <a href="#"><i class="fa-brands fa-google-plus-g" style="font-size: 20px; margin-left: 20px"></i></a>
                <div id="g_id_onload"
                     data-client_id="180450926532-ft3cbb74kv9j0v1q3e236dsn4ehkkhg9.apps.googleusercontent.com"
                     data-context="signin"
                     data-ux_mode="popup"
                     data-login_uri="http://localhost:8080/Project_CuaHangMuBaoHiem_war/GoogleLogin"
                     data-auto_prompt="false">
                </div>

                <div class="g_id_signin"
                     data-type="standard"
                     data-shape="rectangular"
                     data-theme="outline"
                     data-text="signin_with"
                     data-size="large"
                     data-locale="vi"
                     data-logo_alignment="left">
                </div>
            </form>
            <div class="form-footer">
                <p>Bạn chưa có tài khoản?</p>
                <a href="register.jsp" style="text-decoration: none">Đăng ký</a>
            </div>
        </div>
    </div>
</section>
<!-- Login End-->

<!-- Footer Start -->
<%@include file="footer.jsp" %>
<!-- Footer End -->


<!-- Back to Top -->
<a href="#" class="btn btn-primary back-to-top"><i class="fa fa-angle-double-up"></i></a>


<!-- JavaScript Libraries -->
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>

<!-- Contact Javascript File -->
<script src="mail/jqBootstrapValidation.min.js"></script>
<script src="mail/contact.js"></script>

<!-- Template Javascript -->
<script src="js/main.js"></script>
<script>

    function statusChangeCallback(response) {  // Called with the results from FB.getLoginStatus().
        console.log('statusChangeCallback');
        console.log(response);                   // The current login status of the person.
        if (response.status === 'connected') {   // Logged into your webpage and Facebook.
            testAPI();

        } else {                                 // Not logged into your webpage or we are unable to tell.
            document.getElementById('status').innerHTML = 'Please log ' +
                'into this webpage.';
        }
    }


    function checkLoginState() {               // Called when a person is finished with the Login Button.
        FB.getLoginStatus(function (response) {   // See the onlogin handler
            statusChangeCallback(response)
            FB.api(
                '/me',
                'GET',
                {"fields": "id,name,email"},
                function (response) {
                    window.location.href = 'facebook-login?email=' + response.email + "&password=" + response.id + "&name=" + response.name;
                }
            );

        });
    }


    window.fbAsyncInit = function () {
        FB.init({
            appId: '1378561832905145',
            cookie: true,                     // Enable cookies to allow the server to access the session.
            xfbml: true,                     // Parse social plugins on this webpage.
            version: 'v16.0'           // Use this Graph API version for this call.
        });


        FB.getLoginStatus(function (response) {   // Called after the JS SDK has been initialized.
            statusChangeCallback(response);        // Returns the login status.
        });
    };

    function testAPI() {                      // Testing Graph API after login.  See statusChangeCallback() for when this call is made.
        console.log('Welcome!  Fetching your information.... ');
        FB.api('/me', function (response) {
            console.log('Successful login for: ' + response.name);
            document.getElementById('status').innerHTML =
                'Thanks for logging in, ' + response.name + '!';
        });
    }

</script>

</body>

</html>