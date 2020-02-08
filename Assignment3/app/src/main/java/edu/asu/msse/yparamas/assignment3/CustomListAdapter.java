package edu.asu.msse.yparamas.assignment3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
