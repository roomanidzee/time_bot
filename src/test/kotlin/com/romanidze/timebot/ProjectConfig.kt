package com.romanidze.timebot

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringAutowireConstructorExtension
import io.zonky.test.db.postgres.embedded.EmbeddedPostgres

class ProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringAutowireConstructorExtension)

    lateinit var db: EmbeddedPostgres

    override fun beforeAll() {

        db = EmbeddedPostgres.builder().setPort(5432).start()

        val conn = db.postgresDatabase.connection

        val st = conn.createStatement()

        st.execute("CREATE DATABASE bot_db;")
        st.execute("CREATE USER bot_user WITH PASSWORD 'bot_pass';")
        st.close()

        conn.close()
    }

    override fun afterAll() {
        db.close()
    }
}
