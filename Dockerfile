FROM java:openjdk-8u40-jdk AS parent
VOLUME /tmp
COPY /spring-application /project
ENV MAVEN_VERSION 3.2.3
RUN curl -sSL http://archive.apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar xzf - -C /usr/share \
    && mv /usr/share/apache-maven-$MAVEN_VERSION /usr/share/maven \
    && ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
RUN cd project && mvn install && cp target/*.jar ../../service.jar



FROM java:openjdk-8u40-jdk
VOLUME /tmp
COPY --from=parent /service.jar /service.jar
ENTRYPOINT ["java", "-jar", "/service.jar"]