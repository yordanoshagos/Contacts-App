package com.pulseshift.contactsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_contacts")

//it created a table outside

data class Contact(
    @PrimaryKey(autoGenerate = true) var contactId: Int = 0,
    var name: String,
    var phoneNumber: String,
    var email: String = "",
    var imageUrl: String? = null
)