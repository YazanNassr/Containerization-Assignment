FROM jetty

COPY maven/root.war /var/lib/jetty/webapps/root.war

RUN java -jar "$JETTY_HOME/start.jar" --add-modules=ee10-webapp,ee10-deploy,ee10-jsp,ee10-jstl,ee10-websocket-jetty,ee10-websocket-jakarta,server,http,deploy

EXPOSE 8080