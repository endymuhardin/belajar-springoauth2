<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>

        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">

        <!-- Optional theme -->
        <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap-theme.min.css">

        <style>
            body {
                padding-top: 40px;
                padding-bottom: 40px;
                background-color: #eee;
            }

            .form-signin {
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin .checkbox {
                font-weight: normal;
            }
            .form-signin .form-control {
                position: relative;
                height: auto;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }
        </style>

    </head>
    <body>
        <div class="container">
            
            <% if (request.getParameter("error") != null) { %>
            <div class="alert alert-danger">${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</div>
            <% } %>
            <form class="form-signin" role="form" action="j_spring_security_check" method="post">
                <h2 class="form-signin-heading">Silahkan Login</h2>
                <input type="text" name="j_username" class="form-control" placeholder="Username" required autofocus>
                <input type="password" name="j_password" class="form-control" placeholder="Password" required>
                <label class="checkbox">
                    <input type="checkbox" value="remember-me"> Remember me
                </label>
                <input type="hidden" name="spring-security-redirect" value="http://google.com">
                <input type="hidden"
                       name="${_csrf.parameterName}"
                       value="${_csrf.token}"/>

                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
            </form>

        </div> <!-- /container -->
    </body>
</html>
