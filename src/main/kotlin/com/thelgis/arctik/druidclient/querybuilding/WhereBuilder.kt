package com.thelgis.arctik.druidclient.querybuilding

@DruidDSLMarker
class WhereBuilder {

  infix fun WhereOperation.and(other: WhereOperation) =
    And(this, other)

  infix fun WhereOperation.or(other: WhereOperation) =
    Or(this, other)

  infix fun String.eq(other: String) =
    DimensionOperation(this, DimensionOperator.EQUAL, other)

  infix fun String.eq(other: Int) =
    DimensionOperation(this, DimensionOperator.EQUAL, other.toString())

  infix fun String.neq(other: String) =
    DimensionOperation(this, DimensionOperator.NOT_EQUAL, other)

  infix fun String.neq(other: Int) =
    DimensionOperation(this, DimensionOperator.NOT_EQUAL, other.toString())

  val String.isNull
    get() = DimensionOperation(this, DimensionOperator.NULL)

  val String.isNotNull
    get() = DimensionOperation(this, DimensionOperator.NOT_NULL)

  /** Druid 'in' operator */
  fun String.among(vararg values: String) = InOperation(this, values.toList())

  /** Negation of Druid 'in' operator */
  fun String.notAmong(vararg values: String) = InOperation(this, values.toList(), isNegation = true)

}