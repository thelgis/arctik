package arctik.druidclient

import arctik.druidclient.adapter.toRequest
import arctik.druidclient.querybuilding.QueryBuilder
import com.google.gson.GsonBuilder
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

// TODO Add instructions in READ-ME about sequential and parallel requests https://ktor.io/clients/http-client.html
class DruidClient(
  private val host: String,
  private val port: String,
  private val dataSource: String? = null
) {

  private val gson = GsonBuilder().setPrettyPrinting().create()

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
    println(gson.toJson(requestPayload)) // TODO add logging
    println("******")

    val response = httpClient.use { // TODO not sure we should be using 'use' cause it closes the client every time

      httpClient.post<String>("http://$host:$port/druid/v2") {
        contentType(ContentType.Application.Json)
        body = requestPayload
      }

    }

    println("***Response***")
    println(response) // TODO add logging and include error messages from Druid when status code is not 200
    println("******")

    return response
  }


}

// TODO (serialization changes)
//inline fun <reified T> String.to() = GsonBuilder().create().fromJson(this, T::class.java)