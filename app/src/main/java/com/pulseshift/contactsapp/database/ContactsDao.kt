package com.pulseshift.contactsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pulseshift.contactsapp.model.Contacts

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contacts)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contacts>>
}