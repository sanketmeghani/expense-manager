package dev.sanket.app;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ApplicationConfiguration extends Configuration
{
    @Valid
    @NotNull
    @JsonProperty("database")
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty("vatPercentage")
    private double vatPercentage;
    
    public DataSourceFactory getDataSourceFactory()
    {
        return database;
    }

    public void setDatabase(DataSourceFactory database)
    {
        this.database = database;
    }

    public double getVatPercentage()
    {
        return vatPercentage;
    }

    public void setVatPercentage(double vatPercentage)
    {
        this.vatPercentage = vatPercentage;
    }
}
