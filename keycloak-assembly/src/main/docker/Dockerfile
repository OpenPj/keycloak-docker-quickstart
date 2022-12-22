FROM ${docker.keycloak.groupId}/${docker.keycloak.image.name}:${keycloak.version}

ENV KEYCLOAK_ADMIN ${docker.keycloak.user}
ENV KEYCLOAK_ADMIN_PASSWORD ${docker.keycloak.password}
ENV JAVA_OPTS -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -agentlib:jdwp=transport=dt_socket,address=0.0.0.0:${docker.keycloak.port.debug.internal},server=y,suspend=n

COPY ${docker.keycloak.volume.internal.realm-config} ${docker.keycloak.volume.external.realm-config}
COPY ${docker.keycloak.volume.internal.providers.authenticator.1} ${docker.keycloak.volume.external.providers.authenticator.1}
COPY ${docker.keycloak.volume.internal.theme} ${docker.keycloak.volume.external.theme}

EXPOSE ${docker.keycloak.port.internal}
EXPOSE ${docker.keycloak.port.debug.internal}

CMD ["${docker.keycloak.run.cmd.arg.1}", "${docker.keycloak.run.cmd.arg.2}"]
