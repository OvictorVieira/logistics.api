package com.logistics.config

import com.datastax.oss.driver.api.core.CqlSession
import com.datastax.oss.driver.api.core.auth.ProgrammaticPlainTextAuthProvider
import com.datastax.oss.driver.api.core.config.DefaultDriverOption
import com.datastax.oss.driver.api.core.config.DriverConfigLoader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.SchemaAction
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories
import java.net.InetSocketAddress

@Configuration
@EnableCassandraRepositories(basePackages = ["com.logistics.domain"])
open class CassandraConfig(
    @Value("\${spring.data.scylla.contact-points}") private val contactPoints: List<String>,
    @Value("\${spring.data.scylla.port}") private val port: Int,
    @Value("\${spring.data.scylla.keyspace-name}") private val keyspaceName: String,
    @Value("\${spring.data.scylla.schema-action}") private val schemaAction: String,
    @Value("\${spring.data.scylla.username}") private val username: String?,
    @Value("\${spring.data.scylla.password}") private val password: String?,
    @Value("\${spring.data.scylla.local-datacenter}") private val localDatacenter: String
) : AbstractCassandraConfiguration() {

    @Bean
    @Primary
    open fun cqlSession(): CqlSession {
        val configLoader = DriverConfigLoader.programmaticBuilder()
            .withStringList(DefaultDriverOption.CONTACT_POINTS, contactPoints)
            .withString(DefaultDriverOption.SESSION_KEYSPACE, keyspaceName)
            .withString(DefaultDriverOption.AUTH_PROVIDER_USER_NAME, username!!)
            .withString(DefaultDriverOption.AUTH_PROVIDER_PASSWORD, password!!)
            .withString(DefaultDriverOption.LOAD_BALANCING_LOCAL_DATACENTER, localDatacenter)
            .build()

        return CqlSession.builder()
            .withConfigLoader(configLoader)
            .addContactPoints(contactPoints.map { InetSocketAddress(it, port) })
            .withLocalDatacenter(localDatacenter)
            .withKeyspace(keyspaceName)
            .withAuthProvider(ProgrammaticPlainTextAuthProvider(username, password))
            .build()
    }

    override fun getKeyspaceName() = keyspaceName

    override fun getSchemaAction() = SchemaAction.valueOf(schemaAction)

    override fun getEntityBasePackages() = arrayOf("com.logistics.domain")
}
