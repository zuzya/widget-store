FROM centos:latest

#install java
RUN yum install -y java-11-openjdk java-11-openjdk-headless \
    && mkdir -p /usr/java && ln -s /etc/alternatives/jre /usr/java/default \
    && rm /etc/localtime && ln -s /usr/share/zoneinfo/Asia/Yekaterinburg /etc/localtime \
    && rm -rf /tmp/dist && rm -rf /var/cache/yum

#scripts
COPY /scripts/run_app.sh /

RUN \
    mkdir /opt/apps && \
    cd / && \
    chown -R root:root /run_app.sh && chmod +x /run_app.sh

#copy jar
ADD ./target/*.jar /opt/apps/app.jar

#run
ENTRYPOINT ["/run_app.sh"]
