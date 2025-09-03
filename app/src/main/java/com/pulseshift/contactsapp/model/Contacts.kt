package com.pulseshift.contactsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Contacts (
    @PrimaryKey(autoGenerate = true) var contactId: Int,
    var name: String,
    var phoneNumber: String,
    var email: String,
    var imageUrl: String


)
