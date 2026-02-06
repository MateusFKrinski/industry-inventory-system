#!/bin/bash
set -e

BACKUP_DIR="backups/$(date +%Y%m%d_%H%M%S)"
mkdir -p "$BACKUP_DIR"

echo "Creating database backup."

docker exec inventory-postgres pg_dump \
  -U admin \
  -d inventory_db \
  -F c \
  -f /tmp/backup.dump

docker cp inventory-postgres:/tmp/backup.dump "$BACKUP_DIR/inventory_backup.dump"

echo "Backup created at: $BACKUP_DIR/inventory_backup.dump"
