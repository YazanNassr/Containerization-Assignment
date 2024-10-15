<%@ page language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>

<%@ page import="java.util.List, database.VideoDAO" %>

<%
    String username = (String) session.getAttribute("username");
    if (username == null) {
        response.sendRedirect("/streamer/login");
    }
%>

<html>
    <head>
        <style>
            body {
                background: linear-gradient(109.6deg, rgb(6, 2, 2) 32.4%, rgb(137, 30, 47) 98.8%);
                width: 100vw;
                height: 100vh;
                display: flex;
                justify-content: center;
                align-items: center;
            }

            .container {
                background: white;
                width: 600px;
                height: 550px;
                padding: 2em;
                border-radius: 10%;
            }

            .title {
                text-align: center;
                margin: 5%;
            }
        </style>
    </head>

    <body>

        <div class="container">
            <h1 class="title">Welcome, <%= username %>!</h1>

            <h2>Here is a list of your videos:</h2>

            <ul>
                <%
                List<String> names = VideoDAO.getInstance().videoNames(username);
                for (String name : names) {
                %>
                    <li><a href="/streamer/watch?name=<%= name %>"><%= name %></a></li>
                <%
                }
                %>
            </ul>

        </div>
    </body>
</html>
