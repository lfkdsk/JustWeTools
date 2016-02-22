package com.lfk.demo.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.lfk.demo.R;
import com.lfk.justwetools.View.CircleGraph.CircleGraph;
import com.lfk.justwetools.View.Clock.Clock;
import com.lfk.justwetools.View.LineProgress.LineProgress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ViewDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_demo);

        initCircleGraph();

        initLineProgress();

        initClock();
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

    private void initLineProgress() {
        final LineProgress lineProgress = (LineProgress) findViewById(R.id.linepro);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineProgress.setProgressing(lineProgress.getProgressing() + 10);
            }
        });
    }

    private void initClock() {
        ((Clock) findViewById(R.id.clock)).
                setColor(getResources().getColor(R.color.colorPrimary));
    }
}
