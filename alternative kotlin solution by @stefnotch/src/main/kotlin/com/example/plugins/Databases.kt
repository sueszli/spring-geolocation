package com.example.plugins

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import kotlinx.serialization.Serializable
import kotlinx.coroutines.Dispatchers
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.serialization.SerialName

fun Application.configureDatabases() {
    val database = Database.connect(
            url = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
            user = "root",
            driver = "org.h2.Driver",
            password = ""
        )
    val locationService = LocationService(database)
    routing {
        // Create user
        post("/locations") {
            val location = call.receive<Location>()
            val id = locationService.create(location)
            call.respond(HttpStatusCode.Created, id)
        }
        // Read user
        get("/locations") {
            // type: LocationType?, area: LocationRectangle?, limit: Int?
            val type = call.request.queryParameters["type"]?.let { LocationType.valueOf(it.toUpperCasePreservingASCIIRules()) }
            val limit = call.request.queryParameters["limit"]?.toInt()
            // area from p1 and p2, both are coordinate pairs
            val area = call.request.queryParameters["p1"]?.let {
                val p2 = call.request.queryParameters["p2"]?.let { it } ?: throw IllegalArgumentException("p2 is required")
                val p1Points = it.split(",").map { it.toDouble() }
                val p2Points = p2.split(",").map { it.toDouble() }
                LocationRectangle(p1Points[0], p1Points[1], p2Points[0], p2Points[1])
            }
            val locations = locationService.search(type, area, limit)
            call.respond(HttpStatusCode.OK, locations)
        }
    }
}

@Serializable
enum class LocationType {
    @SerialName("premium")
    PREMIUM,
    @SerialName("standard")
    STANDARD
}

@Serializable
data class LocationRectangle(val lat1: Double, val lng1: Double, val lat2: Double, val lng2: Double)

@Serializable
data class Location(val lat: Double, val lng: Double, val name: String, val type: LocationType)
class LocationService(private val database: Database) {
    object Locations: Table() {
        val id = integer("id").autoIncrement()
        val name = varchar("name", length = 150)
        val lat = double("lat")
        val lng = double("lng")
        val type = enumerationByName("type", 50, LocationType::class)

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Locations)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

    suspend fun create(location: Location): Int = dbQuery {
        Locations.insert {
            it[name] = location.name
            it[lat] = location.lat
            it[lng] = location.lng
            it[type] = location.type
        }[Locations.id]
    }

    suspend fun search(type: LocationType?, area: LocationRectangle?, limit: Int?): List<Location> = dbQuery{
        val query = Locations.selectAll()
        if (type != null) {
            query.andWhere { Locations.type eq type }
        }
        if (area != null) {
            // TODO: Fix edge case around 180/-180 longitude
            query.andWhere { Locations.lat.between(area.lat1, area.lat2) }
            query.andWhere { Locations.lng.between(area.lng1, area.lng2) }
        }
        if (limit != null) {
            query.limit(limit)
        }
        query.orderBy(Locations.type to SortOrder.ASC)
        query.map { Location(it[Locations.lat], it[Locations.lng], it[Locations.name], it[Locations.type]) }
    }
}

