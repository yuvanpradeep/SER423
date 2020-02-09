/* CustomListAdapter.java
 * Copyright (c) 2020 Yuvan Pradeep. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * The instructor and the University have rights to build and evaluate
 * the software package for the purpose of determining my grade and program assessment
 *
 * Purpose: CustomListAdapter class is the custom adapter used for displaying data in list view
 *
 * Ser423 Mobile Applications
 * @author Yuvan Pradeep Paramasivam Murugesan
 * mailto: yparamas@asu.edu
 * @version February 7, 2020
 */

package edu.asu.msse.yparamas.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * CustomListAdapter class is the custom adapter used for displaying data in list view
 */
public class CustomListAdapter extends BaseAdapter {

    private Context parent;
    private String[] members;

    CustomListAdapter(Context parent, String[] members ) {
        super();
        this.parent = parent;
        this.members = members;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public Object getItem(int position){
        return members[position];
    }

    @Override
    public int getCount() {
        return members.length;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) this.parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.place_listview, null);
        }
        TextView placeName = convertView.findViewById(R.id.place_name);
        placeName.setText(members[position]);
        return convertView;
    }
}
