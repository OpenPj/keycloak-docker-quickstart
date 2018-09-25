FROM ${docker.keycloak.groupId}/${docker.keycloak.image.name}:${keycloak.version}

ENV KEYCLOAK_USER ${docker.keycloak.user}
ENV KEYCLOAK_PASSWORD ${docker.keycloak.password}
ENV DB_VENDOR ${docker.keycloak.db.vendor}
ENV JAVA_OPTS -server -Xms64m -Xmx512m -XX:MetaspaceSize=96M -XX:MaxMetaspaceSize=256m -Djava.net.preferIPv4Stack=true -Djboss.modules.system.pkgs=org.jboss.byteman -Djava.awt.headless=true -agentlib:jdwp=transport=dt_socket,address=9090,server=y,suspend=n


COPY ${docker.keycloak.volume.internal.realm-config} ${docker.keycloak.volume.external.realm-config}
COPY ${docker.keycloak.volume.internal.configuration.standalone} ${docker.keycloak.volume.external.configuration.standalone}
COPY ${docker.keycloak.volume.internal.providers.authenticator.1} ${docker.keycloak.volume.external.providers.authenticator.1}
COPY ${docker.keycloak.volume.internal.theme} ${docker.keycloak.volume.external.theme}

EXPOSE ${docker.keycloak.port.internal}
EXPOSE ${docker.keycloak.port.external}

CMD ["${docker.keycloak.run.cmd.arg.1}", "${docker.keycloak.run.cmd.arg.2}", "${docker.keycloak.run.cmd.arg.3}", "${docker.keycloak.run.cmd.arg.4}", "${docker.keycloak.run.cmd.arg.5}", "${docker.keycloak.run.cmd.arg.6}","${docker.keycloak.run.cmd.arg.7}","${docker.keycloak.run.cmd.arg.8}"]