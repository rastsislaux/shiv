package io.github.rastsislaux.io.github.rastsislaux.shiv.platform.spring.jpa

import io.github.rastsislaux.shiv.core.application.Identifiable
import io.github.rastsislaux.shiv.core.application.hex.DeleteOutputPort
import io.github.rastsislaux.shiv.core.application.hex.GetOutputPort
import io.github.rastsislaux.shiv.core.application.hex.SaveOutputPort
import io.github.rastsislaux.shiv.core.application.hex.SearchOutputPort
import io.github.rastsislaux.shiv.core.domain.Readable
import io.github.rastsislaux.shiv.core.domain.Writeable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.Repository
import kotlin.jvm.optionals.getOrNull

class JpaGetOutputAdapter<T, ID, E : Any, EID : Any>(
    private val repository: CrudRepository<E, EID>,
    private val idMapper: (ID) -> EID,
    private val entityMapper: (E) -> T,
) : GetOutputPort<T, ID> where T : Readable, T : Identifiable<ID> {
    override fun get(id: ID): T? = repository.findById(idMapper(id))
        .map { entityMapper(it) }
        .getOrNull()
}

class JpaSaveOutputAdapter<T : Writeable, E : Any>(
    private val repository: CrudRepository<E, *>,
    private val toEntityMapper: (T) -> E,
    private val toDomainMapper: (E) -> T,
) : SaveOutputPort<T> {
    override fun save(value: T): T {
        val result = repository.save(toEntityMapper(value))
        return toDomainMapper(result)
    }
}

class JpaSearchOutputAdapter<T : Readable, QUERY, E : Any, R : Repository<E, *>>(
    private val repository: R,
    private val toDomainMapper: (E) -> T,
    private val search: (R, QUERY, Pageable) -> Page<E>,
) : SearchOutputPort<T, QUERY> {
    override fun search(
        query: QUERY,
        page: SearchOutputPort.PageRequest
    ): SearchOutputPort.Page<T> {
        val pageRequest = PageRequest.of(page.page, page.size)
        val result = search(repository, query, pageRequest)
        return SearchOutputPort.Page(
            items = result.content.map { toDomainMapper(it) },
            page = result.pageable.pageNumber,
            size = result.pageable.pageSize,
            totalItems = result.totalElements,
            totalPages = result.totalPages,
        )
    }
}

class JpaHardDeleteOutputAdapter<T, ID, EID : Any>(
    private val repository: CrudRepository<*, EID>,
    private val idMapper: (ID) -> EID,
) : DeleteOutputPort<T> where T : Writeable, T : Identifiable<ID> {
    override fun delete(value: T) {
        repository.deleteById(idMapper(value.id))
    }
}
