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

package graphics.glimpse.geom

import graphics.glimpse.logging.GlimpseLogger
import graphics.glimpse.types.Vec3
import kotlin.reflect.KClass

internal class Curve3BuilderImpl<T>(
    private val type: KClass<T>
) : Curve3.Builder<T> where T : Number, T : Comparable<T> {

    private val logger: GlimpseLogger = GlimpseLogger.create(this)

    private var curveType: CurveType? = null
    private var controlPoints: List<Vec3<T>> = emptyList()
    private var knots: List<T> = emptyList()

    override fun ofType(curveType: CurveType): Curve3.Builder<T> {
        this.curveType = curveType
        return this
    }

    override fun withControlPoints(controlPoints: List<Vec3<T>>): Curve3.Builder<T> {
        this.controlPoints = controlPoints
        return this
    }

    override fun withControlPoints(vararg controlPoints: Vec3<T>): Curve3.Builder<T> {
        this.controlPoints = controlPoints.toList()
        return this
    }

    override fun withKnots(knots: List<T>): Curve3.Builder<T> {
        this.knots = knots
        return this
    }

    override fun withKnots(vararg knots: T): Curve3.Builder<T> {
        this.knots = knots.toList()
        return this
    }

    override fun build(): Curve3<T> =
        when (requireNotNull(curveType) { "Curve type must be specified" }) {
            CurveType.BEZIER -> buildBezierCurve()
            CurveType.B_SPLINE -> buildBSplineCurve()
        }

    private fun buildBezierCurve(): Curve3<T> {
        if (knots.isNotEmpty()) {
            logger.warn("Bezier curves do not require knots vector")
        }
        return BezierCurve3(
            controlPoints = this.controlPoints,
            type = this.type
        )
    }

    private fun buildBSplineCurve(): Curve3<T> =
        BSplineCurve3(
            controlPoints = this.controlPoints,
            knots = this.knots,
            type = this.type
        )
}
