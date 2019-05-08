# Keycloak Docker Quickstart
This project is aimed to manage Keycloak extensions using a fully Maven lifecycle.
The current supported component are the following:

*  keycloak-assembly
*  keycloak-identity-providers
*  keycloak-integration-tests
*  keycloak-theme

Developed and tested on Keycloak 5.0.0.

## Requirements
In order to use this project, you need to install the following components:

* Apache Maven 3.6.x
* Docker


## Keycloak Assembly
This is the Maven assembly module for deploying the platform in any environment that is not currently supporting Docker.
The final artifact for this module is a folder tree as the following:

* realm-config
* standalone
* themes

## Keycloak Identity Providers
Main module that includes all the needed custom providers and authenticators.
The default project contains only a single custom authenticator but you can easily add new providers following the same configuration approach.

**Custom Authenticator**
This is the implementation of the secret question provider taken from the Keycloak examples folder.

## Keycloak Integration Tests
This module will install and run all the artifacts using Docker.
It can include also your own code for integration tests after executing your custom image.

**Building**
```
mvn clean package
```

This will also regenerate the Dockerfile in the project root.

**Running Integration Tests**

To execute integration tests you can run the following command:

```
mvn clean integration-test
```

A specific Maven property (docker.keepRunning) is provided to decide if keep running the container after the execution of tests or not.
Please consider that the default value is true, this means that you have to manually stop the container after executing integration-test.


## Saving and running the Custom Keycloak Docker image
In order to execute the build process locally of the Docker image **custom/keycloak:latest**
```
mvn clean package
mvn install
```

After the startup of Keycloak, you can access as admin/admin from the admin console URL:
```
http://localhost:8080/auth/admin
```

A quickstart realm is provided to test your extensions.

To dinamically regenerate the Dockerfile and build only the Docker image:
```
mvn clean package
docker build -t custom/keycloak:${project.version} .
```

Run the latest Docker image:

```
docker run -p 8080:8080 custom/keycloak:latest
```

## Keycloak Themes
This module includes the default example of themes with the addition of the secret question sample templates taken from the default authenticator.

## Deliverables

**Overlays**

| Source | Target deployment | Artifact |
| -------- | -------- | -------- |
| /src/main/resources/standalone/configuration/standalone.xml | /opt/jboss/keycloak/standalone/configuration/standalone.xml | XML |
| /src/main/realm-config | /opt/jboss/keycloak/realm-config | Folder |
| /keycloak-theme/target/keycloak-theme-${project.version}.jar | /opt/jboss/keycloak/standalone/deployments | JAR |
| /keycloak-identity-providers/keycloak-identity-provider-authenticator/target/keycloak-identity-provider-authenticator-${project.version}.jar | /opt/jboss/keycloak/standalone/deployments | JAR |

**Docker image**
The Docker image is written inside your local repo but it is also available in the following path:

```
/target/docker/custom/keycloak/${project.version}/tmp/docker-build.tar.gz
```
