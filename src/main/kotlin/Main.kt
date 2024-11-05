package org.example

import org.h2.jdbcx.JdbcDataSource
import org.sql2o.Sql2o
import java.sql.DriverManager
import javax.sql.DataSource

annotation class NoArg

@NoArg
data class User(val id: Int, val name: String)

fun main() {
    val jdbcUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
    val source: DataSource = JdbcDataSource().apply {
        setUrl(jdbcUrl)
    }
    val createTable = """
        CREATE TABLE users (
            id INT PRIMARY KEY AUTO_INCREMENT,
            name VARCHAR(255)
        )
    """.trimIndent()

    val insert = "INSERT INTO users (name) values ('foo'), ('bar')"

    DriverManager.getConnection(jdbcUrl).use { conn ->
        conn.createStatement().use { stmt ->
            stmt.execute(createTable)
            stmt.execute(insert)
        }
    }

    Sql2o(source).open().use { stmt ->
        val users = stmt.createQuery("SELECT * FROM users").executeAndFetch(User::class.java)
        println(users)
    }
}