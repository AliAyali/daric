package com.aliayali.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.aliayali.datastore.proto.UserPreferences
import java.io.InputStream
import java.io.OutputStream

object UserPreferencesSerializer : Serializer<UserPreferences> {

    override val defaultValue: UserPreferences =
        UserPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): UserPreferences {
        try {
            return UserPreferences.parseFrom(input)
        } catch (exception: Exception) {
            throw CorruptionException(
                "Cannot read UserPreferences.",
                exception
            )
        }
    }

    override suspend fun writeTo(
        t: UserPreferences,
        output: OutputStream,
    ) {
        t.writeTo(output)
    }
}