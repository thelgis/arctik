package com.thelgis.arctik.druidclient.querybuilding

@DruidDSLMarker
class WhereBuilder {

  infix fun WhereOperation.and(other: WhereOperation) =
    And(this, other)

  infix fun WhereOperation.or(other: WhereOperation) =
    Or(this, other)

  infix fun String.eq(other: String) =
    SelectorOperation(this, SelectorOperator.EQUAL, other)

  infix fun String.eq(other: Int) =
    SelectorOperation(this, SelectorOperator.EQUAL, other.toString())

  infix fun String.neq(other: String) =
    SelectorOperation(this, SelectorOperator.NOT_EQUAL, other)

  infix fun String.neq(other: Int) =
    SelectorOperation(this, SelectorOperator.NOT_EQUAL, other.toString())

  val String.isNull
    get() = SelectorOperation(this, SelectorOperator.NULL)

  val String.isNotNull
    get() = SelectorOperation(this, SelectorOperator.NOT_NULL)

  /** Druid 'in' operator */
  fun String.among(vararg values: String) =
    InOperation(this, values.toList())

  /** Negation of Druid 'in' operator */
  fun String.notAmong(vararg values: String) =
    InOperation(this, values.toList(), isNegation = true)

  infix fun String.lt(other: String) =
    BoundOperation(
      this,
      upperBound = Bound(other, strict = true)
    )

  infix fun String.lt(other: Long) =
    BoundOperation(
      this,
      upperBound = Bound(other.toString(), strict = true),
      ordering = Ordering.ALHANUMERIC
    )

  infix fun String.lte(other: String) =
    BoundOperation(
      this,
      upperBound = Bound(other)
    )

  infix fun String.lte(other: Long) =
    BoundOperation(
      this,
      upperBound = Bound(other.toString()),
      ordering = Ordering.ALHANUMERIC
    )

  infix fun String.gt(other: String) =
    BoundOperation(
      this,
      lowerBound = Bound(other, strict = true)
    )

  infix fun String.gt(other: Long) =
    BoundOperation(
      this,
      lowerBound = Bound(other.toString(), strict = true),
      ordering = Ordering.ALHANUMERIC
    )

  infix fun String.gte(other: String) =
    BoundOperation(
      this,
      lowerBound = Bound(other)
    )

  infix fun String.gte(other: Long) =
    BoundOperation(
      this,
      lowerBound = Bound(other.toString()),
      ordering = Ordering.ALHANUMERIC
    )

  fun String.between(lowerBound: String, upperBound: String) =
    BoundOperation(
      this,
      lowerBound = Bound(lowerBound),
      upperBound = Bound(upperBound)
    )

  fun String.between(lowerBound: Long, upperBound: Long) =
    BoundOperation(
      this,
      lowerBound = Bound(lowerBound.toString()),
      upperBound = Bound(upperBound.toString()),
      ordering = Ordering.ALHANUMERIC
    )

}