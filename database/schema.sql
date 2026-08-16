-- PokeBrain SQLite schema.
-- The application must execute PRAGMA foreign_keys = ON for every connection.

PRAGMA foreign_keys = ON;

CREATE TABLE users (
    user_uuid TEXT PRIMARY KEY,
    display_name TEXT NOT NULL,
    trainer_name TEXT,
    trainer_level INTEGER CHECK (trainer_level IS NULL OR trainer_level BETWEEN 1 AND 100),
    team TEXT CHECK (team IS NULL OR team IN ('mystic', 'valor', 'instinct', 'blank')),
    created_at TEXT NOT NULL,
    updated_at TEXT NOT NULL
);

CREATE TABLE player_preferences (
    preference_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL UNIQUE REFERENCES users(user_uuid) ON DELETE CASCADE,
    collector_weight INTEGER NOT NULL DEFAULT 50 CHECK (collector_weight BETWEEN 0 AND 100),
    pokedex_weight INTEGER NOT NULL DEFAULT 50 CHECK (pokedex_weight BETWEEN 0 AND 100),
    raid_weight INTEGER NOT NULL DEFAULT 50 CHECK (raid_weight BETWEEN 0 AND 100),
    pvp_weight INTEGER NOT NULL DEFAULT 50 CHECK (pvp_weight BETWEEN 0 AND 100),
    trade_weight INTEGER NOT NULL DEFAULT 50 CHECK (trade_weight BETWEEN 0 AND 100),
    sentimental_weight INTEGER NOT NULL DEFAULT 50 CHECK (sentimental_weight BETWEEN 0 AND 100),
    storage_pressure INTEGER NOT NULL DEFAULT 50 CHECK (storage_pressure BETWEEN 0 AND 100),
    stardust_conservative INTEGER NOT NULL DEFAULT 1 CHECK (stardust_conservative IN (0, 1)),
    duplicate_policy TEXT,
    shiny_policy TEXT,
    shadow_policy TEXT,
    trade_policy TEXT,
    notes TEXT
);

