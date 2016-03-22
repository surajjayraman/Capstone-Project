package ielite.app.eventme.data;

import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;

/**
 * Created by Suraj on 22-03-2016.
 */
public interface EventsAndRegionsColumns {
    @DataType(DataType.Type.TEXT)
    @PrimaryKey
    @References(table = EventDatabase.EVENTS,
            column = EventsColumns.EB_ID)
    String EVENT_ID =
            "event_id";

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @References(table = EventDatabase.REGIONS,
            column = RegionsColumns._ID)
    String REGION_ID =
            "region_id";

    //in Julian days so we can compare
    @DataType(DataType.Type.REAL)
    @NotNull
    @References(table = EventDatabase.REGIONS,
            column = RegionsColumns.ADDED_DATE_TIME)
    String ADDED_DATE_TIME =
            "added_date_time";

}
