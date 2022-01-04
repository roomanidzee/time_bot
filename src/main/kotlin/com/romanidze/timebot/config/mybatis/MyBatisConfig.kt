package com.romanidze.timebot.config.mybatis

import org.apache.ibatis.session.ExecutorType
import org.apache.ibatis.session.SqlSessionFactory
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

/** Configuration for DB access layer */
@Configuration
@MapperScan(
    basePackages =
    [
        "com.romanidze.timebot.module.time.mappers.mybatis",
    ]
)
class MyBatisConfig(@Qualifier("dataSource") private val datasource: DataSource) {

    /**
     *
     * Configures a transaction manager for the database and MyBatis library
     *
     * @return DataSourceTransactionManager db transaction manager
     */
    @Bean
    fun dbTransactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(this.datasource)
    }

    /**
     *
     * Factory for creating db session to work with domain models
     *
     * @return SqlSessionFactory factory to work with db session
     */
    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactory {

        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(this.datasource)

        return sessionFactory.`object`!!
    }

    /**
     * Factory for returning session template for batch queries
     *
     * @return SqlSessionTemplate session template for batch queries
     */
    @Bean
    @Throws(Exception::class)
    fun sqlSessionTemplate(): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory(), ExecutorType.BATCH)
    }
}