-- One canonical row per Pokémon currently known to the user.
-- Rows are retained after transfer/trade so history and deduplication remain auditable.
CREATE TABLE pokemon_inventory (
    pokemon_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    lifecycle_status TEXT NOT NULL DEFAULT 'active'
        CHECK (lifecycle_status IN ('active', 'transferred', 'traded', 'fused', 'unknown')),
    species_name TEXT NOT NULL,
    form TEXT,
    nickname TEXT,
    pokedex_number INTEGER CHECK (pokedex_number IS NULL OR pokedex_number > 0),
    generation TEXT,
    region TEXT,
    type_1 TEXT,
    type_2 TEXT,
    cp INTEGER CHECK (cp IS NULL OR cp >= 0),
    hp INTEGER CHECK (hp IS NULL OR hp >= 0),
    level REAL CHECK (level IS NULL OR level BETWEEN 1 AND 100),
    atk_iv INTEGER CHECK (atk_iv IS NULL OR atk_iv BETWEEN 0 AND 15),
    def_iv INTEGER CHECK (def_iv IS NULL OR def_iv BETWEEN 0 AND 15),
    hp_iv INTEGER CHECK (hp_iv IS NULL OR hp_iv BETWEEN 0 AND 15),
    iv_percent REAL CHECK (iv_percent IS NULL OR iv_percent BETWEEN 0 AND 100),
    appraisal_stars INTEGER CHECK (appraisal_stars IS NULL OR appraisal_stars BETWEEN 0 AND 4),
    gender TEXT,
    height REAL CHECK (height IS NULL OR height >= 0),
    weight REAL CHECK (weight IS NULL OR weight >= 0),
    size_class TEXT,
    shiny INTEGER NOT NULL DEFAULT 0 CHECK (shiny IN (0, 1)),
    lucky INTEGER NOT NULL DEFAULT 0 CHECK (lucky IN (0, 1)),
    shadow INTEGER NOT NULL DEFAULT 0 CHECK (shadow IN (0, 1)),
    purified INTEGER NOT NULL DEFAULT 0 CHECK (purified IN (0, 1)),
    dynamax INTEGER NOT NULL DEFAULT 0 CHECK (dynamax IN (0, 1)),
    gigantamax INTEGER NOT NULL DEFAULT 0 CHECK (gigantamax IN (0, 1)),
    costume INTEGER NOT NULL DEFAULT 0 CHECK (costume IN (0, 1)),
    costume_name TEXT,
    background INTEGER NOT NULL DEFAULT 0 CHECK (background IN (0, 1)),
    background_name TEXT,
    favorite INTEGER NOT NULL DEFAULT 0 CHECK (favorite IN (0, 1)),
    buddy_status TEXT,
    buddy_level TEXT,
    best_buddy INTEGER NOT NULL DEFAULT 0 CHECK (best_buddy IN (0, 1)),
    mega_unlocked INTEGER NOT NULL DEFAULT 0 CHECK (mega_unlocked IN (0, 1)),
    catch_date TEXT,
    acquisition_method TEXT,
    acquisition_location TEXT,
    event_name TEXT,
    weather_boosted INTEGER NOT NULL DEFAULT 0 CHECK (weather_boosted IN (0, 1)),
    original_trainer TEXT,
    traded INTEGER NOT NULL DEFAULT 0 CHECK (traded IN (0, 1)),
    trade_date TEXT,
    keep_transfer TEXT CHECK (keep_transfer IS NULL OR keep_transfer IN ('keep', 'transfer', 'review', 'unknown')),
    favorite_recommended INTEGER CHECK (favorite_recommended IS NULL OR favorite_recommended IN (0, 1)),
    recommended_tags TEXT,
    next_action TEXT,
    why_keep TEXT,
    decision_reason TEXT,
    canonical_fingerprint TEXT,
    first_seen_at TEXT NOT NULL,
    last_verified_at TEXT,
    transferred_at TEXT,
    notes TEXT
);

-- Every import, screenshot, OCR run, or manual edit gets a source record.
CREATE TABLE source_batches (
    source_batch_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    source_type TEXT NOT NULL CHECK (source_type IN ('manual', 'csv', 'json', 'screenshot', 'video', 'ocr', 'api', 'other')),
    source_file_name TEXT,
    source_file_sha256 TEXT,
    source_date TEXT,
    parser_version TEXT,
    ocr_engine TEXT,
    created_at TEXT NOT NULL,
    import_notes TEXT
);

-- Immutable observations are retained even after the canonical record changes.
CREATE TABLE pokemon_observations (
    observation_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    source_batch_uuid TEXT NOT NULL REFERENCES source_batches(source_batch_uuid) ON DELETE RESTRICT,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    observation_type TEXT NOT NULL CHECK (observation_type IN ('manual', 'csv', 'json', 'ocr', 'api')),
    observed_at TEXT NOT NULL,
    raw_payload TEXT,
    parser_version TEXT,
    match_status TEXT NOT NULL DEFAULT 'unreviewed'
        CHECK (match_status IN ('unreviewed', 'matched', 'new', 'conflict', 'rejected')),
    notes TEXT
);

-- Field-level extraction records preserve raw OCR text, normalized values, and review decisions.
CREATE TABLE observation_fields (
    field_observation_uuid TEXT PRIMARY KEY,
    observation_uuid TEXT NOT NULL REFERENCES pokemon_observations(observation_uuid) ON DELETE CASCADE,
    field_name TEXT NOT NULL,
    raw_value TEXT,
    normalized_value TEXT,
    confidence INTEGER CHECK (confidence IS NULL OR confidence BETWEEN 0 AND 100),
    extraction_method TEXT NOT NULL CHECK (extraction_method IN ('manual', 'csv', 'json', 'ocr', 'api')),
    ocr_region_json TEXT,
    verified_by_user INTEGER NOT NULL DEFAULT 0 CHECK (verified_by_user IN (0, 1)),
    review_status TEXT NOT NULL DEFAULT 'unreviewed'
        CHECK (review_status IN ('unreviewed', 'accepted', 'corrected', 'rejected')),
    created_at TEXT NOT NULL
);

