/*
 * Copyright 2020 Slawomir Czerwinski
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
 *
 */

package graphics.glimpse.logging

import android.util.Log

/**
 * Default implementation of a [GlimpseLogger].
 */
actual class DefaultLogger(private val tag: String) : GlimpseLogger {

    /**
     * Logs a given [message] at a debug level.
     */
    override fun debug(message: String) {
        Log.d(tag, message)
    }

    /**
     * Logs a given [message] at a info level.
     */
    override fun info(message: String) {
        Log.i(tag, message)
    }

    /**
     * Logs a given [message] at a warning level.
     */
    override fun warn(message: String) {
        Log.w(tag, message)
    }

    /**
     * Logs a given [message] at an error level.
     */
    override fun error(message: String) {
        Log.e(tag, message)
    }

    /**
     * Logs a given [message] with a given [exception] at a debug level.
     */
    override fun debug(message: String, exception: Throwable) {
        Log.d(tag, message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at a info level.
     */
    override fun info(message: String, exception: Throwable) {
        Log.i(tag, message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at a warning level.
     */
    override fun warn(message: String, exception: Throwable) {
        Log.w(tag, message, exception)
    }

    /**
     * Logs a given [message] with a given [exception] at an error level.
     */
    override fun error(message: String, exception: Throwable) {
        Log.e(tag, message, exception)
    }
}
