package io.github.rastsislaux.shiv.core.application.hex

import io.github.rastsislaux.shiv.core.application.Identifiable
import io.github.rastsislaux.shiv.core.domain.Readable
import io.github.rastsislaux.shiv.core.domain.Writeable

interface OutputPort

interface GetOutputPort<T, ID> : OutputPort
        where T : Readable,
              T : Identifiable<ID> {

    fun get(id: ID): T?
}

interface LookupOutputPort<T : Readable, K> : OutputPort {
    fun lookup(key: K): T?
}

interface SearchOutputPort<T : Readable, QUERY> : OutputPort {
    data class PageRequest(
        val page: Int,
        val size: Int,
    )

    data class Page<T>(
        val items: List<T>,
        val page: Int,
        val size: Int,
        val totalItems: Long,
        val totalPages: Int,
    )

    fun search(query: QUERY, page: PageRequest): Page<T>
}

interface CountOutputPort<T : Readable, QUERY> : OutputPort {
    fun count(query: QUERY): Long
}

interface ExistsOutputPort<T : Readable, QUERY> : OutputPort {
    fun exists(query: QUERY): Boolean
}

interface SaveOutputPort<T : Writeable> : OutputPort {
    fun save(value: T): T
}

interface DeleteOutputPort<T : Writeable> : OutputPort {
    fun delete(value: T)
}
