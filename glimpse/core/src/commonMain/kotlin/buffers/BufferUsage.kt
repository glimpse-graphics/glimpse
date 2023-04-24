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

package graphics.glimpse.buffers

/**
 * Buffer usage strategy.
 */
enum class BufferUsage {

    /**
     * One-time use buffer.
     *
     * The buffer will be modified once and drawn at most a few times.
     */
    STREAM_DRAW,

    /**
     * Static buffer contents.
     *
     * The buffer will be modified once and drawn multiple times.
     */
    STATIC_DRAW,

    /**
     * Dynamic buffer contents.
     *
     * The buffer will be modified and drawn multiple times.
     */
    DYNAMIC_DRAW
}
