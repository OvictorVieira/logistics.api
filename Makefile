docker-up:
	docker compose -f development/docker/docker-compose.yml up

migrate:
	@echo "Running CQL migration scripts..."
	docker exec -i scylla cqlsh < migrations/setup.cql
