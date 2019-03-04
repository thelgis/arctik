package com.thelgis.arctik.druidclient.payloads

data class DruidRequestPayload(
  val dataSource: String,
  val queryType: String,
  val threshold: Int,
  val granularity: String,
  val filter: FilterPayload,
  val dimension: String,
  val context: ContextPayload,
  val intervals: List<String>,
  val metric: MetricPayload,
  val aggregations: List<AggregationPayload>
)

data class FilterPayload(
  val type: String,
  val fields: List<FieldPayload>
)

data class FieldPayload(
  val type: String,
  val dimension: String,
  val value: String
)

data class ContextPayload(
  val timeout: Int,
  val queryId: String? = null
)

data class MetricPayload(
  val type: String
)

data class AggregationPayload(
  val type: String,
  val name: String,
  val fieldName: String
)
