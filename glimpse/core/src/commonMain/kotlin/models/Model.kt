package graphics.glimpse.models

import graphics.glimpse.meshes.Mesh
import graphics.glimpse.types.Mat4

/**
 * A model.
 */
interface Model {

    /**
     * Mesh to be drawn.
     */
    val mesh: Mesh

    /**
     * Model transformation matrix.
     */
    val modelMatrix: Mat4
}
