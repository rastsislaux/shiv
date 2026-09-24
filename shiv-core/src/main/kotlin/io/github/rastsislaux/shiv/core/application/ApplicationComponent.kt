package io.github.rastsislaux.shiv.core.application

/**
 * Marks a class as an application-layer component.
 *
 * Application components contain application-level behavior such as
 * use-case orchestration, coordination of domain objects, and interaction
 * with output ports.
 *
 * This annotation is intentionally framework-agnostic. Platform integrations
 * may discover classes annotated with [ApplicationComponent] and register
 * them in their dependency injection container.
 *
 * For example:
 * - a Spring integration may register the class as a Spring bean;
 * - a CDI-based integration may register it as a managed CDI bean.
 *
 * The annotation does not imply any particular lifecycle, scope,
 * transactional behavior, or dependency injection implementation.
 * Those concerns are defined by the platform adapter.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ApplicationComponent
