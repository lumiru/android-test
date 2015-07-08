package in.turp.persistancetp.util;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by lumiru on 08/07/15.
*/
public final class DateTypeAdapter extends TypeAdapter<Date> {
    private final DateFormat dateTimeFormat;
    private final DateFormat dateFormat;

    public DateTypeAdapter() {
        this.dateTimeFormat = buildCustomFormat("yyyy-MM-dd\'T\'hh:mm:ss");
        this.dateFormat = buildCustomFormat("yyyy-MM-dd");
    }

    private static DateFormat buildCustomFormat(String format) {
        SimpleDateFormat iso8601Format = new SimpleDateFormat(format, Locale.FRANCE);
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    public Date read(JsonReader in) throws IOException {
        if(in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            return this.deserializeToDate(in.nextString());
        }
    }

    private synchronized Date deserializeToDate(String json) {
        try {
            return this.dateTimeFormat.parse(json);
        }
        catch (ParseException var1) {
            try {
                return this.dateFormat.parse(json);
            } catch (ParseException var3) {
                throw new JsonSyntaxException(json, var3);
            }
        }
    }

    public synchronized void write(JsonWriter out, Date value) throws IOException {
        if(value == null) {
            out.nullValue();
        } else {
            String dateFormatAsString = this.dateTimeFormat.format(value);
            out.value(dateFormatAsString);
        }
    }
}
