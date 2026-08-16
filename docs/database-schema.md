# PokeBrain database

PokeBrain uses a local-first SQLite model. Every application connection must enable
`PRAGMA foreign_keys = ON`.

## Data layers

- `pokemon_inventory` is the current canonical record for each known Pokémon.
- `source_batches` records where an observation came from: manual entry, CSV, JSON,
  screenshot, video, OCR, or API.
- `pokemon_observations` is immutable evidence from each import or scan. It may be
  unlinked until duplicate matching is reviewed.
- `observation_fields` retains raw extracted text, normalized values, confidence,
  OCR region data, and user corrections.
- `pokemon_events` records state transitions such as transfer, trade, CP, IV, tag,
  nickname, and move changes. Canonical records are never deleted for ordinary
  lifecycle changes.

## Inventory and history

`pokemon_inventory` contains the latest known state. A transferred or traded
Pokémon remains in the table with a lifecycle status so historical scans cannot
resurrect it accidentally. `pokemon_events` and source records provide an audit
trail for every change.

`pokemon_moves` and `candy_inventory` are current snapshots. `candy_events`
records observed, earned, spent, transferred, and corrected changes over time.

## Duplicate handling

`duplicate_candidates` stores possible matches and the evidence score. Matching is
never destructive: the system can mark candidates as the same, different, merged,
or ignored after review. A fingerprint is only an indexable hint, not a unique key,
because two Pokémon can legitimately share the same visible values.

## Validation rules

Foreign keys, uniqueness constraints, indexes, and checks protect user ownership,
IV ranges, boolean values, lifecycle states, confidence scores, and non-negative
resources. Import and OCR code must write observations first, then update the
canonical record only after matching and validation.
