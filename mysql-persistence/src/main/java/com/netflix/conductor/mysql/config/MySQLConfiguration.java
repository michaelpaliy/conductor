/*
 * Copyright 2020 Netflix, Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.mysql.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.conductor.dao.ExecutionDAO;
import com.netflix.conductor.dao.MetadataDAO;
import com.netflix.conductor.dao.QueueDAO;
import com.netflix.conductor.mysql.dao.MySQLExecutionDAO;
import com.netflix.conductor.mysql.dao.MySQLMetadataDAO;
import com.netflix.conductor.mysql.dao.MySQLQueueDAO;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(MySQLProperties.class)
@ConditionalOnProperty(name = "conductor.db.type", havingValue = "mysql")
@Import(DataSourceAutoConfiguration.class)
public class MySQLConfiguration {

    @Bean
    @DependsOn({"flyway", "flywayInitializer"})
    public MetadataDAO mySqlMetadataDAO(ObjectMapper objectMapper, DataSource dataSource, MySQLProperties properties) {
        return new MySQLMetadataDAO(objectMapper, dataSource, properties);
    }

    @Bean
    @DependsOn({"flyway", "flywayInitializer"})
    public ExecutionDAO mySqlExecutionDAO(ObjectMapper objectMapper, DataSource dataSource) {
        return new MySQLExecutionDAO(objectMapper, dataSource);
    }

    @Bean
    @DependsOn({"flyway", "flywayInitializer"})
    public QueueDAO mySqlQueueDAO(ObjectMapper objectMapper, DataSource dataSource) {
        return new MySQLQueueDAO(objectMapper, dataSource);
    }
}
