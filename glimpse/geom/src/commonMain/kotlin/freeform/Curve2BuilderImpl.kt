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

package graphics.glimpse.geom.freeform

import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.types.Vec2
import kotlin.reflect.KClass

internal class Curve2BuilderImpl<T>(
    private val type: KClass<T>
) : Curve2.Builder<T> where T : Number, T : Comparable<T> {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var freeformType: FreeformType? = null
    private var controlPoints: List<Vec2<T>> = emptyList()
    private var knots: List<T> = emptyList()

    override fun ofType(freeformType: FreeformType): Curve2.Builder<T> {
        this.freeformType = freeformType
        return this
    }

    override fun withControlPoints(controlPoints: List<Vec2<T>>): Curve2.Builder<T> {
        this.controlPoints = controlPoints
        return this
    }

    override fun withControlPoints(vararg controlPoints: Vec2<T>): Curve2.Builder<T> {
        this.controlPoints = controlPoints.toList()
        return this
    }

    override fun withKnots(knots: List<T>): Curve2.Builder<T> {
        this.knots = knots
        return this
    }

    override fun withKnots(vararg knots: T): Curve2.Builder<T> {
        this.knots = knots.toList()
        return this
    }

    override fun build(): Curve2<T> =
        when (requireNotNull(freeformType) { "Curve type must be specified" }) {
            FreeformType.BEZIER -> buildBezierCurve()
            FreeformType.B_SPLINE -> buildBSplineCurve()
        }

    private fun buildBezierCurve(): Curve2<T> {
        if (knots.isNotEmpty()) {
            logger.warn("Bezier curves do not require knots vector")
        }
        return BezierCurve2(
            controlPoints = this.controlPoints,
            type = this.type
        )
    }

    private fun buildBSplineCurve(): Curve2<T> =
        BSplineCurve2(
            controlPoints = this.controlPoints,
            knots = this.knots,
            type = this.type
        )
}
