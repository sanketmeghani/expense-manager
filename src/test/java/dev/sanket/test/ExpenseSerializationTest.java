package dev.sanket.test;

import java.io.IOException;
import java.util.Calendar;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.sanket.app.db.model.Expense;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.FixtureHelpers;

public class ExpenseSerializationTest
{
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

    @BeforeClass
    public static void setup()
    {
        MAPPER.setDateFormat(Expense.dateFormat);
    }

    @Test
    public void shouldBeSerializedToJson() throws JsonProcessingException, JSONException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 25);

        final Expense expense = new Expense(0, calendar.getTime(), 20.0, "Test expense", 4.0);

        String expected = FixtureHelpers.fixture("fixtures/expense.json");
        String actual = MAPPER.writeValueAsString(expense);

        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void shouldBeDeserializedFromJson() throws JsonParseException, JsonMappingException, IOException
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 25);

        final Expense expected = new Expense(0, calendar.getTime(), 20.0, "Test expense", 4.0);
        final Expense actual = MAPPER.readValue(FixtureHelpers.fixture("fixtures/expense.json"), Expense.class);

        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
