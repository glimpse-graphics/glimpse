package graphics.glimpse.types

/**
 * A common interface for vector implementations.
 */
interface Vec {

    /**
     * Returns a list of coordinates of this vector.
     */
    fun toList(): List<Float>

    /**
     * Returns an array of coordinates of this vector.
     */
    fun toFloatArray(): FloatArray
}
