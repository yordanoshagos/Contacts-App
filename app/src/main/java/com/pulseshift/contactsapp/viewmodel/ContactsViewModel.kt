package com.pulseshift.contactsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pulseshift.contactsapp.model.Contact
import com.pulseshift.contactsapp.repository.ContactsRepository

import kotlinx.coroutines.launch

class ContactsViewModel : ViewModel() {
    val contacts = MutableLiveData<List<Contact>>()
    private val repo = ContactsRepository()

    fun getContacts() {
        repo.getContacts().observeForever { contactList ->
            contacts.value = contactList
        }
    }

    fun saveContact(contact: Contact) {
        viewModelScope.launch {
            repo.saveContact(contact)
        }
    }
}