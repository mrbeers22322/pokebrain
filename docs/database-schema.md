# Database Schema Draft

This schema is intentionally broad because PokeBrain needs to support AI recommendations, user preferences, team building, trade intelligence, and audit history.

## Core Tables

### users
Trainer/account owner.

### player_preferences
Controls personalized recommendations.

### pokemon_inventory
One row per actual Pokémon.

### field_confidence
Tracks confidence per field, not just per Pokémon.

### sources
Tracks imports/screenshots/videos.

### candy_inventory
One row per candy family per user.

### projects
Tracks current Pokémon goals.

### trade_intelligence
Tracks normal trades and mirror trades.

### ai_questions
AI/user two-way questions.

### ai_recommendations
Recommendation history.

### ai_site_recommendations
AI product-manager table for site feature suggestions.
