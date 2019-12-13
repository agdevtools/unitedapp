package com.football.unitedapp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@Configuration
@EnableCassandraRepositories
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Value("${contactPoints}")
    private String contactPoints;
    @Value("${port}")
    private int port;
    @Value("${keySpace}")
    private String keySpace = "united";

    @Override
    protected String getKeyspaceName() {
        return keySpace;
    }
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }
    @Override
    protected int getPort() {
        return port;
    }
    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    public CassandraClusterFactoryBean cluster() {
        CassandraClusterFactoryBean bean = super.cluster();
        bean.setContactPoints(contactPoints);
        return bean;
    }
    @Override
    protected boolean getMetricsEnabled() { return false; }
}


