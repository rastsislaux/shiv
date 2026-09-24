package io.github.rastsislaux.shiv.core.application.hex

interface Command
interface Query
interface Result

object NoResult : Result

data class PageResult<T : Result>(
    val items: List<T>,
    val page: Int,
    val size: Int,
    val totalItems: Long,
    val totalPages: Int,
) : Result

interface InputPort<I, O : Result> {
    fun execute(input: I): O
}

interface CommandUseCase<C : Command, O : Result> : InputPort<C, O>

interface QueryUseCase<Q : Query, O : Result> : InputPort<Q, O>

interface TargetsResource<ID> {
    val resourceId: ID
}
