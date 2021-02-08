package graphics.glimpse.types

import java.awt.Color
import kotlin.test.Test
import kotlin.test.assertEquals

@Suppress("MagicNumber")
class ColorsTest {

    @Test
    fun `GIVEN color, WHEN Vec3, THEN return Vec3 for that color`() {
        val color = Color(0.1f, 0.2f, 0.3f, 0.4f)

        val result = Vec3(color)

        assertEquals(Vec3(x = 0.1f, y = 0.2f, z = 0.3f), result)
    }

    @Test
    fun `GIVEN color, WHEN Vec4, THEN return Vec4 for that color`() {
        val color = Color(0.1f, 0.2f, 0.3f, 0.4f)

        val result = Vec4(color)

        assertEquals(Vec4(x = 0.1f, y = 0.2f, z = 0.3f, w = 0.4f), result)
    }
}
