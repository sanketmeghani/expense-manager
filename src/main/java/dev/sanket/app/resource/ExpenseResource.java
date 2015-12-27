package dev.sanket.app.resource;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.sanket.app.db.dao.ExpenseDAO;
import dev.sanket.app.db.model.Expense;

@Path("/expenses")
@Produces(MediaType.APPLICATION_JSON)
public class ExpenseResource
{
    private ExpenseDAO expenseDAO;

    public ExpenseResource(ExpenseDAO expenseDAO)
    {
        this.expenseDAO = expenseDAO;
    }

    @GET
    @UnitOfWork
    public Response getAllExpenses()
    {
        return Response.ok(expenseDAO.all()).build();
    }

    @POST
    @UnitOfWork(transactional = true)
    public Response createExpense(Expense expense)
    {
        return Response.ok(expenseDAO.create(expense)).build();
    }
}