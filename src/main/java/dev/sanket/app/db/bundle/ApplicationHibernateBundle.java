package dev.sanket.app.db.bundle;

import dev.sanket.app.ApplicationConfiguration;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

public class ApplicationHibernateBundle extends HibernateBundle<ApplicationConfiguration>
{
    public ApplicationHibernateBundle(Class<?> entity, Class<?>... entities)
    {
        super(entity, entities);
    }

    public PooledDataSourceFactory getDataSourceFactory(ApplicationConfiguration configuration)
    {
        return configuration.getDataSourceFactory();
    }
}
