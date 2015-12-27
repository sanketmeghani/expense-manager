package dev.sanket.app.db.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.Objects;

import dev.sanket.util.CustomDateSerializer;

@Entity
@Table(name = "expense")
public class Expense
{
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "date")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    @Column(name = "amount")
    private double amount;

    @Column(name = "reason")
    private String reason;

    @Column(name = "vat")
    private double vat;

    public Expense()
    {
    }

    public Expense(int id, Date date, double amount, String reason, double vat)
    {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.reason = reason;
        this.vat = vat;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public double getVat()
    {
        return vat;
    }

    public void setVat(double vat)
    {
        this.vat = vat;
    }

    @Override
    public String toString()
    {
        return "{\"id\": " + id + ", \"amount\": " + amount + ", \"reason\": " + reason + ", \"date\": " + date + ", \"vat\": " + vat + " }";
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof Expense))
        {
            return false;
        }

        Expense otherExpense = (Expense) o;

        boolean datesAreSame = (this.date == null && otherExpense.getDate() == null) || (Objects.equal(dateFormat.format(this.date), dateFormat.format(otherExpense.getDate())));

        return (this == otherExpense)
                || ((this.id == otherExpense.getId()) && (this.amount == otherExpense.getAmount()) && (this.vat == otherExpense.getVat()) && Objects.equal(this.getReason(), otherExpense.getReason()) && datesAreSame);
    }
}
