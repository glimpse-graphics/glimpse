package graphics.glimpse.ui

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLException
import com.jogamp.opengl.GLProfile
import graphics.glimpse.logging.GlimpseLogger

object GLCapabilitiesFactory {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private val supportedProfiles = listOf(
        // Supported:
        GLProfile.GL2ES2,
        GLProfile.GL2GL3,
        GLProfile.GL3,
        GLProfile.GL4ES3,
        GLProfile.GL4,

        // Not confirmed:
        GLProfile.GLES2,
        GLProfile.GLES3,
        GLProfile.GL3bc,
        GLProfile.GL4bc,
    )

    fun create(): GLCapabilities {
        for (profileName in supportedProfiles) {
            logger.debug("Obtaining GLProfile '$profileName'")
            val profile: GLProfile? = findGLProfile(profileName)
            if (profile != null) {
                logger.debug("Obtaining GLCapabilities for $profile")
                val capabilities: GLCapabilities? = createGLCapabilities(profile)
                if (capabilities != null) return capabilities
            } else {
                logger.warn("No GLProfile '$profileName'")
            }
        }
        throw UnsupportedOperationException("Could not find any supported GLProfile")
    }

    private fun findGLProfile(profileName: String) = try {
        GLProfile.get(profileName)
    } catch (exception: GLException) {
        logger.error("Could not get GLProfile '$profileName'")
        null
    }

    private fun createGLCapabilities(profile: GLProfile?) = try {
        GLCapabilities(profile)
    } catch (exception: GLException) {
        logger.error("Could not get GLCapabilities for $profile")
        null
    }
}
