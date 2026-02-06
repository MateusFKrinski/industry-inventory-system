#!/bin/bash
set -e

echo "WARNING: This operation will permanently delete all database data."
read -r -p "Type 'yes' to continue: " confirm

if [ "$confirm" != "yes" ]; then
  echo "Operation cancelled."
  exit 0
fi

echo "Resetting database."

docker compose down -v

docker volume rm industry-inventory-system_postgres_data 2>/dev/null || true

docker compose up -d postgres

echo "Database reset completed."
