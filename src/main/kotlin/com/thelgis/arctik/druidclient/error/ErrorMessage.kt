package com.thelgis.arctik.druidclient.error

object ErrorMessage {

  const val dimensionModificationError =
    "Trying to modify dimensions more than once. Check calls of 'topN' and 'groupBy' in the 'QueryBuilder'. You can call only one of the two methods and the call must happen only once."

  const val datasourceMissingError =
    "A Datasource must be defined either when building the 'DruidClient' or when querying."

  const val dimensionsIllegalState =
    "Illegal state of dimensions"

}