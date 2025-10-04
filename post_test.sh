#!/bin/bash
REPORT_DIR="./reports"

# find the latest created report
LATEST_REPORT=$(ls -t ${REPORT_DIR}/InsiderTestReport_*.html | head -n 1)

if [ -f "$LATEST_REPORT" ]; then
  echo "✅ Test completed. Report file: $LATEST_REPORT"
  echo "🔍 Report is opening in the browser..."

  # Windows (WSL or Git Bash)
  if command -v explorer.exe >/dev/null 2>&1; then
    explorer.exe "$(wslpath -w "$LATEST_REPORT")"

  # MacOS
  elif command -v open >/dev/null 2>&1; then
    open "$LATEST_REPORT"

  # Linux
  elif command -v xdg-open >/dev/null 2>&1; then
    xdg-open "$LATEST_REPORT"
  else
    echo "Browser not open automatically, you can open the report manually:"
    echo "$LATEST_REPORT"
  fi
else
  echo "❌ Report not found!"
fi