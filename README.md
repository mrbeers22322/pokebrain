# PokeBrain

**PokeBrain — Your personal Pokémon GO command center.**

PokeBrain is an AI-powered Pokémon GO account advisor designed to help trainers manage collections, prioritize projects, optimize teams, plan trades, and make better decisions.

## Vision

PokeBrain is not just a Pokédex, spreadsheet, raid counter, PvP ranker, or trade tracker.

It is a personal command center for a trainer's actual Pokémon GO account.

## MVP Goal

Build a local-first database and web UI that can:

- Import Pokémon data from Calcy IV, Poké Genie, screenshots, and eventually video
- Store one clean row per Pokémon
- Track confidence/source for each field
- Ask the user questions when confidence is low
- Recommend keep/transfer/evolve/power-up/buddy/trade actions
- Support future team building and trade intelligence

## Recommended MVP Stack

- Python
- SQLite
- FastAPI
- Simple server-rendered pages first
- Later migration path to PostgreSQL + React if needed
