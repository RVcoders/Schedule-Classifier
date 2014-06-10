/**
 * 
 */
package com.rvcoder.scheduleclassifier;

import java.util.ArrayList;
import java.util.List;

import com.rvcoder.scheduleclassifier.R;
import com.tokenautocomplete.FilteredArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Ahsan
 *
 */
public class SpinnerAdapter extends ArrayAdapter<Integer>{

	
	ArrayList<Integer> chip;
	/**
	 * 
	 */
	public SpinnerAdapter(Context context,int textViewResourceId,
			ArrayList<Integer> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = (View)l.inflate(R.layout.spinner_layout, parent, false);
        }

        int p = getItem(position);
        ((TextView)convertView.findViewById(R.id.noofT)).setText(""+p);

        return convertView;
    }
}
