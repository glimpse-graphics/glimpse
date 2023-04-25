/*
 * Copyright 2020-2023 Glimpse Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package graphics.glimpse.logging

import org.slf4j.Logger

/**
 * Default implementation of a [GlimpseLogger].
 */
internal actual class DefaultLogger(private val logger: Logger) : GlimpseLogger {

    /**
     * Logs a given [message] at a debug level.
     */
    override fun debug(message: String) {
        for (line in message.lines()) {
            logger.debug(line)
        }
    }

    /**
     * Logs a given [message] at a info level.
     */
    override fun info(message: String) {
        for (line in message.lines()) {
            logger.info(line)
        }
    }

    /**
     * Logs a given [message] at a warning level.
     */
    override fun warn(message: String) {
        for (line in message.lines()) {
            logger.warn(line)
        }
    }

    /**
     * Logs a given [message] at an error level.
     */
    override fun error(message: String) {
        for (line in message.lines()) {
            logger.error(line)
        }
    }

    /**
     * Logs a given [message] with a given [exception] at a debug level.
     */
    override fun debug(message: String, exception: Throwable) {
        debug(message)
        logger.debug(exception.message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at a info level.
     */
    override fun info(message: String, exception: Throwable) {
        info(message)
        logger.info(exception.message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at a warning level.
     */
    override fun warn(message: String, exception: Throwable) {
        warn(message)
        logger.warn(exception.message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at an error level.
     */
    override fun error(message: String, exception: Throwable) {
        error(message)
        logger.error(exception.message, exception)
    }
}
