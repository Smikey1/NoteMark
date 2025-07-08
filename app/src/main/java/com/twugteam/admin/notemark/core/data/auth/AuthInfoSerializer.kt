package com.twugteam.admin.notemark.core.data.auth

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import timber.log.Timber
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

object AuthInfoSerializer : Serializer<AuthInfoSerializable?> {

    //when file empty, missing or unreadable will fallback defaultValue
    override val defaultValue: AuthInfoSerializable? = null

    override suspend fun readFrom(input: InputStream): AuthInfoSerializable? {
        Timber.tag("MyTag").d("readFrom")
        val encryptedBytes = withContext(Dispatchers.IO) {
            input.use {
                it.readBytes()
            }
        }
        val encryptedBytesDecoded = Base64.getDecoder().decode(encryptedBytes)
        val decryptedBytes = Crypto.decrypt(encryptedBytesDecoded)
        val decodedJsonString = decryptedBytes?.decodeToString()
        return decodedJsonString?.let { Json.decodeFromString(it) }
    }

    override suspend fun writeTo(t: AuthInfoSerializable?, output: OutputStream) {
        Timber.tag("MyTag").d("writeTo")
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encryptedBytes = Crypto.encrypt(bytes)
        val encryptedBytesBase64 = Base64.getEncoder().encodeToString(encryptedBytes)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(encryptedBytesBase64.toByteArray())
            }
        }
    }
}
