FROM openjdk:17-jdk-alpine3.14
LABEL NAME="Unlenen ONAP API"
LABEL AUTHORS="Nebi UNLENEN <unlenen@gmail.com>"

RUN apk add tzdata ;  cp /usr/share/zoneinfo/Etc/GMT-3 /etc/localtime ; echo 'Etc/GMT-3' > /etc/timezone   ;  date 

RUN mkdir -p /opt/unlenen
ADD target/onap_service_manager-1.0.jar /opt/unlenen/onap_service_manager.jar

#Entrypoint
ADD entrypoint.sh /opt/unlenen/entrypoint.sh
RUN chmod +x /opt/unlenen/entrypoint.sh
ENTRYPOINT [ "sh", "-c", "/opt/unlenen/entrypoint.sh" ]
