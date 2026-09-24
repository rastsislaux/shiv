package io.github.rastsislaux.shiv.core.application.hex

object HexagonalMapper {
    fun <I, O : Result> mapToResult(
        page: SearchOutputPort.Page<I>,
        itemMapper: (I) -> O
    ): PageResult<O> {
        return PageResult(
            items = page.items.map(itemMapper),
            page = page.page,
            size = page.size,
            totalItems = page.totalItems,
            totalPages = page.totalPages,
        )
    }
}
