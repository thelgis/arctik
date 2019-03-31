package com.thelgis.arctik.druidclient.querybuilding

import com.thelgis.arctik.druidclient.error.DruidQueryException
import com.thelgis.arctik.druidclient.error.ErrorMessage
import kotlin.properties.Delegates

@DruidDSLMarker
class QueryBuilder(
  var dataSource: String? = null,
  var interval: String? = null,
  var intervals: List<String> = emptyList(),
  var threshold: Int = 1_000,
  var granularity: Granularity = Granularity.ALL,
  var timeout: Long = 30_000,
  var metricType: String = "alphaNumeric", // TODO add support for other types
  var whereOperation: WhereOperation? = null,
  var aggregators: List<Aggregator> = emptyList()
) {

  internal var dimensions: List<String> by Delegates.observable(emptyList()) {
    // the only valid transition is to go from an empty list to a non-empty list
    _, old, _ -> if (!old.isEmpty()) throw DruidQueryException(ErrorMessage.dimensionModificationError)
  }

  fun topN(dimension: String) {
    dimensions = listOf(dimension)
  }

  fun groupBy(vararg dimensions: String) {
    this.dimensions = listOf(*dimensions)
  }

  fun where(build: WhereBuilder.() -> WhereOperation) {
    whereOperation = WhereBuilder().build()
  }

  fun aggregate(build: AggregationsBuilder.() -> Unit) {
    val aggregationsBuilder = AggregationsBuilder()
    aggregationsBuilder.build()
    aggregators = aggregationsBuilder.aggregations()
  }

}

enum class Granularity(val druidStr: String) { // TODO add support for other granularities
  ALL("all")
//  DAY("")
}