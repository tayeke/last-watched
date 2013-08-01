package com.example.lastwatched;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by tayloreke on 7/31/13.
 */
public class ShowActivity extends Activity {

  private final String TAG = "ShowActivity";

  HashMap<String, String> show;

  TextView showTitle;
  TextView showSeason;
  TextView showEpisode;

  DBTools dbTools = new DBTools(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_activity);

    Intent intent = getIntent();
    show = (HashMap<String, String>) intent.getSerializableExtra("show");

    showTitle = (TextView) findViewById(R.id.showTitle);
    showSeason = (TextView) findViewById(R.id.showSeason);
    showEpisode = (TextView) findViewById(R.id.showEpisode);

    showTitle.setText(show.get("title"));
    showSeason.setText(show.get("season"));
    showEpisode.setText(show.get("episode"));

  }

  @Override
  public void onBackPressed() {
    Intent theIntent = new Intent(getApplication(), ShowListActivity.class);
    startActivity(theIntent);
  }

  public void callShowListActivity(View view) {
    Intent theIntent = new Intent(getApplication(), ShowListActivity.class);
    startActivity(theIntent);
  }

  public void incrementEpisode(View view) {
    show.put("episode", String.format("%d", Integer.parseInt(show.get("episode").toString())+1));
    showEpisode.setText(show.get("episode"));
    dbTools.updateShow(show);
  }

  public void decrementEpisode(View view) {
    int curr = Integer.parseInt(show.get("episode").toString());

    if(curr > 0) {
      show.put("episode", String.format("%d",curr-1));
      showEpisode.setText(show.get("episode"));
      dbTools.updateShow(show);
    }

  }

  public void incrementSeason(View view) {
    show.put("season", String.format("%d", Integer.parseInt(show.get("season").toString())+1));
    showSeason.setText(show.get("season"));
    dbTools.updateShow(show);
  }

  public void decrementSeason(View view) {
    int curr = Integer.parseInt(show.get("season").toString());

    if(curr > 0) {
      show.put("season", String.format("%d", curr-1));
      showSeason.setText(show.get("season"));
      dbTools.updateShow(show);
    }
  }

}
