package io.github.rastsislaux.shiv.platform.spring.web

import io.github.rastsislaux.shiv.core.application.hex.PageResult
import io.github.rastsislaux.shiv.core.application.hex.Result

data class PageDto<T>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalItems: Long,
    val totalPages: Int,
) {
    companion object {
        fun <I : Result, O> from(page: PageResult<I>, itemMapper: (I) -> O): PageDto<O> {
            return PageDto(
                items = page.items.map(itemMapper),
                page = page.page,
                size = page.size,
                totalItems = page.totalItems,
                totalPages = page.totalPages,
            )
        }
    }
}
