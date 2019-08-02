package arctik.druidclient.querybuilding

@DruidDSLMarker
class AggregationsBuilder {

  private var aggregations = mutableListOf<Aggregator>()

  // TODO add "as" for aliasing?

  fun count(field: String) = aggregations.add(Aggregator("count", field))

  fun longSum(field: String) = aggregations.add(Aggregator("longSum", field))
  fun doubleSum(field: String) = aggregations.add(Aggregator("doubleSum", field))
  fun floatSum(field: String) = aggregations.add(Aggregator("floatSum", field))

  fun longMin(field: String) = aggregations.add(Aggregator("longMin", field))
  fun longMax(field: String) = aggregations.add(Aggregator("longMax", field))
  fun doubleMin(field: String) = aggregations.add(Aggregator("doubleMin", field))
  fun doubleMax(field: String) = aggregations.add(Aggregator("doubleMax", field))
  fun floatMin(field: String) = aggregations.add(Aggregator("floatMin", field))
  fun floatMax(field: String) = aggregations.add(Aggregator("floatMax", field))

  fun longFirst(field: String) = aggregations.add(Aggregator("longFirst", field))
  fun longLast(field: String) = aggregations.add(Aggregator("longLast", field))
  fun doubleFirst(field: String) = aggregations.add(Aggregator("doubleFirst", field))
  fun doubleLast(field: String) = aggregations.add(Aggregator("doubleLast", field))
  fun floatFirst(field: String) = aggregations.add(Aggregator("floatFirst", field))
  fun floatLast(field: String) = aggregations.add(Aggregator("floatLast", field))
  fun stringFirst(field: String, maxStringBytes: Int = 1024, filterNullValues: Boolean = false) =
    aggregations.add(Aggregator("stringFirst", field))
  fun stringLast(field: String, maxStringBytes: Int = 1024, filterNullValues: Boolean = false) =
    aggregations.add(Aggregator("stringLast", field))

  // TODO Cardinality Aggregators
  // TODO Filtered Aggregator

  fun hyperUnique(field: String, isInputHyperUnique: Boolean = false, round: Boolean = false) =
    aggregations.add(
      Aggregator(
        "hyperUnique",
        field,
        isInputHyperUnique = isInputHyperUnique,
        round = round
      )
    )

  fun aggregations() = aggregations.toList()

}