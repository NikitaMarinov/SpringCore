package com.core.config;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LiquibaseConfig {

    public void configureLiquibase(Session session) {
        try {
            session.doWork(connection -> {
                try {
                    Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
                    try (Liquibase liquibase = new Liquibase("changelog/changelog-master.yaml", new ClassLoaderResourceAccessor(), database)) {
                        liquibase.update(new Contexts());

                        log.info("Changes applied successfully.");
                    }
                } catch (Exception e) {
                    log.error("The database connection didn't create!");
                }
            });
        } catch (Exception e) {
            log.error("The session didn't connect!");
        }
    }
}
