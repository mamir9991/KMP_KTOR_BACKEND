import com.example.db.tables.db.tables.BlogsTable
import db.tables.UsersTable
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.initDB() {
    val url = environment.config.property("database.url").getString()
    val host = environment.config.property("database.host").getString() ?: "localhost"
    val port = environment.config.property("database.port").getString() ?: 3306
    val databaseName = environment.config.property("database.name").getString() ?: "railway"
    val user = environment.config.property("database.user").getString() ?: "root"
    val password = environment.config.property("database.password").getString() ?: ""
    val driver = environment.config.property("database.driver").getString()?:""


    println("host $host")
    println("port $port")
    println("databaseName $databaseName")
    println("user $user")
    println("password $password")
    println("driver $driver")
    println("url $url")
    val db = Database.connect(
        url = url,
        user = user,
        driver = driver,
        password = password
    )

    transaction(db) {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(UsersTable, BlogsTable)
    }
}