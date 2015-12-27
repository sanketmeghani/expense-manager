package dev.sanket.app;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.logging.Logger;

import org.glassfish.jersey.filter.LoggingFilter;

import dev.sanket.app.db.bundle.ApplicationHibernateBundle;
import dev.sanket.app.db.dao.ExpenseDAO;
import dev.sanket.app.db.model.Expense;
import dev.sanket.app.resource.ExpenseResource;

public class ExpenseManager extends Application<ApplicationConfiguration>
{
    private final ApplicationHibernateBundle hibernate = new ApplicationHibernateBundle(Expense.class);

    public static void main(String[] args) throws Exception
    {
        ExpenseManager expenseManager = new ExpenseManager();
        expenseManager.run(args);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap)
    {
        bootstrap.addBundle(hibernate);
        bootstrap.addBundle(new AssetsBundle("/assets", "/", "default.html"));
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception
    {
        final ExpenseDAO expenseDAO = new ExpenseDAO(hibernate.getSessionFactory());
        environment.jersey().register(new ExpenseResource(expenseDAO));

        final ExpenseResource expenseResource = new ExpenseResource(expenseDAO);
        environment.jersey().register(expenseResource);

        environment.jersey().register(new LoggingFilter(Logger.getLogger(LoggingFilter.class.getName()), true));
        environment.jersey().setUrlPattern("/api/*");
    }

    @Override
    public String getName()
    {
        return "expense-manager";
    }
}
