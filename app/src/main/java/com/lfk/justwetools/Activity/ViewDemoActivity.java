package com.lfk.justwetools.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.lfk.justwetools.R;
import com.lfk.justwetools.View.CircleGraph.CircleGraph;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        initCircleGraph();
    }

    private void initCircleGraph() {
        JSONArray jsonArray = new JSONArray();
        JSONObject object1 = new JSONObject();
        try {
            object1.put("name", "1");
            object1.put("sweep", 200);
            object1.put("color", 0xFFFEDD74);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject object2 = new JSONObject();
        try {
            object2.put("name", "2");
            object2.put("sweep", 20);
            object2.put("color", 0xFF82D8EF);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonArray.put(object1);
        jsonArray.put(object2);
        CircleGraph graph = (CircleGraph) findViewById(R.id.circle_graph);
        graph.setText("搞基");
        graph.setJson(jsonArray.toString());
    }
}
