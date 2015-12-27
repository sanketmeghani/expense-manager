package dev.sanket.util;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import dev.sanket.app.db.model.Expense;

public class CustomDateSerializer extends JsonSerializer<Date>
{
    @Override
    public void serialize(Date date, JsonGenerator generator, SerializerProvider provider) throws IOException, JsonProcessingException
    {
        String formattedDate = Expense.dateFormat.format(date);
        generator.writeString(formattedDate);
    }
}
