package dev.sanket.app.db.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import dev.sanket.app.db.model.Expense;

public class ExpenseDAO extends AbstractDAO<Expense>
{
    public ExpenseDAO(SessionFactory sessionFactory)
    {
        super(sessionFactory);
    }

    public List<Expense> all()
    {
        Query query = currentSession().createQuery("FROM " + Expense.class.getName());
        return list(query);
    }

    public Expense create(Expense expense)
    {
        expense.setVat(expense.getAmount() * 0.2);
        expense.setAmount(expense.getAmount() + expense.getVat());
        
        return persist(expense);
    }
}