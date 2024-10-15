<%@ page language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>

<html>
    <head>
        <style>
            body {
                background: linear-gradient(109.6deg, rgb(6, 2, 2) 32.4%, rgb(137, 30, 47) 98.8%);
                display: flex;
                justify-content: center;
                align-items: center;
                width: 100vw;
                height: 100vh;
            }

            .container {
                background: white;
                width: 600px;
                height: 550px;
                padding: 2em;
                border-radius: 10%;
                text-align: center;
            }

            .title {
                margin: 5%;
            }

            video {
                width: 90%;
                min-width: 400px;
                margin-top: 10%;
            }
        </style>
    </head>

    <body>
        <div class="container">
            <h1 class="title">Your Video: <%= request.getParameter("name") %></h1>
            <video controls>
                    <source src='/streamer/getVideo?name=<%= request.getParameter("name") %>' type="video/mp4">
                     Your browser does not support the video tag.
            </video>
        </div>
    </body>
</html>
