CONTAINER_NAME="onap-as-a-service"
IMAGE="unlenen/onap-as-a-service"
ONAP_IP="192.168.135.171"


has=$(docker ps -a | grep $CONTAINER_NAME)
if [ ! -z "$has" ]; then
	docker stop $CONTAINER_NAME
	docker rm $CONTAINER_NAME
fi

docker run --name $CONTAINER_NAME -d -e ONAP_IP=$ONAP_IP -p 8080:8080 $IMAGE
