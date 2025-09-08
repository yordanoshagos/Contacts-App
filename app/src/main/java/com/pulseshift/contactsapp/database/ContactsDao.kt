package com.pulseshift.contactsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pulseshift.contactsapp.model.Contact

@Dao
interface ContactsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contact)

    @Query("SELECT * FROM my_contacts")
    fun getAllContacts(): LiveData<List<Contact>>
    @Query("SELECT * FROM my_contacts WHERE contactId = :contactId")
    fun getContactById(contactId: Int): LiveData<Contact>
}