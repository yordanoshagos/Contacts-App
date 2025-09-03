package com.pulseshift.contactsapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pulseshift.contactsapp.model.Contacts

@Database(entities = arrayOf(Contacts::class), version = 1)
abstract class ContactsDatabase: RoomDatabase() {

}