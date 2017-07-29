package com.example.archiektor.warehouse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listTitle;
    HashMap<String, List<String>> listDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expListView = (ExpandableListView) findViewById(R.id.listviewExpandable);

        // preparing list data
        prepareListData();

        connectWithService();

        listAdapter = new ExpandableListAdapter(this, listTitle, listDetail);

        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String nameOfSupply;

                Toast.makeText(
                        getApplicationContext(),
                        listTitle.get(groupPosition) + " : " + listDetail.get(listTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), EditingActivity.class);
                intent.setAction(Intent.ACTION_SEND);
                nameOfSupply = listTitle.get(groupPosition) + "/" + listDetail.get(listTitle.get(groupPosition)).get(childPosition);
                //try to push Drawable in intent and set in new Activity
                //int res = (int) v.get
                intent.putExtra(EditingActivity.EXTRA_MESSAGE, nameOfSupply);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listTitle.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listTitle.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });
/**
 expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
@Override public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
v.
return false;
}
});*/
    }


    private void prepareListData() {

        listTitle = new ArrayList<>();
        listDetail = new HashMap<>();

        listTitle.add("Bolts");
        listTitle.add("Butterflynuts");
        listTitle.add("Screws (metal)");
        listTitle.add("Rivets");
        listTitle.add("Spacers");
        listTitle.add("Screws (wood)");

        List<String> bolt = new ArrayList<>();
        bolt.add("M6");
        bolt.add("M8");
        bolt.add("M10");
        bolt.add("M12");
        bolt.add("M16");
        bolt.add("M20");

        List<String> butterflynut = new ArrayList<>();
        butterflynut.add("M5");
        butterflynut.add("M6");

        List<String> metallscrew = new ArrayList<>();
        metallscrew.add("3,5 Х 9,5");
        metallscrew.add("3,5 Х 11");
        metallscrew.add("4,2 Х 16");
        metallscrew.add("4,2 Х 19");
        metallscrew.add("4,2 Х 25");

        List<String> rivet = new ArrayList<>();
        rivet.add("4 Х 6");
        rivet.add("4 Х 8");
        rivet.add("4 Х 12");
        rivet.add("4,8 Х 12");
        rivet.add("4,8 Х 16");

        List<String> spacer = new ArrayList<>();
        spacer.add("M4");
        spacer.add("M5");
        spacer.add("M6");

        List<String> woodscrew = new ArrayList<>();
        woodscrew.add("3,5 Х 9,5");
        woodscrew.add("3,5 Х 11");
        woodscrew.add("4,2 Х 16");
        woodscrew.add("4,2 Х 19");
        woodscrew.add("4,2 Х 25");


        listDetail.put(listTitle.get(0), bolt); // Header, Child data
        listDetail.put(listTitle.get(1), butterflynut);
        listDetail.put(listTitle.get(2), metallscrew);
        listDetail.put(listTitle.get(3), rivet);
        listDetail.put(listTitle.get(4), spacer);
        listDetail.put(listTitle.get(5), woodscrew);


    }

    private void connectWithService() {
        Intent intent = new Intent(this, DelayedService.class);
        startService(intent);
    }

}


