package com.rvcoder.scheduleclassifier;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.Toast;

import com.rvcoder.scheduleclassifier.MultiSpinner.MultiSpinnerListener;
import com.tokenautocomplete.TokenCompleteTextView.TokenListener;
/*
Copyright (c) <2014> <Rvcoders>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

*/
public class MainActivity extends Activity implements TokenListener,MultiSpinnerListener,OnClickListener{

	ArrayList<Button> fields=new ArrayList<Button>();
	ArrayList<TableRow> row;//=new ArrayList<TableRow>();
	ArrayList<String> rows=new ArrayList<String>();
	ArrayList<String> col=new ArrayList<String>();
	TableLayout tbl;
	TransactionView TagsView;
	ChipAdapter tagsadapter;
	String alltext="";
	ArrayList<ScheduleItem> trans=new ArrayList<ScheduleItem>();
	private int no_of_transaction;
	ArrayList<String> items,selecteditems=new ArrayList<String>();
	ArrayList<Integer> nooftrans,no_of_commits;
	Spinner nooftransactions;
	MultiSpinner itemsinner;
	ScrollView table;
	LinearLayout finale;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setitems();
		setnooftrans();
		table=(ScrollView) findViewById(R.id.scrolllayout);
		finale=(LinearLayout) findViewById(R.id.linearLayout);
		TagsView = (TransactionView)findViewById(R.id.searchView);
		TagsView.setTokenListener(this);
		TagsView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(!table.isShown())
				{
					table.setVisibility(View.VISIBLE);
					showtable();
					Animation animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.up_from_bottom);
					table.startAnimation(animation);

				}
				return true;
			}
		});
		TagsView.setEnabled(false);
		itemsinner=(MultiSpinner) findViewById(R.id.multi_spinner);
		itemsinner.setItems(items, alltext,this);
		nooftransactions=(Spinner) findViewById(R.id.transactionpinner1);
		SpinnerAdapter adat=new SpinnerAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,nooftrans);
		nooftransactions.setAdapter(adat);
		nooftransactions.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
				no_of_transaction=nooftrans.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parentView) {
				no_of_transaction=nooftrans.get(0);
			}

		});
	}

	public void reset(View v)
	{
		TagsView.setEnabled(false);
		itemsinner.setEnabled(true);
		itemsinner.setSelection(0);
		nooftransactions.setEnabled(true);
		trans.clear();
		List<Object> tra=TagsView.getObjects();
		for(int i=0;i<tra.size();i++)
		{
			TagsView.removeObject(tra.get(i));
		}
		TagsView.setHint("");
		table.setVisibility(View.GONE);
	}
	public void disablegroup(RadioGroup grp)
	{
		for(int i=0;i<grp.getChildCount();i++)
		{
			((RadioButton)grp.getChildAt(i)).setEnabled(false);
		}
	}
	public void enablegroup(RadioGroup grp)
	{
		for(int i=0;i<grp.getChildCount();i++)
		{
			((RadioButton)grp.getChildAt(i)).setEnabled(true);
		}
	}
	public void apply(View v){

		
	if(!selecteditems.isEmpty())
	{
		TagsView.setHint("Please click on text field");
		TagsView.setEnabled(true);
		itemsinner.setEnabled(false);
		nooftransactions.setEnabled(false);
	}
	else
	{
		Toast.makeText(getApplicationContext(), "please Select variables", Toast.LENGTH_SHORT).show();
	}
}
public void showtable()
{
	row=new ArrayList<TableRow>();
	tbl=(TableLayout) findViewById(R.id.btntableLayout);
	double selecteditem=0;
	if(selecteditems.size()<no_of_transaction)
		selecteditem=Math.floor(selecteditems.size()/no_of_transaction);
	int selection=0;
	for(int i=0;i<no_of_transaction;i++)
	{
		TableRow tableRow = new TableRow(MainActivity.this);
		tableRow.setPadding(5, 10, 5, 20);
		for(int k=0;k<selecteditems.size();k++)
		{
			Button read = new Button(MainActivity.this);
			read.setId(5*no_of_transaction+k);   
			read.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			read.setText("r"+(i+1)+selecteditems.get(k).toString());
			read.setBackgroundResource(R.drawable.edge);
			read.setPadding(5, 5, 5, 5);
			read.setGravity(Gravity.CENTER);
			read.setOnClickListener(this);
			fields.add(read);
			tableRow.addView(read);
			Button write = new Button(MainActivity.this);
			write.setId(5*no_of_transaction+k+1);   
			write.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			write.setText("w"+(i+1)+selecteditems.get(k).toString());
			write.setBackgroundResource(R.drawable.edge);
			write.setPadding(15, 5, 5, 5);
			write.setGravity(Gravity.CENTER);
			write.setOnClickListener(this);
			fields.add(write);
			tableRow.addView(write);

		}
		Button commit = new Button(MainActivity.this);
		commit.setId(5*no_of_transaction+12);   
		commit.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
		commit.setText("c"+(i+1));
		commit.setBackgroundResource(R.drawable.edge);
		commit.setPadding(15, 5, 5, 5);
		commit.setGravity(Gravity.CENTER);
		commit.setOnClickListener(this);
		fields.add(commit);
		tableRow.addView(commit);

		for(int s=0;s<selecteditem;s++)
		{
			Button read = new Button(MainActivity.this);
			read.setId(5*no_of_transaction+s);   
			read.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			read.setText(selecteditems.get(selection+s).toString());
			read.setBackgroundResource(R.drawable.edge);
			read.setPadding(15, 5, 5, 5);
			read.setGravity(Gravity.CENTER);
			read.setOnClickListener(this);
			fields.add(read);
			tableRow.addView(read);
		}
		selection+=selecteditem;
		row.add(tableRow);
	}
	tbl.removeAllViews();
	for(int d=0;d<row.size();d++)
	{
		TableRow tb=row.get(d);
		tbl.addView(tb);
	}
	tbl.requestLayout();
	Toast.makeText(getApplicationContext(), "row items"+tbl.getChildCount(), Toast.LENGTH_SHORT).show();
}
public void ondelete(View v)
{
	List<Object> tra=TagsView.getObjects();
	if(tra.size()>0)
		TagsView.removeObject(tra.get(tra.size()-1));
}
public void onsave(View v)
{
	savetransaction();
	checkcommits();
	if(!trans.isEmpty())
	{
		Intent i=new Intent(MainActivity.this,TransactionActivity.class);
		Bundle b=new Bundle();
		b.putSerializable("schedule", trans);
		i.putExtra("schedulebundle",b);
		i.putExtra("nooftransactions", no_of_transaction);
		startActivity(i);
	}
	else
	{
		Toast.makeText(getApplicationContext(), "Please enter the Schedule ! :)", Toast.LENGTH_SHORT).show();
	}
}
public void setitems()
{
	items=new ArrayList<String>();
	items.add("a");
	items.add("b");
	items.add("c");
	items.add("d");
	items.add("e");
	items.add("f");
	items.add("g");
	items.add("h");
	items.add("i");
	items.add("j");
	items.add("k");
	items.add("l");
	items.add("m");
	items.add("n");
	items.add("o");
	items.add("p");
	items.add("q");
	items.add("r");
	items.add("s");
	items.add("t");
	items.add("u");
	items.add("v");
	items.add("w");
	items.add("x");
	items.add("y");
	items.add("z");
}
public void setnooftrans()
{
	nooftrans=new ArrayList<Integer>();
for(int i=2;i<20;i++)
	nooftrans.add(i);

}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.activity_main, menu);
	return true;
}

