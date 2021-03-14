#!/usr/bin/env bash

export NAMESPACE=
export TAG=latest
USERNAME=
# third-party repo address
REPO=

docker rmi "$(docker images -f "dangling=true" -q)"

echo
echo "Please input you repo password"
echo
docker login --username="${USERNAME}" "${REPO}"

echo
echo "Start building image using docker-compose build"
echo
docker-compose build

echo
echo "Start pushing image"
echo
docker images | grep "${NAMESPACE}/ts" | awk 'BEGIN{OFS=":"}{print $1,$2}' | xargs -I {} docker push {}
