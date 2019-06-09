package arctik.druidclient.querybuilding

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

data class SelectorOperation(
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

data class BoundOperation(
  val dimension: String,
  val lowerBound: Bound? = null,
  val upperBound: Bound? = null,
  val ordering: Ordering = Ordering.LEXICOGRAPHIC
): WhereOperation()

infix fun BoundOperation.withOrdering(ordering: Ordering) =
  this.copy(ordering = ordering)

data class Bound(
  val value: String,
  val strict: Boolean = false
  // TODO Extraction Function
)

enum class Ordering {
  LEXICOGRAPHIC,
  ALHANUMERIC,
  NUMERIC,
  STRLEN,
  VERSION
}

data class InOperation(
  val dimension: String,
  val values: List<String>,
  val isNegation: Boolean = false
): WhereOperation()