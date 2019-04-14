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
  fun String.among(vararg values: String) = InOperation(this, values.toList())

  /** Negation of Druid 'in' operator */
  fun String.notAmong(vararg values: String) = InOperation(this, values.toList(), isNegation = true)

}