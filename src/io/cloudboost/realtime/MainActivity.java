package io.cloudboost.realtime;

import io.cloudboost.CloudApp;
import io.cloudboost.CloudException;
import io.cloudboost.CloudObject;
import io.cloudboost.CloudObjectCallback;
import android.app.ListActivity;
import android.os.Bundle;

public class MainActivity extends ListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//inflate the main layout
		setContentView(R.layout.main_layout);
		//create adapter object and pass the row layout for listview rows
		final Adapter adapter = new Adapter(this, R.layout.row);
		//set adapter to listview
		setListAdapter(adapter);
		//initialize you cloudboost app, the first parameter is app id 
		//and the second is the client key. please replace accordingly
		CloudApp.init("bengi123", "mLiJB380x9fhPRCjCGmGRg==");
		try {
			//start listening to "created" events on REAL_TIME table. the other 
			//2 types of events are "updated" and "deleted"
			CloudObject.on("REAL_TIME", "created", new CloudObjectCallback() {

				@Override
				public void done(final CloudObject x, CloudException t)
						throws CloudException {
					//remember this callback runs on a background thread
					//not the ui thread, so to update our adapter with the
					//received cloudobject, we run the code on ui thread
					runOnUiThread(new Runnable() {
						public void run() {
							adapter.add(x);
						}
					});

				}
			});
		} catch (CloudException e) {
			e.printStackTrace();
		}

	}

}
