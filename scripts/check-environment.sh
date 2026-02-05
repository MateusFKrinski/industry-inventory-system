#!/usr/bin/env bash
set -e

echo "Checking development environment"
echo

fail=0

check_cmd() {
  local name="$1"
  local cmd="$2"
  local version_cmd="$3"

  printf "%-10s: " "$name"
  if command -v "$cmd" >/dev/null 2>&1; then
    eval "$version_cmd"
  else
    echo "not installed"
    fail=1
  fi
}

check_cmd "Java"    "java"   "java -version 2>&1 | head -1"
check_cmd "Maven"   "mvn"    "mvn -v | head -1"
check_cmd "Node.js" "node"   "node --version"
check_cmd "Docker"  "docker" "docker --version"
check_cmd "Git"     "git"    "git --version"

echo
if [ "$fail" -eq 1 ]; then
  echo "Environment check failed. Please install the missing tools."
  exit 1
else
  echo "Environment is ready."
fi
