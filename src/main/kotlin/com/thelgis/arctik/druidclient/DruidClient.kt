package com.thelgis.arctik.druidclient

import com.thelgis.arctik.druidclient.adapter.toRequest
import com.thelgis.arctik.druidclient.querybuilding.QueryBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

class DruidClient(
  private val host: String,
  private val port: String,
  private val dataSource: String? = null
) {

  private val httpClient = HttpClient(Apache) {
    install(JsonFeature) {
      serializer = GsonSerializer()
    }
  }

  suspend operator fun invoke(build: QueryBuilder.() -> Unit): String {

    val requestPayload = QueryBuilder()
      .apply(build)
      .toRequest(dataSource)

    println("***Query***")
    println(requestPayload) // TODO add logging and pretty print json
    println("******")

    val response = httpClient.use { // TODO not sure we should be using 'use' cause it closes the client every time

      httpClient.post<String>("http://$host:$port/druid/v2") {
        contentType(ContentType.Application.Json)
        body = requestPayload
      }

    }

    println("***Response***")
    println(response) // TODO add logging and pretty print json
    println("******")

    return response
  }


}