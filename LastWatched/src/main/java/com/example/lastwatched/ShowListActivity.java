package com.example.lastwatched;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowListActivity extends Activity {

  private final String TAG = "ShowListActivity";

  EditText newShowFormTitle;
  ListView showList;
  ListAdapter showAdapter;

  ArrayList<HashMap<String, String>> showArray;

  DBTools dbTools = new DBTools(this);

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.show_list_activity);

    newShowFormTitle = (EditText) findViewById(R.id.newShowFormTitle);

    showList = (ListView) findViewById(R.id.showList);

    showList.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        callShowActivity(showArray.get(i));

      }

    });

    showArray =  dbTools.getAllShows();

    if(showArray.size()!=0) {

      ArrayList<HashMap<String, String>> showsWithProgress = new ArrayList<HashMap<String, String>>();
      for(HashMap<String, String> show : showArray) {
        show.put("progress", String.format("S%sE%s",
          new String[] {show.get("season").toString(), show.get("episode").toString()}));
        showsWithProgress.add(show);
      }
      showArray = showsWithProgress;

      updateShowList();
    }
  }

  public void addNewShow(View view) {

    HashMap<String, String> newValues = new HashMap<String, String>();
    newValues.put("title", newShowFormTitle.getText().toString());


    String rowId = dbTools.insertShow(newValues);

    newShowFormTitle.setText("");

    newValues.put("id", rowId);
    newValues.put("progress", "S0E0");
    newValues.put("season", "0");
    newValues.put("episode", "0");

    showArray.add(0, newValues);

    updateShowList();

  }

  public void updateShowList() {

    showAdapter = new SimpleAdapter(ShowListActivity.this, showArray,
      R.layout.show_item,
      new String[] {"title", "progress"},
      new int[] {R.id.showItemTitle, R.id.showItemProgress});

    showList.setAdapter(showAdapter);

  }

  public void callShowActivity(HashMap<String, String> show) {
    Intent theIntent = new Intent(getApplication(), ShowActivity.class);
    theIntent.putExtra("show", show);
    startActivity(theIntent);
  }

  public void callShowListActivity(View view) {
    Intent theIntent = new Intent(getApplication(), ShowListActivity.class);
    startActivity(theIntent);
  }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.show_list, menu);
//        return true;
//    }
    
}
