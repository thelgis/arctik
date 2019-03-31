package com.thelgis.arctik.druidclient.querybuilding

data class Aggregator(
  val type: String,
  val field: String,
  val alias: String = field,
  val isInputHyperUnique: Boolean? = null,  // for 'hyperUnique' aggregator
  val round: Boolean? = null                // for 'hyperUnique', 'cardinality' aggregators
)