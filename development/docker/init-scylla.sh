#!/bin/bash

until cqlsh -u "$SCYLLA_USERNAME" -p "$SCYLLA_PASSWORD" -e "describe cluster"; do
  >&2 echo "ScyllaDB is unavailable - waiting..."
  sleep 5
done

cqlsh -u "$SCYLLA_USERNAME" -p "$SCYLLA_PASSWORD" -e "
  CREATE KEYSPACE IF NOT EXISTS logistics_keyspace
  WITH REPLICATION = {'class': 'NetworkTopologyStrategy', 'dc1': 3};
"

echo "Keyspace created successfully."
