```
  ____  ____      __ ______  ____  __  _ 
 /    ||    \    /  ]      ||    ||  |/ ]
|  o  ||  D  )  /  /|      | |  | |  ' / 
|     ||    /  /  / |_|  |_| |  | |    \ 
|  _  ||    \ /   \_  |  |   |  | |     \
|  |  ||  .  \\     | |  |   |  | |  .  |
|__|__||__|\_| \____| |__|  |____||__|\_|
                                         
```

**Arctik** is a library to facilitate querying [Druid](http://druid.io/) analytics database through 
[Kotlin](https://kotlinlang.org/). 

The advantages of using the library are the following: 
* Write the queries in a type safe DSL instead of the default Druid query language which is JSON based. 
* Map the responses directly to data classes. (work-in-progress)
* Query Druid in a non-blocking Kotlin idiomatic way by using 
[Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).

_The library is under heavy development. There are a log of missing features and the API could change.
Feel free to speed-up the release of the first version by contributing._

# Example query 

Suppose you have a Druid schema that contains events with the **dimensions** `Gender`, `Country`, `PhoneType`. 
And you have a **metric** `count` that keeps the number of events that the system received.    

| Timestamp             | Gender  | Country | PhoneType |  
| --------------------- | ------- | ------- | --------- |
| 2019-02-15T00:00:00Z  |  Male   | US      | Android   | 
| 2019-02-16T00:00:00Z  |  Female | GB      |           |
| 2019-03-01T00:00:00Z  |  Female | GR      | iPhone    |
| 2019-03-01T00:00:00Z  |  Male   | US      |           |
| 2019-03-01T00:00:00Z  |  Male   | US      | Android   |

An sample query could be the following: 

_"How many events did I get in the period `2019-02-15` to `2019-03-02` from the `US` and `GB` where the `PhoneType` was 
known (not null), grouped by `Country` and `PhoneType`."_

The above query could be expressed in JSON with the default Druid query language like this: 

```json
{
  "intervals": [
    "2019-02-15T00:00:00.000Z/2019-03-02T00:00:00.000Z"
  ],
  "granularity": "all",
  "context": {
    "timeout": 30000
  },
  "queryType": "groupBy",
  "dataSource": "your-datasource",
  "aggregations": [
    {
      "type": "doubleSum",
      "name": "sum",
      "fieldName": "count"
    }
  ],
  "dimensions": [
    "PhoneType",
    "Country"
  ],
  "filter": {
    "type": "and",
    "fields": [
      {
        "type": "or",
        "fields": [
          {
            "type": "selector",
            "dimension": "Country",
            "value": "US"
          },
          {
            "type": "selector",
            "dimension": "Country",
            "value": "GB"
          }
        ]
      },
      {
        "type": "or",
        "fields": [
          {
            "type": "selector",
            "dimension": "Gender",
            "value": "Female"
          },
          {
            "type": "selector",
            "dimension": "Gender",
            "value": "Male"
          }
        ]
      },
      {
        "type": "not",
        "field": {
          "type": "selector",
          "dimension": "PhoneType",
          "value": null
        }
      }
    ]
  }
}
```

Using the Arctik DSL this query could be expressed like that: 

```kotlin

val druidClient = DruidClient("127.0.0.1", "8082", "your-datasource")

  runBlocking { // used only as a coroutine context for the example, usual usage should be non blocking 

    val results = 
        druidClient {
          interval = "2019-02-15/2019-03-02"
          groupBy("PhoneType", "Country")
          where {
            ("PhoneType".isNotNull and (("Country" eq "US") or ("Country" eq "GB"))) and
            ("Gender" eq "Male" or ("Gender" eq "Female"))
          }
          aggregate {
            doubleSum("count")
          }
        }
    
  }

```

# Gradle instructions 

To clean the project: `./gradlew clean`

To build the project: `./gradlew build`

To run unit tests: `./gradlew test`

# Supported / not-supported features

## Supported: 
- Queries
    - Timeseries 
    - TopN
    - GroupBy
- Filters
    - Interval
    - Selector
    - And
    - Or
    - Null
    - NotNull
    - In (renamed as 'among')
    - NotIn (renamed as 'notAmong')
    - Bound
- Aggregations
    - count 
    - doubleSum
    - floatSum 
    - longMin 
    - longMax 
    - doubleMin 
    - doubleMax 
    - floatMin 
    - floatMax 
    - hyperUnique 
- Simple Granularity 
    - Simple (all)

## To be supported: 
- Map response to data class
- Extraction functions   
- Filters 
    - Regex
    - Search 
- Aggregations 
    - first/last
    - cardinality 
    - filtered
- Post Aggregations
- Granularity 
    - Simple (none, second, minute, fifteen_minute, thirty_minute, hour, day, week, month, quarter and year)
    - Duration
    - Period
- Geo queries  

    
# External Links 

* [Druid official website](http://druid.io/)
* [Druid architecture blog post](https://anskarl.github.io/post/2019/druid-part-1/)
* [Druidry](https://github.com/zapr-oss/druidry) (similar library for Java)
* [Scruid](https://github.com/ing-bank/scruid) (similar library for Scala)       
