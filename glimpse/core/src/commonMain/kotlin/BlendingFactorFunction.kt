package graphics.glimpse

/**
 * Blending factor function.
 */
enum class BlendingFactorFunction {

    /**
     * Equal to zero.
     */
    ZERO,

    /**
     * Equal to one.
     */
    ONE,

    /**
     * Source color vector.
     */
    SOURCE_COLOR,

    /**
     * One minus source color vector.
     */
    ONE_MINUS_SOURCE_COLOR,

    /**
     * Destination color vector.
     */
    DESTINATION_COLOR,

    /**
     * One minus destination color vector.
     */
    ONE_MINUS_DESTINATION_COLOR,

    /**
     * Source alpha.
     */
    SOURCE_ALPHA,

    /**
     * One minus source alpha.
     */
    ONE_MINUS_SOURCE_ALPHA,

    /**
     * Destination alpha.
     */
    DESTINATION_ALPHA,

    /**
     * One minus destination alpha.
     */
    ONE_MINUS_DESTINATION_ALPHA,

    /**
     * Constant color vector.
     *
     * The constant color vector must be set separately, using [GlimpseAdapter.glBlendingColor].
     */
    CONSTANT_COLOR,

    /**
     * One minus constant color vector.
     *
     * The constant color vector must be set separately, using [GlimpseAdapter.glBlendingColor].
     */
    ONE_MINUS_CONSTANT_COLOR,

    /**
     * Constant alpha.
     *
     * The constant alpha value must be set separately, using [GlimpseAdapter.glBlendingColor].
     */
    CONSTANT_ALPHA,

    /**
     * One minus constant alpha.
     *
     * The constant alpha value must be set separately, using [GlimpseAdapter.glBlendingColor].
     */
    ONE_MINUS_CONSTANT_ALPHA
}
