#!/bin/bash
set -e

if [ -z "$1" ]; then
  echo "Usage: $0 <backup_file.dump>"
  exit 1
fi

BACKUP_FILE="$1"

echo "Restoring database from backup: $BACKUP_FILE"

docker cp "$BACKUP_FILE" inventory-postgres:/tmp/restore.dump

docker exec inventory-postgres pg_restore \
  -U admin \
  -d inventory_db \
  --clean \
  --if-exists \
  /tmp/restore.dump

echo "Database restore completed successfully."
