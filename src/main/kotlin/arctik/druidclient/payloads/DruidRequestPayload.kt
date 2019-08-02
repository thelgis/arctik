package arctik.druidclient.payloads

data class DruidRequestPayload(
  val dataSource: String,
  val queryType: String,
  val threshold: Int,
  val granularity: String,
  val filter: FilterPayload? = null,
  val dimension: String?,          // used in topN
  val dimensions: List<String>?,   // used in groupBy
  val context: ContextPayload,
  val intervals: List<String>,
  val metric: MetricPayload,
  val aggregations: List<AggregationPayload>
)

data class FilterPayload(
  val type: String,
  val field: FilterPayload? = null,
  val fields: List<FilterPayload>? = null,
  val dimension: String? = null,
  val value: String? = null,
  val values: List<String>? = null,
  val lower: String? = null,
  val upper: String? = null,
  val lowerStrict: Boolean? = null,
  val upperStrict: Boolean? = null,
  val ordering: String? = null
)

data class ContextPayload(
  val timeout: Long,
  val queryId: String? = null
)

data class MetricPayload(
  val type: String
)

data class AggregationPayload(
  val type: String,
  val name: String,
  val fieldName: String,
  val isInputHyperUnique: Boolean?,
  val round: Boolean?,
  val maxStringBytes: Int?,
  val filterNullValues: Boolean?
)