@Override
public void onTokenAdded(Object arg0) {
	// TODO Auto-generated method stub
	//trans.add(arg0.toString());
	TagsView.performValidation();


}

@Override
public void onTokenRemoved(Object arg0) {
	// TODO Auto-generated method stub
	TagsView.performValidation();

}

@Override
public void onItemsSelected(boolean[] selected) {
	selecteditems.clear();
	for(int i=0;i<selected.length;i++)
	{
		if(selected[i])
		{
			selecteditems.add(items.get(i));
		}
	}

}
@Override 
public void onBackPressed() { 
	// NOTE Trap the back key: when the CustomKeyboard is still visible hide it, only when it is invisible, finish activity 
	if(table.isShown())
	{
		table.setVisibility(View.GONE);
	}
	else this.finish();
}

public void checkcommits()
{
	boolean check=false;
	no_of_commits=new ArrayList<Integer>();
	for(int i=0;i<no_of_transaction;i++)
	{
		for(int j=0;j<trans.size();j++)
		{
			if(trans.get(j).getOperation()==2)
			{
				if(trans.get(j).getTransaction()==i+1)
				{	check=true;
					break;
				}
			}
		}
		if(!check)
		{
			ScheduleItem schditem=new ScheduleItem();
			schditem.setOperation(2);
			schditem.setTransaction(i+1);
			trans.add(schditem);
		}
		else
		{
			check=false;
		}
	}
}
public void savetransaction()
{
	List<Object> tra=TagsView.getObjects();
	for(int i=0;i<tra.size();i++)
	{
		String clicked=tra.get(i).toString();
		ScheduleItem schditem=new ScheduleItem();
		int oerationitem=1;
		if(clicked.charAt(0)=='r')
		{
			oerationitem=0;
		}
		if(clicked.charAt(0)=='c')
		{
			oerationitem=2;
		}

		schditem.setOperation(oerationitem);
		schditem.setTransaction(Integer.parseInt(""+clicked.charAt(1)));
		if(oerationitem!=2)
		{
			schditem.setDataElem(""+clicked.charAt(2));
		}
		if(oerationitem==2)
		{
			if(!checkcommit(clicked))
			{
				trans.add(schditem);
			}
		}
		else
		{
			trans.add(schditem);
		}
	}
}
@Override
public void onClick(View v) {
	String clicked=((Button) v).getText().toString();
	ScheduleItem schditem=new ScheduleItem();
	int oerationitem=1;
	if(clicked.charAt(0)=='r')
	{
		oerationitem=0;
	}
	if(clicked.charAt(0)=='c')
	{
		oerationitem=2;
	}

	schditem.setOperation(oerationitem);
	schditem.setTransaction(Integer.parseInt(""+clicked.charAt(1)));
	if(oerationitem!=2)
	{
		schditem.setDataElem(""+clicked.charAt(2));
	}
	if(oerationitem==2)
	{
		if(!checkcommit(clicked))
		{
			TagsView.addObject(new Transactiontext(clicked));
		}else
			Toast.makeText(getApplicationContext(), "Transaction T"+schditem.getTransaction()+" has already been commited", Toast.LENGTH_SHORT).show();
	}
	else
	{
		TagsView.addObject(new Transactiontext(clicked));
	}
	// TODO Auto-generated method stub

}
public boolean checkcommit(String s)
{
	for(int i=0;i<TagsView.getObjects().size();i++)
	{
		if(TagsView.getObjects().get(i).toString().equalsIgnoreCase(s))
		{
			return true;
		}
	}
	return false;
}
}
