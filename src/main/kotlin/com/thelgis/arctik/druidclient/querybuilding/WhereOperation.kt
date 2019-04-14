package com.thelgis.arctik.druidclient.querybuilding

sealed class WhereOperation

sealed class BooleanOperation(
  val left: WhereOperation,
  val right: WhereOperation,
  val booleanOperator: BooleanOperator
): WhereOperation()

class And(
  left: WhereOperation,
  right: WhereOperation
): BooleanOperation(left, right, BooleanOperator.AND)

class Or(
  left: WhereOperation,
  right: WhereOperation
): BooleanOperation(left, right, BooleanOperator.OR)

enum class BooleanOperator {
  AND,
  OR
}

class SelectorOperation(
  val dimension: String,
  val selectorOperator: SelectorOperator,
  val value: String? = null
): WhereOperation()

enum class SelectorOperator {
  EQUAL,
  NOT_EQUAL,
  NULL,
  NOT_NULL
}

class InOperation(
  val dimension: String,
  val values: List<String>,
  val isNegation: Boolean = false
): WhereOperation()