-- State transitions such as transfer, trade, CP changes, tag changes, and move changes.
CREATE TABLE pokemon_events (
    event_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    source_batch_uuid TEXT REFERENCES source_batches(source_batch_uuid) ON DELETE SET NULL,
    event_type TEXT NOT NULL CHECK (event_type IN (
        'created', 'updated', 'transferred', 'traded', 'fused', 'reappeared', 'move_changed',
        'tag_changed', 'cp_changed', 'iv_changed', 'nickname_changed', 'status_changed'
    )),
    event_at TEXT NOT NULL,
    before_json TEXT,
    after_json TEXT,
    confidence INTEGER CHECK (confidence IS NULL OR confidence BETWEEN 0 AND 100),
    reason TEXT
);

CREATE TABLE pokemon_moves (
    pokemon_move_uuid TEXT PRIMARY KEY,
    pokemon_uuid TEXT NOT NULL REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE CASCADE,
    move_slot INTEGER NOT NULL CHECK (move_slot BETWEEN 1 AND 2),
    move_id TEXT,
    move_name TEXT NOT NULL,
    move_type TEXT,
    legacy INTEGER NOT NULL DEFAULT 0 CHECK (legacy IN (0, 1)),
    elite_tm_candidate INTEGER NOT NULL DEFAULT 0 CHECK (elite_tm_candidate IN (0, 1)),
    first_seen_at TEXT NOT NULL,
    last_verified_at TEXT,
    source_batch_uuid TEXT REFERENCES source_batches(source_batch_uuid) ON DELETE SET NULL,
    UNIQUE (pokemon_uuid, move_slot)
);

CREATE TABLE candy_inventory (
    candy_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    candy_family TEXT NOT NULL,
    candy INTEGER NOT NULL DEFAULT 0 CHECK (candy >= 0),
    xl_candy INTEGER NOT NULL DEFAULT 0 CHECK (xl_candy >= 0),
    mega_energy INTEGER NOT NULL DEFAULT 0 CHECK (mega_energy >= 0),
    last_verified_at TEXT NOT NULL,
    source_batch_uuid TEXT REFERENCES source_batches(source_batch_uuid) ON DELETE SET NULL,
    UNIQUE (user_uuid, candy_family)
);

CREATE TABLE candy_events (
    candy_event_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    candy_family TEXT NOT NULL,
    event_type TEXT NOT NULL CHECK (event_type IN ('observed', 'earned', 'spent', 'transferred', 'corrected')),
    candy_delta INTEGER,
    xl_candy_delta INTEGER,
    mega_energy_delta INTEGER,
    candy_after INTEGER CHECK (candy_after IS NULL OR candy_after >= 0),
    xl_candy_after INTEGER CHECK (xl_candy_after IS NULL OR xl_candy_after >= 0),
    mega_energy_after INTEGER CHECK (mega_energy_after IS NULL OR mega_energy_after >= 0),
    occurred_at TEXT NOT NULL,
    source_batch_uuid TEXT REFERENCES source_batches(source_batch_uuid) ON DELETE SET NULL,
    notes TEXT
);

CREATE TABLE duplicate_candidates (
    candidate_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    left_pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE CASCADE,
    right_pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE CASCADE,
    match_score INTEGER NOT NULL CHECK (match_score BETWEEN 0 AND 100),
    matching_fields_json TEXT,
    decision TEXT NOT NULL DEFAULT 'pending' CHECK (decision IN ('pending', 'same', 'different', 'merged', 'ignored')),
    decided_at TEXT,
    notes TEXT,
    CHECK (left_pokemon_uuid IS NOT NULL AND right_pokemon_uuid IS NOT NULL AND left_pokemon_uuid <> right_pokemon_uuid),
    UNIQUE (left_pokemon_uuid, right_pokemon_uuid)
);

