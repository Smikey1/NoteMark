package com.twugteam.admin.notemark.core.domain.util

import java.util.UUID

object UUID {
    fun new(): UUID = UUID.randomUUID()
    fun toString(uuid: UUID): String = uuid.toString()
    fun fromString(value: String): UUID = UUID.fromString(value)
}