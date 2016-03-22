package ielite.app.eventme.widget;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import ielite.app.eventme.Projections;
import ielite.app.eventme.data.EventProvider;
import ielite.app.eventme.service.EventMeService;
import ielite.app.eventme.utils.DateUtils;
import ielite.app.eventme.utils.LocationUtils;

/**
 * Created by Suraj on 22-03-2016.
 */
public class DetailWidgetIntentService extends IntentService {

    public DetailWidgetIntentService() {
        super("DetailWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get today's data from the ContentProvider
        long regionId = LocationUtils.getPreferredRegionId(this);
        Uri regionUri = EventProvider.Regions.withId(regionId);

        Cursor data = getContentResolver().query(regionUri, Projections.REGION_COLUMNS, null,
                null, null);
        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }

        double dateAdded = data.getDouble(Projections.Regions.ADDED_DATE_TIME);
        if (!DateUtils.isDateTimeAfterCutOff(dateAdded)) {
            Intent i = new Intent(getApplicationContext(), EventMeService.class);
            startService(i);
        }

        data.close();
    }
}