CREATE TABLE projects (
    project_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    project_type TEXT NOT NULL,
    project_status TEXT NOT NULL DEFAULT 'planned',
    project_priority INTEGER CHECK (project_priority IS NULL OR project_priority BETWEEN 0 AND 100),
    next_action TEXT,
    blocking_resource TEXT,
    candy_needed INTEGER CHECK (candy_needed IS NULL OR candy_needed >= 0),
    xl_needed INTEGER CHECK (xl_needed IS NULL OR xl_needed >= 0),
    stardust_needed INTEGER CHECK (stardust_needed IS NULL OR stardust_needed >= 0),
    goal TEXT,
    notes TEXT
);

CREATE TABLE trade_intelligence (
    trade_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    trade_status TEXT NOT NULL DEFAULT 'considering',
    trade_priority INTEGER CHECK (trade_priority IS NULL OR trade_priority BETWEEN 0 AND 100),
    trade_intent TEXT,
    trade_value INTEGER,
    personal_value INTEGER,
    replaceability INTEGER,
    mirror_trade_candidate INTEGER CHECK (mirror_trade_candidate IS NULL OR mirror_trade_candidate IN (0, 1)),
    mirror_trade_priority INTEGER,
    mirror_trade_rule TEXT,
    minimum_return_required TEXT,
    notes TEXT
);

CREATE TABLE ai_questions (
    question_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    source TEXT,
    category TEXT,
    question TEXT NOT NULL,
    answer TEXT,
    confidence_before INTEGER CHECK (confidence_before IS NULL OR confidence_before BETWEEN 0 AND 100),
    confidence_after INTEGER CHECK (confidence_after IS NULL OR confidence_after BETWEEN 0 AND 100),
    resolved INTEGER NOT NULL DEFAULT 0 CHECK (resolved IN (0, 1)),
    created_at TEXT NOT NULL,
    resolved_at TEXT
);

CREATE TABLE ai_recommendations (
    recommendation_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL REFERENCES users(user_uuid) ON DELETE CASCADE,
    pokemon_uuid TEXT REFERENCES pokemon_inventory(pokemon_uuid) ON DELETE SET NULL,
    recommendation_type TEXT NOT NULL,
    recommendation TEXT NOT NULL,
    reason TEXT,
    confidence INTEGER CHECK (confidence IS NULL OR confidence BETWEEN 0 AND 100),
    priority_score INTEGER,
    impact_score INTEGER,
    difficulty_score INTEGER,
    accepted INTEGER CHECK (accepted IS NULL OR accepted IN (0, 1)),
    created_at TEXT NOT NULL
);

CREATE INDEX idx_pokemon_user_status ON pokemon_inventory(user_uuid, lifecycle_status);
CREATE INDEX idx_pokemon_species ON pokemon_inventory(user_uuid, species_name);
CREATE INDEX idx_pokemon_fingerprint ON pokemon_inventory(user_uuid, canonical_fingerprint);
CREATE INDEX idx_sources_user_date ON source_batches(user_uuid, created_at);
CREATE INDEX idx_observations_pokemon ON pokemon_observations(pokemon_uuid, observed_at);
CREATE INDEX idx_observation_fields_name ON observation_fields(observation_uuid, field_name);
CREATE INDEX idx_events_pokemon_date ON pokemon_events(pokemon_uuid, event_at);
CREATE INDEX idx_candy_events_family_date ON candy_events(user_uuid, candy_family, occurred_at);
CREATE INDEX idx_duplicate_review ON duplicate_candidates(user_uuid, decision);
