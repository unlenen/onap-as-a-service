cd ..
mvn clean install

docker build . -t unlenen/onap-as-a-service:1.0
