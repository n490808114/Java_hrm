package xyz.n490808114.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateConverter implements Converter<String,Date> {
    private String dateFormString;

    public void setDateFormString(String dateFormString) {
        this.dateFormString = dateFormString;
    }

    @Override
    public Date convert(String date){
        try{
            SimpleDateFormat format = new SimpleDateFormat(this.dateFormString);
            return format.parse(date);
        }catch (ParseException ex){
            ex.printStackTrace();
            System.out.println("ÈÕÆÚ×ª»»Ê§°Ü");
            return null;
        }
    }
}
