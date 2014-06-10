/**
 * 
 */
package com.rvcoder.scheduleclassifier;

import java.util.ArrayList;
import java.util.List;

import com.tokenautocomplete.FilteredArrayAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Ahsan
 *
 */
public class ChipAdapter extends FilteredArrayAdapter<Transactiontext>{

	
	ArrayList<Transactiontext> chip;
	/**
	 * 
	 */
	public ChipAdapter(Context context,int textViewResourceId,
			ArrayList<Transactiontext> objects) {
		super(context, textViewResourceId, objects);
		// TODO Auto-generated constructor stub
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater l = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = (View)l.inflate(R.layout.person_layout, parent, false);
        }

        Transactiontext p = getItem(position);
        ((TextView)convertView.findViewById(R.id.name)).setText(p.getName());

        return convertView;
    }

	@Override
    protected boolean keepObject(Transactiontext obj, String mask) {
        mask = mask.toLowerCase();
        return obj.getName().toLowerCase().startsWith(mask);
    }


}
