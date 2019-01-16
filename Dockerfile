FROM jboss/keycloak:4.8.2.Final

ENV KEYCLOAK_USER admin
ENV KEYCLOAK_PASSWORD admin
ENV DB_VENDOR H2
ENV JAVA_OPTS -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true -agentlib:jdwp=transport=dt_socket,address=9090,server=y,suspend=n


COPY /src/main/realm-config /opt/jboss/keycloak/realm-config
COPY /src/main/resources/standalone/configuration/standalone.xml /opt/jboss/keycloak/standalone/configuration/standalone.xml
COPY /keycloak-identity-providers/keycloak-identity-provider-authenticator/target/keycloak-identity-provider-authenticator-0.0.1-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments/keycloak-identity-provider-authenticator-0.0.1-SNAPSHOT.jar
COPY /keycloak-theme/target/keycloak-theme-0.0.1-SNAPSHOT.jar /opt/jboss/keycloak/standalone/deployments/keycloak-theme-0.0.1-SNAPSHOT.jar

EXPOSE 8080
EXPOSE 8080

CMD ["-b", "0.0.0.0", "-Dkeycloak.migration.action=import", "-Dkeycloak.migration.provider=dir", "-Dkeycloak.migration.dir=/opt/jboss/keycloak/realm-config", "-Dkeycloak.migration.strategy=OVERWRITE_EXISTING","-bmanagement","0.0.0.0"]