package com.jerry.common.r2dbc

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.domain.Persistable
import java.time.LocalDateTime

abstract class AbstractBaseEntity(
    @Id
    private var id: Long = 0
) : Persistable<Long> {
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    lateinit var createdAt: LocalDateTime

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @LastModifiedDate
    lateinit var modifiedAt: LocalDateTime

    override fun getId(): Long = id
    override fun isNew(): Boolean = getId() == 0L

    fun setId(id: Long) { this.id = id }
}
