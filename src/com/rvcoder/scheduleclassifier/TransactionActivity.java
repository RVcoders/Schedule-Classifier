package com.rvcoder.scheduleclassifier;

import java.util.ArrayList;
import java.util.Locale;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint({ "ValidFragment", "ResourceAsColor" })
public class TransactionActivity extends FragmentActivity {


	static Context contextglobal;
	CanvasView canvas;
	Canvas can;


	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	ArrayList<ScheduleItem> schedule=new ArrayList<ScheduleItem>();
	int no_of_transaction,highlight=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction);
		Intent in=getIntent();
		Bundle b=in.getExtras().getBundle("schedulebundle");//ParcelableArrayList("schedule")
		schedule=(ArrayList<ScheduleItem>) b.getSerializable("schedule");
		no_of_transaction=in.getIntExtra("nooftransactions", 0);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(),TransactionActivity.this);

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.transaction, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter implements OnPageChangeListener{

		Context context;
		public SectionsPagerAdapter(FragmentManager fm,Context con) {
			super(fm);
			context=con;
			contextglobal=context;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;

			if(position==0)
			{
				//Toast.makeText(context,"Creating Transaction", Toast.LENGTH_SHORT).show();
				fragment = new DummySectionFragment(context);
			}
			else
			{
				//Toast.makeText(context,"Creating Transaction Processing" +highlight, Toast.LENGTH_SHORT).show();
				fragment = new TranFragment(context);
			}
			return fragment;

		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return "Transaction Processing".toUpperCase(l);
			case 1:
				return "Transaction Items".toUpperCase(l);
			}
			return null;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			if(arg0==1)
			{
				canvas.setHighlight(highlight);
				canvas.draw(can);
			}
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	@SuppressLint("ResourceAsColor")
	public class DummySectionFragment extends Fragment implements OnClickListener{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */

		ArrayList<TextView> fields=new ArrayList<TextView>();
		ArrayList<TableRow> row=new ArrayList<TableRow>();
		ArrayList<String> rows=new ArrayList<String>();
		ArrayList<String> col=new ArrayList<String>();
		TableLayout tbl;
		Context cont;
		Button savebutton,next;
		@SuppressLint("ValidFragment")
		public DummySectionFragment(Context con) 
		{
			cont=con;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_transaction_dummy, container, false);
			tbl=(TableLayout) rootView.findViewById(R.id.tableLayout1);
			//Processing done here
			savebutton=(Button) rootView.findViewById(R.id.transactionSavebutton);
			savebutton.setOnClickListener(this);
			next=(Button) rootView.findViewById(R.id.transactionUndobutton);
			next.setOnClickListener(this);
			TableRow tableRow = new TableRow(cont);
			tableRow.setPadding(5, 10, 5, 20);
			addtransactions();
			for(int k=0;k<col.size();k++)
			{
				TextView ed = new TextView(cont);
				ed.setId(k);   
				ed.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
				ed.setText(col.get(k).toString());
				ed.setPadding(5, 5, 5, 5);
				ed.setGravity(Gravity.CENTER);
				ed.setBackgroundColor(color.darker_gray);
				ed.setTextSize(20);
				tableRow.addView(ed);
			}
			row.add(tableRow);
			for(int i=0;i<schedule.size();i++)
			{
				tableRow = new TableRow(getActivity());
				tableRow.setPadding(5, 10, 5, 0);
				for(int j=0;j<no_of_transaction;j++)
				{

					TextView ed = new TextView(getActivity());
					ed.setId(j);   
					ed.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
					if(schedule.get(i).transaction==j+1)
					{
						ed.setText(schedule.get(i).toString());
					}
					else
						ed.setText("");
					ed.setTextSize(20);
					ed.setPadding(5, 5, 5, 5);
					ed.setGravity(Gravity.CENTER);
					fields.add(ed);
					tableRow.addView(ed);
				}
				row.add(tableRow);
			}
			for(int d=0;d<row.size();d++)
			{
				TableRow tb=row.get(d);
				tbl.addView(tb);
			}
			return rootView;
		}
		public void addtransactions()
		{
			for(int i=0;i<no_of_transaction;i++)
			{
				col.add("T"+(i+1));
			}
		}
		////////////on this click function\\\\\\\\\\\\\\\\\\\\\



		@SuppressLint("ResourceAsColor")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			switch(v.getId())
			{
			case R.id.transactionUndobutton:
			{
				if(highlight<tbl.getChildCount()-1)
				{
					for(int i=0;i<highlight+1;i++)
					{
						tbl.getChildAt(i).setBackgroundColor(getResources().getColor(android.R.color.transparent));
					}
					TableRow row=(TableRow) tbl.getChildAt(highlight+1);
					row.getChildAt(1).setFocusable(true);
					tbl.getChildAt(highlight+1).setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
					highlight++;
					break;

				}
				else
				{
					mViewPager.setCurrentItem(1, true);
				}
			}
			//schedule

			//Toast.makeText(contextglobal,"processing button pressed", Toast.LENGTH_SHORT).show();
			////////////////////////////////////////////////////////////////////////////////////
			//testing the classification///////////////////////////////////////////////////////
			case R.id.transactionSavebutton:
			{
				boolean recoverable = isRecoverable(schedule);
				boolean cascadeless = isCascadeless(schedule);
				boolean strict      = isStrict(schedule);


				if(!recoverable)
				{
					Toast.makeText(contextglobal,"This Schedule is not Recoverable", Toast.LENGTH_SHORT).show();
				}
				else if(recoverable && !cascadeless && !strict )
				{
					Toast.makeText(contextglobal,"This Schedule is Recoverable", Toast.LENGTH_SHORT).show();
				}
				else if(recoverable && cascadeless && !strict )
				{
					Toast.makeText(contextglobal,"This Schedule is Cascadeless", Toast.LENGTH_SHORT).show();
				}
				else if(recoverable && cascadeless && strict )
				{
					Toast.makeText(contextglobal,"This Schedule is Strict", Toast.LENGTH_SHORT).show();
				}
				highlight=tbl.getChildCount()-1;
				mViewPager.setCurrentItem(1, true);
				break;
			}
			}
		}
	}
	@SuppressLint("ValidFragment")
	public class TranFragment extends Fragment implements OnClickListener{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		Context cont;
		LinearLayout lin;
		@SuppressLint("ValidFragment")
		public TranFragment(Context con) {
			cont=con;

		}
		Button b;
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.trans, container, false);
			lin=(LinearLayout) rootView.findViewById(R.id.canvas);
			can=new Canvas();

			canvas=new CanvasView(cont,schedule,no_of_transaction,highlight);
			//		canvas.setHighlight(highlight);
			//			canvas.drawCircle(can);
			lin.addView(canvas);
			//b=(Button) rootView.findViewById(R.id.resultbutton);
			//b.setOnClickListener(this);
			return rootView;
		}



		@Override
		public void onClick(View v) {
			final Dialog dialog = new Dialog(cont);
			dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			dialog.setTitle("Result");
			dialog.setContentView(R.layout.customdialog);
			dialog.show();
			// TODO Auto-generated method stub

		}


	}


	////////////////////////////////////


	public static boolean isRecoverable(ArrayList<ScheduleItem> schedule)
	{
		// code for Recoverable modeule

		int Count=schedule.size();

		//Toast.makeText(contextglobal,"Count is "+Count, Toast.LENGTH_SHORT).show();

		Boolean flag = false;
		for (int i = 0; i < Count; i++)
		{

			if (schedule.get(i).operation == 1)
			{
				//Toast.makeText(contextglobal,"i is "+i, Toast.LENGTH_SHORT).show();

				for (int j = i + 1; j < Count; j++)
				{
					//Toast.makeText(contextglobal,"row is "+j, Toast.LENGTH_SHORT).show();

					if (schedule.get(j).operation == 0 && schedule.get(j).dataElem.equals(schedule.get(i).dataElem) && schedule.get(i).transaction != schedule.get(j).transaction)
					{	
						//	Toast.makeText(contextglobal,"in if condition", Toast.LENGTH_SHORT).show();

						for (int k = j + 1; k < Count; k++)
						{	
							//Toast.makeText(contextglobal,"row is "+k, Toast.LENGTH_SHORT).show();

							if (schedule.get(k).operation == 2 && schedule.get(k).transaction != schedule.get(i).transaction)
							{ return false;}
							else if (schedule.get(k).operation >= 2 && schedule.get(k).transaction == schedule.get(i).transaction)
							{
								flag = true;
								break;
							} 
						}
						if (flag)
							break;
					}
					else if (schedule.get(j).operation > 1 && schedule.get(i).transaction == schedule.get(j).transaction) // if commit of same transaction arises break;
					{
						flag = true;
						//  System.Console.WriteLine("\n\n yes its working \n\n ");
						break;
					}
				}
				flag = false;
			}
		}
		return true;
	}




	////////////////////////////////////

	public static boolean isCascadeless(ArrayList<ScheduleItem> schedule)
	{
		int Count=schedule.size();
		// code for Cascadeless modeule
		for (int i = 0; i < Count; i++)
		{
			if (schedule.get(i).operation == 1)
			{
				for (int j = i + 1; j < Count; j++)
				{
					if (schedule.get(j).operation == 0 && schedule.get(j).dataElem.equals(schedule.get(i).dataElem)  && schedule.get(i).transaction != schedule.get(j).transaction)
						return false;
					else if (schedule.get(j).operation >= 2 && schedule.get(j).transaction == schedule.get(i).transaction)
						break;
				}
			}
		}
		return true;
	}



	///////////////////////////////////


	public static boolean isStrict(ArrayList<ScheduleItem> schedule)
	{
		// code for strict modeule

		int Count=schedule.size();

		for (int i = 0; i < Count; i++)
		{
			if (schedule.get(i).operation == 1)
			{
				for (int j = i + 1; j < Count; j++)
				{
					if (schedule.get(j).operation == 1 && schedule.get(j).dataElem.equals(schedule.get(i).dataElem)  && schedule.get(i).transaction != schedule.get(j).transaction)
						return false;
					else if (schedule.get(j).operation == 0 && schedule.get(j).dataElem.equals(schedule.get(i).dataElem)  && schedule.get(i).transaction != schedule.get(j).transaction)
						return false;
					else if (schedule.get(j).operation >= 2 && schedule.get(j).transaction == schedule.get(i).transaction)
						break;
				}
			}
		}
		return true;
	}






	//////////////////////////////////////
}









