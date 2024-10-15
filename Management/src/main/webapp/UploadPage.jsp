<%@ page language="java"
        contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8"%>

<html>
    <head>
        <title>Upload Video</title>
        <style>
            body {
                background: linear-gradient(109.6deg, rgb(0, 0, 0) 11.2%, rgb(11, 132, 145) 91.1%);
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
                text-align: center;
            }

            .input-field {
                margin: 20px;
            }

			#video-name {
				font-size: 1.4em;
				width: 400px;
				height: 35px;
				border: #222 solid;
				border-width: 0 0 2px 0;
			}

            .upload-button {
                display: none;
            }

            .button {
				font-size: 1.4em;
                cursor: pointer;
				margin: auto;
				display: block;
				width: 410px;
				background: #09707b;
				color: white;
				border-radius: 5px;
				transition: all 0.3s ease;
            }

			.light {
				background: #096872;
			}

			.dark {
				background: #054248;
			}

			.culomnar-form {
				display: flex;
			    justify-content: space-between;
  			    flex-direction: column;
			}

			h1 {
				margin-bottom: 3.7em;
			}
        </style>
    </head>

    <body>
        <div class="container">
            <h1>Upload Another Video</h1>
            <form action="/manager/upload-video" method="post" enctype="multipart/form-data" class="culomnar-form">

                <div class="input-field">
                    <input id="video-name" placeholder="Video Name" name="video-name">
                </div>

                <div class="input-field">
                    <label class="button light" for="video-file">Select Your Video</label>
                    <input class="upload-button" type="file" id="video-file" name="video-file" accept="video/*">
                </div>

                <div class="input-field">
					<button class="button dark">Upload</button>
				</div>
            </form>
        </div>
    </body>
</html>

