package com.pulseshift.contactsapp.repository

import androidx.lifecycle.LiveData
import com.pulseshift.contactsapp.ContactsApp
import com.pulseshift.contactsapp.database.ContactsDatabase
import com.pulseshift.contactsapp.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class ContactsRepository {
    private val database = ContactsDatabase.getDatabase(ContactsApp.appContext)
    private val dao = database.contactsDao()
    suspend fun saveContact(contact: Contact) {
        withContext(Dispatchers.IO) {
            dao.insertContact(contact)
        }
    }
    fun getContacts(): LiveData<List<Contact>> = dao.getAllContacts()
    fun getContactById(contactId: Int): LiveData<Contact> = dao.getContactById(contactId)
}