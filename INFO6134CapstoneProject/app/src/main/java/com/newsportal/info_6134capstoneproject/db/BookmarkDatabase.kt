package com.newsportal.info_6134capstoneproject.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.newsportal.info_6134capstoneproject.model.DBArticle
@Database(
    entities = [DBArticle::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract fun dao(): Dao

    companion object{
        @Volatile
        private var INSTANCE: BookmarkDatabase? = null

        fun getDatabase(context: Context): BookmarkDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_article"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}