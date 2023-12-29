#!/usr/bin/env bash

CONDUCTOR_HOST=http://localhost:8080

ADD_TASK=$(cat definitions/add-task.json)

curl -X POST "$CONDUCTOR_HOST/api/metadata/taskdefs" \
  -H "Content-Type: application/json" \
  --data-raw "$ADD_TASK" \
  --compressed

SQUARE_TASK=$(cat definitions/square-task.json)

curl -X POST "$CONDUCTOR_HOST/api/metadata/taskdefs" \
  -H "Content-Type: application/json" \
  --data-raw "$SQUARE_TASK" \
  --compressed

WORKFLOW=$(cat definitions/add-workflow.json)

curl -X POST "$CONDUCTOR_HOST/api/metadata/workflow" \
  -H "Content-Type: application/json" \
  --data-raw "$WORKFLOW" \
  --compressed