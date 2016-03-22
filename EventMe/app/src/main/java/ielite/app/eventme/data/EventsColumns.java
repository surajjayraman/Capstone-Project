package ielite.app.eventme.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.ConflictResolutionType;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.References;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Suraj on 22-03-2016.
 */
public interface EventsColumns {

    @DataType(DataType.Type.INTEGER)
    @PrimaryKey
    @AutoIncrement
    String _ID =
            "_id";

    @DataType(DataType.Type.TEXT)
    @References(table = EventDatabase.VENUES, column = VenuesColumns.EB_VENUE_ID)
    String EVENTBRITE_VENUE_ID =
            "eb_venue_id";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String NAME =
            "event_name";

    @DataType(DataType.Type.TEXT)
    @NotNull
    @Unique(onConflict = ConflictResolutionType.REPLACE)
    String EB_ID =
            "eb_id";

    @DataType(DataType.Type.TEXT)
    String DESCRIPTION = "description";

    @DataType(DataType.Type.TEXT)
    String URL =
            "event_url";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String START_DATE_TIME =
            "start_date_time";

    @DataType(DataType.Type.INTEGER)
    @NotNull
    String END_DATE_TIME =
            "end_date_time";

    @DataType(DataType.Type.INTEGER)
    String CAPACITY =
            "capacity";

    @DataType(DataType.Type.TEXT)
    @NotNull
    String STATUS =
            "status";

    @DataType(DataType.Type.TEXT)
    String LOGO_URL =
            "logo_url";

    @DataType(DataType.Type.TEXT)
    String CATEGORY =
            "category";

    //in Julian days so we can compare
    @DataType(DataType.Type.REAL)
    @NotNull
    @References(table = EventDatabase.REGIONS,
            column = RegionsColumns.ADDED_DATE_TIME)
    String ADDED_DATE_TIME =
            "added_date_time";

}

