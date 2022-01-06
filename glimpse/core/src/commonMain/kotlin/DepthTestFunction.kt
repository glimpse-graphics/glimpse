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

package graphics.glimpse

/**
 * Depth test function.
 */
enum class DepthTestFunction(val isDepthTestEnabled: Boolean = true) {

    /**
     * Depth test is disabled.
     */
    DISABLED(isDepthTestEnabled = false),

    /**
     * Never passes.
     */
    NEVER,

    /**
     * Passes if the new depth value is less than the current depth value.
     */
    LESS,

    /**
     * Passes if the new depth value is equal to the current depth value.
     */
    EQUAL,

    /**
     * Passes if the new depth value is less than or equal to the current depth value.
     */
    LESS_OR_EQUAL,

    /**
     * Passes if the new depth value is greater than the current depth value.
     */
    GREATER,

    /**
     * Passes if the new depth value is not equal to the current depth value.
     */
    NOT_EQUAL,

    /**
     * Passes if the new depth value is greater than or equal to the current depth value.
     */
    GREATER_OR_EQUAL,

    /**
     * Always passes.
     */
    ALWAYS
}
