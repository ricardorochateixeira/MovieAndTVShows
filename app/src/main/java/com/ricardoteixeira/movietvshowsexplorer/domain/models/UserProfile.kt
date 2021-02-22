package com.ricardoteixeira.movietvshowsexplorer.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile (
    val id: String,
    val name: String,
    val email: String,
    val imageUri: String,
    val dateCreated: String
): Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfile

        if (id != other.id) return false
        if (name != other.name) return false
        if (imageUri != other.imageUri) return false
        if (dateCreated != other.dateCreated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUri.hashCode()
        result = 31 * result + dateCreated.hashCode()
        return result
    }
}