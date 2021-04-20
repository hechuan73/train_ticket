#!/usr/bin/env bash

echo
echo "Clean images, Repo: $1"
echo
images=$(docker images | grep "$1"/ts- | awk '{print $3}')

if [[ -n "$images" ]]; then
    echo "$images" | xargs -I {} docker rmi {}
fi
