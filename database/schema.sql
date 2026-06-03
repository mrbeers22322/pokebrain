-- PokeBrain MVP Schema Draft

CREATE TABLE users (
    user_uuid TEXT PRIMARY KEY,
    display_name TEXT,
    trainer_name TEXT,
    trainer_level INTEGER,
    team TEXT,
    created_at TEXT,
    updated_at TEXT
);

CREATE TABLE player_preferences (
    preference_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL,
    collector_weight INTEGER DEFAULT 50,
    pokedex_weight INTEGER DEFAULT 50,
    raid_weight INTEGER DEFAULT 50,
    pvp_weight INTEGER DEFAULT 50,
    trade_weight INTEGER DEFAULT 50,
    sentimental_weight INTEGER DEFAULT 50,
    storage_pressure INTEGER DEFAULT 50,
    stardust_conservative BOOLEAN DEFAULT TRUE,
    duplicate_policy TEXT,
    shiny_policy TEXT,
    shadow_policy TEXT,
    trade_policy TEXT,
    notes TEXT
);

CREATE TABLE pokemon_inventory (
    pokemon_uuid TEXT PRIMARY KEY,
    user_uuid TEXT NOT NULL,
    source_batch_uuid TEXT,
    species_name TEXT NOT NULL,
    form TEXT,
    nickname TEXT,
    pokedex_number INTEGER,
    generation TEXT,
    region TEXT,
    type_1 TEXT,
    type_2 TEXT,
    cp INTEGER,
    hp INTEGER,
    level REAL,
    atk_iv INTEGER,
    def_iv INTEGER,
    hp_iv INTEGER,
    iv_percent REAL,
    appraisal_stars INTEGER,
    gender TEXT,
    height REAL,
    weight REAL,
    size_class TEXT,
    shiny BOOLEAN,
    lucky BOOLEAN,
    shadow BOOLEAN,
    purified BOOLEAN,
    dynamax BOOLEAN,
    gigantamax BOOLEAN,
    costume BOOLEAN,
    costume_name TEXT,
    background BOOLEAN,
    background_name TEXT,
    favorite BOOLEAN,
    buddy_status TEXT,
    buddy_level TEXT,
    best_buddy BOOLEAN,
    mega_unlocked BOOLEAN,
    catch_date TEXT,
    acquisition_method TEXT,
    acquisition_location TEXT,
    event_name TEXT,
    weather_boosted BOOLEAN,
    original_trainer TEXT,
    traded BOOLEAN,
    trade_date TEXT,
    fast_move TEXT,
    charged_move_1 TEXT,
    charged_move_2 TEXT,
    legacy_move BOOLEAN,
    elite_tm_candidate BOOLEAN,
    candy_family TEXT,
    candy_seen INTEGER,
    xl_candy_seen INTEGER,
    mega_energy_seen INTEGER,
    keep_transfer TEXT,
    favorite_recommended BOOLEAN,
    recommended_tags TEXT,
    next_action TEXT,
    why_keep TEXT,
    decision_reason TEXT,
    confidence_score INTEGER,
    last_verified_at TEXT,
    notes TEXT
);

CREATE TABLE field_confidence (
    confidence_uuid TEXT PRIMARY KEY,
    pokemon_uuid TEXT,
    field_name TEXT,
    field_value TEXT,
    confidence INTEGER,
    source_batch_uuid TEXT,
    verified_by_user BOOLEAN DEFAULT FALSE,
    notes TEXT
);

CREATE TABLE sources (
    source_batch_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    source_type TEXT,
    source_file_name TEXT,
    source_date TEXT,
    parser_version TEXT,
    import_notes TEXT
);

CREATE TABLE candy_inventory (
    candy_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    candy_family TEXT,
    candy INTEGER,
    xl_candy INTEGER,
    mega_energy INTEGER,
    last_verified_at TEXT,
    source_batch_uuid TEXT
);

CREATE TABLE projects (
    project_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    pokemon_uuid TEXT,
    project_type TEXT,
    project_status TEXT,
    project_priority INTEGER,
    next_action TEXT,
    blocking_resource TEXT,
    candy_needed INTEGER,
    xl_needed INTEGER,
    stardust_needed INTEGER,
    goal TEXT,
    notes TEXT
);

CREATE TABLE trade_intelligence (
    trade_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    pokemon_uuid TEXT,
    trade_status TEXT,
    trade_priority INTEGER,
    trade_intent TEXT,
    trade_value INTEGER,
    personal_value INTEGER,
    replaceability INTEGER,
    mirror_trade_candidate BOOLEAN,
    mirror_trade_priority INTEGER,
    mirror_trade_rule TEXT,
    minimum_return_required TEXT,
    notes TEXT
);

CREATE TABLE ai_questions (
    question_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    pokemon_uuid TEXT,
    source TEXT,
    category TEXT,
    question TEXT,
    answer TEXT,
    confidence_before INTEGER,
    confidence_after INTEGER,
    resolved BOOLEAN DEFAULT FALSE,
    created_at TEXT,
    resolved_at TEXT
);

CREATE TABLE ai_recommendations (
    recommendation_uuid TEXT PRIMARY KEY,
    user_uuid TEXT,
    pokemon_uuid TEXT,
    recommendation_type TEXT,
    recommendation TEXT,
    reason TEXT,
    confidence INTEGER,
    priority_score INTEGER,
    impact_score INTEGER,
    difficulty_score INTEGER,
    accepted BOOLEAN,
    created_at TEXT
);

CREATE TABLE ai_site_recommendations (
    site_recommendation_uuid TEXT PRIMARY KEY,
    source TEXT,
    category TEXT,
    recommendation_text TEXT,
    reason TEXT,
    user_demand_score INTEGER,
    impact_score INTEGER,
    difficulty_score INTEGER,
    ai_priority_score INTEGER,
    status TEXT,
    created_at TEXT,
    reviewed_at TEXT,
    implemented_at TEXT
);
