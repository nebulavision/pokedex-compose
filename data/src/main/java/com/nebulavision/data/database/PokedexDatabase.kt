package com.nebulavision.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nebulavision.data.database.dao.PokemonDao
import com.nebulavision.data.database.entity.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PokemonTypeConverter::class)
abstract class PokedexDatabase : RoomDatabase(){
    abstract fun pokemonDao(): PokemonDao

    companion object{
        @Volatile
        private var Instance: PokedexDatabase? = null

        fun getDatabase(context: Context): PokedexDatabase{
            return Instance ?: synchronized(this){
                Room
                    .databaseBuilder(context, PokedexDatabase::class.java, "pokedex_database")
                    .fallbackToDestructiveMigration()
                    .build()
            }.also { Instance = it }
        }
    }
}