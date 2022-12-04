package com.cbengineer.onitamaai

import kotlin.math.abs

data class Point(
  val x: Int,
  val y: Int,
) {
  fun distanceTo(other : Point): Int {
    return abs(other.x - this.x) + abs(other.y - this.y)
  }
}
