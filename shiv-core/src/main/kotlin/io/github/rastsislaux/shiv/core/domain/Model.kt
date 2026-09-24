package io.github.rastsislaux.shiv.core.domain

/**
 * Marker interface for an entity, that can be written.
 */
interface Writeable

/**
 * Marker interface for an entity, that can be read.
 */
interface Readable

/**
 * Marker interface for an entity, representing an internal concept to this bounded context.
 */
interface InternalConcept

/**
 * Marker interface for an entity, representing an external concept to this bounded context.
 */
interface ExternalConcept

/**
 * Marker interface for an entity, which is an aggregate root. For an aggregate root, this
 * bounded context is authoritative for this entity, which means that reads / writes are local
 * and authoritative.
 */
interface AggregateRoot : Writeable, Readable, InternalConcept

/**
 * Marker interface for an entity, which is a value object.
 */
interface ValueObject : InternalConcept

/**
 * Marker interface for an entity, which is a proxy. For a proxy, this bounded context is
 * non-authoritative - reads / writes are proxied to authoritative bounded context, providing
 * authoritative reads / writes.
 */
interface Proxy : Writeable, Readable, ExternalConcept

/**
 * Marker interface for an entity, which is a replica. For a replica, this bounded context is
 * non-authoritative - reads are local and eventually consistent, non-authoritative; writes are
 * not supported.
 */
interface Replica : Readable, ExternalConcept

/**
 * Marker interface for an entity, which is a read model. Read model does not represent a business
 * entity, but operationally useful views / aggregations / abstractions.
 */
interface ReadModel : Readable

/**
 * Marker interface for an entity, which is a replicated proxy. For a replicated proxy, this bounded
 * context is non-authoritative - reads are local and eventually consistent, non-authoritative;
 * writes are proxied to authoritative bounded context and are authoritative.
 */
interface ReplicatedProxy : Writeable, Readable, ExternalConcept
