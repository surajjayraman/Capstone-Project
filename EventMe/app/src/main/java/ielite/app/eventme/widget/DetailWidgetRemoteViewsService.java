package ielite.app.eventme.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import ielite.app.eventme.Projections;
import ielite.app.eventme.R;
import ielite.app.eventme.data.EventProvider;
import ielite.app.eventme.utils.Constants;
import ielite.app.eventme.utils.DateUtils;
import ielite.app.eventme.utils.LocationUtils;

/**
 * Created by Suraj on 22-03-2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class DetailWidgetRemoteViewsService extends RemoteViewsService {
    public final String LOG_TAG = DetailWidgetRemoteViewsService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;

            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                //We need to clear (and finally restore) the calling identity so
                //that calls use our process and permission

                data = getEvents();

            }

            private Cursor getEvents() {
                long regionId = LocationUtils.getPreferredRegionId(getApplicationContext());
                Log.i(LOG_TAG, "getting events for region ID " + regionId);
                Uri eventsUri = EventProvider.Events.withRegionId(regionId);
                Log.i(LOG_TAG, "Querying content provider with " + eventsUri);

                return getContentResolver().query(
                        eventsUri,
                        Projections.EVENT_COLUMNS_LIST_VIEW,
                        null,
                        null,
                        null);
            }

            @Override
            public void onDestroy() {
                if (data != null) {
                    data.close();
                    data = null;
                }
            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }
                RemoteViews views = new RemoteViews(getPackageName(),
                        R.layout.widget_detail_list_item);

                String logoUrl = data.getString(Projections.EventsListView.COL_LOGO_URL);
                String name = data.getString(Projections.EventsListView.COL_NAME);
                String venueName = data.getString(Projections.EventsListView.COL_VENUE_NAME);
                long startTime = data.getLong(Projections.EventsListView.COL_START_DATE_TIME);
                long endTime = data.getLong(Projections.EventsListView.COL_END_DATE_TIME);

                views.setTextViewText(R.id.widget_list_item_name_textview, name);
                views.setTextViewText(R.id.widget_list_item_venue_textview, venueName);

                if ((startTime - endTime) <= Constants.MILLIS_IN_A_DAY) {
                    String text = DateUtils.getSingleDayStartAndEndTime(DateUtils
                                    .FORMAT_LIST_TEXTVIEW_DATE_TIME, startTime,
                            endTime);
                    views.setTextViewText(R.id.widget_list_item_start_textview, text);
                } else {
                    String text1 = DateUtils.formatDateTime(DateUtils
                            .FORMAT_LIST_TEXTVIEW_DATE_TIME, startTime);
                    String text2 = DateUtils.formatEndDateTimeString(DateUtils
                            .FORMAT_LIST_TEXTVIEW_DATE_TIME, endTime);
                    views.setTextViewText(R.id.widget_list_item_start_textview, text1);
                    views.setTextViewText(R.id.widget_list_item_end_textview, text2);

                }

                //Set up the intent that will open the detail view of the match
                long eventId = data.getLong(Projections.EventsListView.COL_EVENT_ID);
                final Intent fillInIntent = new Intent();
                Uri eventUri = EventProvider.Events.withId(eventId);
                fillInIntent.setData(eventUri);
                views.setOnClickFillInIntent(R.id.widget_list_item, fillInIntent);
                Log.i(LOG_TAG, "onClickFillInIntent set!! ");
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_detail_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                if (data.moveToPosition(position))
                    return data.getLong(Projections.EventsListView.COL_EVENT_ID);
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}
