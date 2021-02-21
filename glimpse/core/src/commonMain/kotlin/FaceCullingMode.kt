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
 *
 */

package graphics.glimpse

/**
 * Face culling mode.
 */
enum class FaceCullingMode(val isFaceCullingEnabled: Boolean = true) {

    /**
     * Face culling is disabled.
     */
    DISABLED(isFaceCullingEnabled = false),

    /**
     * Front-facing facets are culled.
     */
    FRONT,

    /**
     * Back-facing facets are culled.
     */
    BACK,

    /**
     * Both front- and back-facing facets are culled.
     */
    FRONT_AND_BACK
}
