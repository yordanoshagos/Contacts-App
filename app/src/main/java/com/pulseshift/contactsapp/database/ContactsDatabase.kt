package com.pulseshift.contactsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pulseshift.contactsapp.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactsDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        private var database: ContactsDatabase? = null

        fun getDatabase(context: Context): ContactsDatabase {
            if (database == null) {
                database = Room
                    .databaseBuilder(context, ContactsDatabase::class.java, "contactsDb")
                    .fallbackToDestructiveMigration(false)
                    .build()
            }
            return database as ContactsDatabase
        }
    }
}

// migrations will be excuted  when we invoke the dao dunctions and it will give us a response object htet will impliment