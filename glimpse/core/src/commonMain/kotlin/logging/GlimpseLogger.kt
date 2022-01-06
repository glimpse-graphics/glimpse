/*
 * Copyright 2020-2022 Slawomir Czerwinski
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

package graphics.glimpse.logging

/**
 * A common interface for a logger.
 */
interface GlimpseLogger {

    /**
     * Logs a given [message] at a debug level.
     */
    fun debug(message: String)

    /**
     * Logs a given [message] at a info level.
     */
    fun info(message: String)

    /**
     * Logs a given [message] at a warning level.
     */
    fun warn(message: String)

    /**
     * Logs a given [message] at an error level.
     */
    fun error(message: String)

    /**
     * Logs a given [message] with a given [exception] at a debug level.
     */
    fun debug(message: String, exception: Throwable)

    /**
     * Logs a given [message] with a given [exception] at a info level.
     */
    fun info(message: String, exception: Throwable)

    /**
     * Logs a given [message] with a given [exception] at a warning level.
     */
    fun warn(message: String, exception: Throwable)

    /**
     * Logs a given [message] with a given [exception] at an error level.
     */
    fun error(message: String, exception: Throwable)

    companion object {

        /**
         * Creates a logger for a given [object][obj].
         */
        fun create(obj: Any): GlimpseLogger = GlimpseLoggerFactory.getLogger(obj)
    }
}
