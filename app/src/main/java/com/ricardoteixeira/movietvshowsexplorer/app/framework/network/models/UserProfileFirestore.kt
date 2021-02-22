package com.ricardoteixeira.movietvshowsexplorer.app.framework.network

import com.google.firebase.Timestamp

data class UserProfileFirestore(
    val id: String = "",
    val name: String= "",
    val email: String= "",
    val imageUri: String= "",
    val dateCreated: Timestamp = Timestamp.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileFirestore

        if (id != other.id) return false
        if (name != other.name) return false
        if (email != other.email) return false
        if (imageUri != other.imageUri) return false
        if (dateCreated != other.dateCreated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + imageUri.hashCode()
        result = 31 * result + dateCreated.hashCode()
        return result
    }
}
