package com.example.archiektor.warehouse;

import android.app.Activity;
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

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.listviewExpandable);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listTitle, listDetail);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listTitle.get(groupPosition) + " : " + listDetail.get(listTitle.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
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

        // Adding header data
//        listTitle.add(new Supplies("Bolts", R.drawable.bolt));
//        listTitle.add(new Supplies("Butterflynuts", R.drawable.butterflynut));
//        listTitle.add(new Supplies("Screws (metal)", R.drawable.metallscrew));
//        listTitle.add(new Supplies("Rivets", R.drawable.rivet));
//        listTitle.add(new Supplies("Spacers", R.drawable.spacer));
//        listTitle.add(new Supplies("Screws (wood)", R.drawable.woodscrew));  //6

        // Adding child data
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
        metallscrew.add("Screw 3,5 Х 9,5");
        metallscrew.add("Screw 3,5 Х 11");
        metallscrew.add("Screw 4,2 Х 16");
        metallscrew.add("Screw 4,2 Х 19");
        metallscrew.add("Screw 4,2 Х 25");

        List<String> rivet = new ArrayList<>();
        rivet.add("Rivet 4 Х 6");
        rivet.add("Rivet 4 Х 8");
        rivet.add("Rivet 4 Х 12");
        rivet.add("Rivet 4,8 Х 12");
        rivet.add("Rivet 4,8 Х 16");

        List<String> spacer = new ArrayList<>();
        spacer.add("M4");
        spacer.add("M5");
        spacer.add("M6");

        List<String> woodscrew = new ArrayList<>();
        woodscrew.add("Screw 3,5 Х 9,5");
        woodscrew.add("Screw 3,5 Х 11");
        woodscrew.add("Screw 4,2 Х 16");
        woodscrew.add("Screw 4,2 Х 19");
        woodscrew.add("Screw 4,2 Х 25");


        listDetail.put(listTitle.get(0), bolt); // Header, Child data
        listDetail.put(listTitle.get(1), butterflynut);
        listDetail.put(listTitle.get(2), metallscrew);
        listDetail.put(listTitle.get(3), rivet);
        listDetail.put(listTitle.get(4), spacer);
        listDetail.put(listTitle.get(5), woodscrew);

//        listDetail.put(listTitle.get(0).getName(), bolt); // Header, Child data
//        listDetail.put(listTitle.get(1).getName(), butterflynut);
//        listDetail.put(listTitle.get(2).getName(), metallscrew);
//        listDetail.put(listTitle.get(3).getName(), rivet);
//        listDetail.put(listTitle.get(4).getName(), spacer);
//        listDetail.put(listTitle.get(5).getName(), woodscrew);

    }

}
