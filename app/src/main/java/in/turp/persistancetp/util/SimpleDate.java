package in.turp.persistancetp.util;

import java.util.Date;

/**
 * Created by lumiru on 04/08/15.
 */
public class SimpleDate extends Date {
    public SimpleDate(Date date) {
        super(date == null ? 0 : date.getTime());
    }
}
