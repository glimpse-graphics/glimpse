/*
 * Copyright 2020-2021 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.ui

import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLException
import com.jogamp.opengl.GLProfile
import graphics.glimpse.logging.GlimpseLogger

internal object GLCapabilitiesFactory {

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
            logger.debug(message = "Obtaining GLProfile '$profileName'")
            val profile: GLProfile? = findGLProfile(profileName)
            if (profile != null) {
                logger.debug(message = "Obtaining GLCapabilities for $profile")
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
