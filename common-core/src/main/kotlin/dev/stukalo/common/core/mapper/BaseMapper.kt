package dev.stukalo.common.core.mapper

/**
 * A generic interface that defines methods for mapping between two different types (`In` and `Out`).
 *
 * @param In The input type (source model).
 * @param Out The output type (target model).
 *
 * This interface provides methods to map individual objects (`mapTo`, `mapFrom`)
 * as well as lists of objects (`mapListTo`, `mapListFrom`).
 *
 * The `mapTo` and `mapFrom` methods are expected to be implemented in subclasses or throw a `NotImplementedError`.
 */
interface BaseMapper<In : Any, Out : Any> {

    /**
     * Maps an input model of type [In] to an output model of type [Out].
     *
     * This function should be implemented by subclasses to define custom mapping logic.
     * By default, it throws a `NotImplementedError`.
     *
     * @param model The input model of type [In] to be mapped.
     * @return A mapped output model of type [Out].
     * @throws NotImplementedError If not overridden by a subclass.
     */
    fun mapTo(model: In): Out =
        throw NotImplementedError(
            "${this::class.java} function mapTo function for class ${model::class.java} is not implemented",
        )

    /**
     * Maps an output model of type [Out] to an input model of type [In].
     *
     * This function should be implemented by subclasses to define custom mapping logic.
     * By default, it throws a `NotImplementedError`.
     *
     * @param model The output model of type [Out] to be mapped.
     * @return A mapped input model of type [In].
     * @throws NotImplementedError If not overridden by a subclass.
     */
    fun mapFrom(model: Out): In =
        throw NotImplementedError(
            "${this::class.java} function mapFrom function for class ${model::class.java} is not implemented",
        )

    /**
     * Maps a list of input models of type [In] to a list of output models of type [Out].
     *
     * This uses the [mapTo] function to map each element in the list.
     *
     * @param model A list of input models of type [In].
     * @return A list of output models of type [Out] after mapping.
     */
    fun mapListTo(model: List<In>): List<Out> = model.map(::mapTo)

    /**
     * Maps a list of output models of type [Out] to a list of input models of type [In].
     *
     * This uses the [mapFrom] function to map each element in the list.
     *
     * @param model A list of output models of type [Out].
     * @return A list of input models of type [In] after mapping.
     */
    fun mapListFrom(model: List<Out>): List<In> = model.map(::mapFrom)
}
