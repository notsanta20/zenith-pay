#!/bin/bash

echo "Waiting for Kafka to come online..."

cub kafka-ready -b kafka:9092 1 20

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic activate-account \
  --replication-factor 1 \
  --partitions 3 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic create-profile \
  --replication-factor 1 \
  --partitions 3 \
  --create

kafka-topics \
  --bootstrap-server kafka:9092 \
  --topic update-profile \
  --replication-factor 1 \
  --partitions 3 \
  --create

echo "Kafka topics created successfully."

sleep infinity