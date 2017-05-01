package com.example.archiektor.warehouse;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
//import android.widget.ImageView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;


class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    //    private LayoutInflater infalInflater;
    private List<String> listTitle; // header titles
    private HashMap<String, List<String>> listDetail; // child data in format of header title, child title


    ExpandableListAdapter(Context context, List<String> listTitle,
                          HashMap<String, List<String>> listDetail) {
        this.context = context;
        this.listTitle = listTitle;
        this.listDetail = listDetail;
    }

    //Get a single supply
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDetail.get(this.listTitle.get(groupPosition)).get(childPosition);
    }

    //Get supply id
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDetail.get(this.listTitle.get(groupPosition)).size();
    }

    //Get supply ROW
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.children_item, null);
        }

        //Get supply name
        final String childText = (String) getChild(groupPosition, childPosition);
        //Set supply name
        TextView txtListChild = (TextView) convertView.findViewById(R.id.supplyItem);
        txtListChild.setText(childText);

        return convertView;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listTitle.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listTitle.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);

        }

        //Get application resource/drawable not in Activity class, using context
        Resources contextResources = context.getResources();

        Drawable groupDrawable;

        switch (headerTitle) {
            case "Bolts":
                groupDrawable = contextResources.getDrawable(R.drawable.bolt, context.getTheme());
                break;
            case "Butterflynuts":
                groupDrawable = contextResources.getDrawable(R.drawable.butterflynut, context.getTheme());
                break;
            case "Screws (metal)":
                groupDrawable = contextResources.getDrawable(R.drawable.metallscrew, context.getTheme());
                break;
            case "Rivets":
                groupDrawable = contextResources.getDrawable(R.drawable.rivet, context.getTheme());
                break;
            case "Spacers":
                groupDrawable = contextResources.getDrawable(R.drawable.spacer, context.getTheme());
                break;
            case "Screws (wood)":
                groupDrawable = contextResources.getDrawable(R.drawable.woodscrew, context.getTheme());
                break;
            default:
                groupDrawable = contextResources.getDrawable(R.mipmap.ic_launcher, context.getTheme());
        }


        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageGroup);
        imageView.setImageDrawable(groupDrawable);

        TextView textView = (TextView) convertView.findViewById(R.id.textGroup);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(headerTitle);

        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
