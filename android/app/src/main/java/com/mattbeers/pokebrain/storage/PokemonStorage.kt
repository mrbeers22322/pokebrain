package com.mattbeers.pokebrain.storage

import android.content.Context
import com.mattbeers.pokebrain.model.PokemonObservation
import org.json.JSONArray
import org.json.JSONObject

class PokemonStorage(context: Context) {

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    fun saveInventory(pokemonList: List<PokemonObservation>) {
        val inventory = JSONArray()

        pokemonList.forEach { pokemon ->
            inventory.put(
                JSONObject().apply {
                    put("pokemonUuid", pokemon.pokemonUuid)
                    put("species", pokemon.species)
                    putNullable("nickname", pokemon.nickname)
                    putNullable("cp", pokemon.cp)
                    putNullable("level", pokemon.level)
                    putNullable("attackIv", pokemon.attackIv)
                    putNullable("defenseIv", pokemon.defenseIv)
                    putNullable("staminaIv", pokemon.staminaIv)
                    put("shiny", pokemon.shiny)
                    put("shadow", pokemon.shadow)
                    put("purified", pokemon.purified)
                    put("lucky", pokemon.lucky)
                    put("legendary", pokemon.legendary)
                    put("mythical", pokemon.mythical)
                    put("buddy", pokemon.buddy)
                }
            )
        }

        preferences.edit()
            .putString(INVENTORY_KEY, inventory.toString())
            .apply()
    }

    fun loadInventory(): List<PokemonObservation>? {
        val savedInventory = preferences.getString(INVENTORY_KEY, null) ?: return null

        return runCatching {
            val inventory = JSONArray(savedInventory)
            buildList(inventory.length()) {
                for (index in 0 until inventory.length()) {
                    val pokemon = inventory.getJSONObject(index)
                    add(
                        PokemonObservation(
                            pokemonUuid = pokemon.getString("pokemonUuid"),
                            species = pokemon.getString("species"),
                            nickname = pokemon.getNullableString("nickname"),
                            cp = pokemon.getNullableInt("cp"),
                            level = pokemon.getNullableDouble("level"),
                            attackIv = pokemon.getNullableInt("attackIv"),
                            defenseIv = pokemon.getNullableInt("defenseIv"),
                            staminaIv = pokemon.getNullableInt("staminaIv"),
                            shiny = pokemon.optBoolean("shiny"),
                            shadow = pokemon.optBoolean("shadow"),
                            purified = pokemon.optBoolean("purified"),
                            lucky = pokemon.optBoolean("lucky"),
                            legendary = pokemon.optBoolean("legendary"),
                            mythical = pokemon.optBoolean("mythical"),
                            buddy = pokemon.optBoolean("buddy")
                        )
                    )
                }
            }
        }.getOrNull()
    }

    private fun JSONObject.putNullable(key: String, value: Any?) {
        put(key, value ?: JSONObject.NULL)
    }

    private fun JSONObject.getNullableString(key: String): String? =
        if (isNull(key)) null else optString(key)

    private fun JSONObject.getNullableInt(key: String): Int? =
        if (isNull(key)) null else optInt(key)

    private fun JSONObject.getNullableDouble(key: String): Double? =
        if (isNull(key)) null else optDouble(key)

    private companion object {
        const val PREFERENCES_NAME = "pokebrain_storage"
        const val INVENTORY_KEY = "pokemon_inventory"
    }
}
