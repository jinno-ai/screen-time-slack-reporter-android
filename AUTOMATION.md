# AUTOMATION.md — screen-time-slack-reporter-android

大ゴール: AIによる全自動開発の実現。人間より圧倒的に効率的に。(fleet共通)

## 発火 (Driver)
- crontab: 0本(2026-09-15 実測・全41行との突合)
- GitHub Actions: 4本(実測ファイル名)
  - android-ci.yml
  - ci.yml
  - coverage-check.yml
  - quality.yml

## Kill Switch
`gh workflow disable <name>`(対象: 上記4本)

## Status
2026-09-15 初版整備(実測: crontab突合・workflow列挙)。fleet台帳: /home/jinno/business_notes/AUTOMATION.md
