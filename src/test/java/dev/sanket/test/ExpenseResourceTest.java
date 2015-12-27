package dev.sanket.test;

import io.dropwizard.testing.FixtureHelpers;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;

import dev.sanket.app.db.dao.ExpenseDAO;
import dev.sanket.app.db.model.Expense;
import dev.sanket.app.resource.ExpenseResource;

public class ExpenseResourceTest
{
    private static final ExpenseDAO expenseDAO = Mockito.mock(ExpenseDAO.class);

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder().addResource(new ExpenseResource(expenseDAO)).build();

    @BeforeClass
    public static void setup()
    {
        resources.getObjectMapper().setDateFormat(Expense.dateFormat);
    }

    @After
    public void tearDown()
    {
        Mockito.reset(expenseDAO);
    }

    @Test
    public void shouldReturnExistingExpenses() throws JSONException
    {
        mockExpenses();

        Response response = resources.client().target("/expenses").request(MediaType.APPLICATION_JSON).get();

        String actual = response.readEntity(String.class);
        String expected = FixtureHelpers.fixture("fixtures/expenses.json");

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(expected, actual, true);
    }

    private void mockExpenses()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 25);

        final Expense expense = new Expense(0, calendar.getTime(), 20.0, "Test expense", 4.0);
        final Expense anotherExpense = new Expense(1, calendar.getTime(), 30.0, "Another test expense", 6.0);

        List<Expense> expenses = new ArrayList<Expense>();
        expenses.add(expense);
        expenses.add(anotherExpense);

        Mockito.when(expenseDAO.all()).thenReturn(expenses);
    }

    @Test
    public void shouldCreateNewExpenseWithVat() throws JSONException
    {
        Entity<Expense> mockedExpense = mockExpense();

        Response response = resources.client().target("/expenses").request(MediaType.APPLICATION_JSON).post(mockedExpense);

        String actual = response.readEntity(String.class);
        String expected = FixtureHelpers.fixture("fixtures/add-expense-response.json");

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(expected, actual, true);
    }

    private Entity<Expense> mockExpense()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, Calendar.DECEMBER, 25);

        final Expense expense = new Expense(0, calendar.getTime(), 20.0, "Test expense", 4.0);

        Mockito.when(expenseDAO.create(expense)).thenAnswer(new Answer<Expense>()
        {
            @Override
            public Expense answer(InvocationOnMock invocation) throws Throwable
            {
                Expense expense = invocation.getArgumentAt(0, Expense.class);

                expense.setVat(expense.getAmount() * 0.2);
                expense.setAmount(expense.getAmount() + expense.getVat());

                return expense;
            }
        });

        return Entity.entity(expense, MediaType.APPLICATION_JSON);
    }
}
