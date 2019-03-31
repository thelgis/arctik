package com.thelgis.arctik.druidclient.adapter

import com.thelgis.arctik.druidclient.error.DruidClientException
import com.thelgis.arctik.druidclient.error.ErrorMessage
import com.thelgis.arctik.druidclient.payloads.*
import com.thelgis.arctik.druidclient.querybuilding.*

/*
 * The adapter contains all logic to go from the internal data model of the 'QueryBuilder' to a 'DruidRequestPayload'
 */

fun QueryBuilder.toRequest(_dataSource: String?) =
  DruidRequestPayload(
    dataSource = this.dataSource ?: _dataSource ?: throw DruidClientException(ErrorMessage.datasourceMissingError),
    queryType = when {
      dimensions.isEmpty() -> "timeseries"
      dimensions.size == 1 -> "topN"
      dimensions.size > 1 -> "groupBy"
      else -> throw IllegalStateException(ErrorMessage.dimensionsIllegalState)
    },
    dimension = when {
      dimensions.size == 1 -> dimensions[0]
      else -> null
    },
    dimensions = when {
      dimensions.size > 1 -> dimensions
      else -> null
    },
    threshold = threshold,
    granularity = granularity.druidStr,
    filter = whereOperation?.toFilterPayload(),
    context = ContextPayload(
      timeout = timeout
    ),
    intervals = if (interval != null) listOf(interval!!) else intervals,
    metric = MetricPayload(
      type = metricType
    ),
    aggregations = aggregators.map { it.toAggregationPayload() }
  )

fun WhereOperation.toFilterPayload(): FilterPayload =
  when (this) {
    is And ->
      FilterPayload(
        type = "and",
        fields = listOf(left.toFilterPayload(), right.toFilterPayload())
      )
    is Or ->
      FilterPayload(
        type = "or",
        fields = listOf(left.toFilterPayload(), right.toFilterPayload())
      )
    is DimensionOperation -> this.toFilterPayload()
  }


fun DimensionOperation.toFilterPayload() =
  when(dimensionOperator) {
    DimensionOperator.NULL ->
      FilterPayload(
        dimension = dimension,
        value = null,
        type = "selector"
      )
    DimensionOperator.NOT_NULL ->
      FilterPayload(
        type = "not",
        field = FilterPayload(
          dimension = dimension,
          value = null,
          type = "selector"
        )
      )
    DimensionOperator.EQUAL ->
      FilterPayload(
        type = "selector",
        dimension = dimension,
        value = value
      )
    DimensionOperator.NOT_EQUAL ->
      FilterPayload(
        type = "not",
        field = FilterPayload(
          dimension = dimension,
          value = value,
          type = "selector"
        )
      )
  }

  fun Aggregator.toAggregationPayload() =
    AggregationPayload(
      type = this.type,
      name = this.alias,
      fieldName = this.field,
      isInputHyperUnique = this.isInputHyperUnique,
      round = this.round
    )



