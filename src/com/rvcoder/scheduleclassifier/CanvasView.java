package com.rvcoder.scheduleclassifier;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class CanvasView extends View {


	//drawing path
	private Path drawPath;
	//drawing and canvas paint
	private Paint drawPaint, canvasPaint;
	//initial color
	private int paintColor = Color.BLACK;
	//canvas
	private Canvas drawCanvas;
	//canvas bitmap
	private Bitmap canvasBitmap;
	//brush sizes
	private float brushSize, lastBrushSize;
	//erase flag
	private boolean erase=false;
	
	
public	float gradient (Point first, Point second)
	{
		float rise=second.y-first.y;
		float run=second.x-first.x;
		float grad=rise/run;

		if(run==0)
		{

			grad=10;

		}

		return grad;
	}		

	
public float makelinegetY(Point p1, Point p2,float x)
{
	float y=0;
	float m=gradient(p1, p2);

	float c=p1.y-m*p1.x;

	y=m*x+c;


	return y;
}

public boolean diffx(Point p1, Point p2)
{
	boolean diff=false;
	
	float diffx=p2.x-p1.x;
	
	
	if(diffx>0){diff=true;}
	
	
	return diff;
}
	
	
	
	
	
	
	
	
	
	
	
	public Context context;
	int no_of_transaction,highlight;
	Canvas canvas;
	

	
	/**
	 * @return the highlight
	 */
	public int getHighlight() {
		return highlight;
	}

	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(int highlight) {
		this.highlight = highlight;
		//drawgraph();
		
		
	
		
		 //Toast.makeText(context,"highlighted value "+highlight, Toast.LENGTH_SHORT).show();
		 
	}

	public CanvasView(Context context, AttributeSet attrs){
		super(context, attrs);
		setupDrawing();
	}
	private void setupDrawing(){

		//prepare for drawing and setup paint stroke properties
//		brushSize = getResources().getInteger(R.INTEGER.small_size);
		lastBrushSize = brushSize;
		drawPath = new Path();
		drawPaint = new Paint();
		drawPaint.setColor(paintColor);
		drawPaint.setAntiAlias(true);
		drawPaint.setStrokeWidth(1f);
		drawPaint.setStyle(Paint.Style.STROKE);
		drawPaint.setStrokeJoin(Paint.Join.ROUND);
		drawPaint.setStrokeCap(Paint.Cap.ROUND);
		canvasPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		drawCanvas = new Canvas(canvasBitmap);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
		canvas.drawPath(drawPath, drawPaint);
	this.canvas=canvas;
	
	}
	
	
	public void deepcopy(ArrayList<ScheduleItem> scheduleList)
	{
		
		ScheduleItem item;
		schedule1= new ArrayList<ScheduleItem>() ;
		
		for(int i=0;i<scheduleList.size();i++)
		{
			item = new ScheduleItem();
			item.operation = scheduleList.get(i).operation;
			item.transaction = scheduleList.get(i).transaction;
			item.dataElem = scheduleList.get(i).dataElem;
			
			schedule1.add(item);
			
			
				
			
		}
		

	}
	
	
	
	
	//register user touches as drawing action
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float touchX = event.getX();
			float touchY = event.getY();
			//respond to down, move and up events
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				drawgraph();
				
				
				break;
			default:
				return false;
			}
			//redraw
			invalidate();
			return true;

		}

	
	public CanvasView(Context context,ArrayList<ScheduleItem> scheduleList,int no_oF,int high) {
	  super(context);
	 
	   this.context=context;
	  no_of_transaction=no_oF;
	  highlight=high;
		deepcopy(scheduleList);
		setupDrawing();
	}

	
	
	

		public int sides;
		public ArrayList<ScheduleItem> schedule1;
	 
		public void drawgraph() 
		{
				
			
			
			
			
			ArrayList<TranscationPair> scheduleCount = new ArrayList<TranscationPair>();
			
			for(int i=0;i<no_of_transaction;i++)
			{
				for(int j=0;j<no_of_transaction;j++)
				{
				TranscationPair elem = new TranscationPair();
				elem.count=0;
				elem.tran1=i+1;
				elem.tran2=j+1;
				
				scheduleCount.add(elem);
				}
			}
		
			
		
			sides=no_of_transaction;
			
			
			
			int x = (getMeasuredWidth()/2)  ;
			int y = (getMeasuredHeight()/2) ;

			if (sides < 2) return;
			ArrayList<Point> plist = new ArrayList<Point>();

			Paint paint = new Paint();
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			for (int i = 0; i < sides; i++)
			{ 
				paint.setStyle(Style.STROKE);
				canvas.drawCircle((float) (x + 190 * Math.cos(i * 2 * Math.PI / sides)),
						(float) (y + 190 * Math.sin(i * 2 * Math.PI / sides)),20,paint);
				canvas.drawText("T"+(i+1), (float) (x + 190 * Math.cos(i * 2 * Math.PI / sides)) -6 , (float) (y + 190 * Math.sin(i * 2 * Math.PI / sides)) +4 , paint);
				Point p = new Point();
				p.setX((float) (x + 190 * Math.cos(i * 2 * Math.PI / sides)));
				p.setY((float) (y + 190 * Math.sin(i * 2 * Math.PI / sides)));
				plist.add(p);
			}
		
	
			int rad=5;
			int vard=40;
			int varx=25;
			
			for(int i=0;i<highlight;i++)
			{	

				if (schedule1.get(i).operation == 0)//read
			       {  	   
				for(int j=i+1;j<schedule1.size();j++)
				{	//write
					   if (schedule1.get(j).operation == 1 && schedule1.get(j).dataElem.equals(schedule1.get(i).dataElem)  && schedule1.get(i).transaction != schedule1.get(j).transaction)
				       {
						  // Toast.makeText(context,"conflict " , Toast.LENGTH_SHORT).show();
					
				//		   drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
					//		drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
//						   canvas.drawLine(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY(), plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY(), paint);   
							canvas.drawPath(drawPath, paint);
							
							   Point pp1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
							   Point pp2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
						
							   if(diffx(pp1,pp2))
							   {
								
								   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()+varx));
									drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx));
									canvas.drawPath(drawPath, paint);
//									
								   
								   
								   
								   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
								canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()-varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx),rad, paint);
							   }
							   else
							   {	
								   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()-varx));
									drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx));
									canvas.drawPath(drawPath, paint);
//									
								   
								   
								   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
									canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()+varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx),rad, paint);
										   
							   }

							
							
									
								drawPath.reset();
						   int trans1=0;
						   int trans2=0;
						////////////////////////////////////////////////////////   
						   for(int z=0;z<no_of_transaction*no_of_transaction;z++)
						   {
							   if(scheduleCount.get(z).tran1==schedule1.get(i).transaction && scheduleCount.get(z).tran2==schedule1.get(j).transaction)
							   {	
								//   Toast.makeText(context,"trans1 "+i+" trans2 "+j+" data "+schedule1.get(i).dataElem , Toast.LENGTH_SHORT).show();
								   boolean cond=false;
								   for(int a=0;a<scheduleCount.get(z).variables.size();a++)
								   {
									   if(scheduleCount.get(z).variables.get(a).equals(schedule1.get(i).dataElem))
									   {
										   cond=true;
										   
									   }
									   
								   }
								   
								   if(!cond)
								   {
									   
									   Point p1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
									   Point p2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
									//   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);

									   
		//							   Toast.makeText(context,"conflict "+schedule1.get(i).dataElem, Toast.LENGTH_SHORT).show();
									 //  canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) (plist.get(schedule1.get(j).transaction-1).getY()) , paint);
							
									   if(diffx(p1,p2))
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
	   
									   }
									   else
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
											
										   
									   }
		
	//								   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) (plist.get(schedule1.get(j).transaction-1).getY()) , paint);
										
									   
									   
									   scheduleCount.get(z).count=scheduleCount.get(z).count+9;
									   scheduleCount.get(z).variables.add(schedule1.get(i).dataElem);
									   
								   }
								   
								}
							   
						   }
						   ////////////////////////////////////////////////////////
							
				       
				       }
				}
			       }
			
			       else if (schedule1.get(i).operation == 1)//write
			       {  	   
				for(int j=i+1;j<schedule1.size();j++)
				{
					   if (schedule1.get(j).operation == 1 && schedule1.get(j).dataElem.equals(schedule1.get(i).dataElem)  && schedule1.get(i).transaction != schedule1.get(j).transaction)
				       {
						   //Toast.makeText(context,"conflict " , Toast.LENGTH_SHORT).show();
						   		//drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
								//drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
								//canvas.drawPath(drawPath, paint);
//								canvas.drawCircle(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY(),rad, paint);
								   Point pp1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
								   Point pp2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
							
								   if(diffx(pp1,pp2))
								   {
									
									   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()+varx));
										drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx));
										canvas.drawPath(drawPath, paint);
//										
									   
									   
									   
									   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
									canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()-varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx),rad, paint);
								   }
								   else
								   {	
									   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()-varx));
										drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx));
										canvas.drawPath(drawPath, paint);
//										
									   
									   
									   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
										canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()+varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx),rad, paint);
											   
								   }
								   
								   
								   
								   
								   
								   
								drawPath.reset();
								
						//   canvas.drawLine(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY(), plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY(), paint);   
						   int trans1=0;
						   int trans2=0;
						   //////////////////////////////////////////////////////
						   for(int z=0;z<no_of_transaction*no_of_transaction;z++)
						   {
							   if(scheduleCount.get(z).tran1==schedule1.get(i).transaction && scheduleCount.get(z).tran2==schedule1.get(j).transaction)
							   {	
								//   Toast.makeText(context,"trans1 "+i+" trans2 "+j+" data "+schedule1.get(i).dataElem , Toast.LENGTH_SHORT).show();
								   
								   boolean cond=false;
								   for(int a=0;a<scheduleCount.get(z).variables.size();a++)
								   {
									   if(scheduleCount.get(z).variables.get(a).equals(schedule1.get(i).dataElem))
									   {
										   cond=true;
										   
									   }
									   
								   }
								   
								   if(!cond)
								   {
//									   Toast.makeText(context,"conflict "+schedule1.get(i).dataElem, Toast.LENGTH_SHORT).show();
								
									   
									   Point p1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
									   Point p2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
									   if(diffx(p1,p2))
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
	   
									   }
									   else
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
											
										   
									   }
//									   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) (plist.get(schedule1.get(j).transaction-1).getY()) , paint);
									 
//									   canvas.drawText(""+schedule1.get(i).dataElem, (float) ((plist.get(schedule1.get(i).transaction-1).getX()+plist.get(schedule1.get(j).transaction-1).getX())/2  + scheduleCount.get(z).count+8  ) , (float) ((plist.get(schedule1.get(i).transaction-1).getY()+plist.get(schedule1.get(j).transaction-1).getY())/2) , paint);
									   scheduleCount.get(z).count=scheduleCount.get(z).count+9;
									   scheduleCount.get(z).variables.add(schedule1.get(i).dataElem);
									   
								   }
								   
								   
								   
								  
							   
							   
							   }
							   
						   }		
				       /////////////////////////////////////////////////////
				       
				       }
					   else if (schedule1.get(j).operation == 0 && schedule1.get(j).dataElem.equals(schedule1.get(i).dataElem)  && schedule1.get(i).transaction != schedule1.get(j).transaction)
				       {
						   //Toast.makeText(context,"conflict " , Toast.LENGTH_SHORT).show();
					   
						   
//						   drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
	//							drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
		//						canvas.drawPath(drawPath, paint);
//								canvas.drawCircle(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY(),rad, paint);
								   Point pp1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
								   Point pp2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
							
								   if(diffx(pp1,pp2))
								   {
									
									   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()+varx));
										drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx));
										canvas.drawPath(drawPath, paint);
//										
									   
									   
									   
									   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
									canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()-varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()-varx),rad, paint);
								   }
								   else
								   {	
									   	drawPath.moveTo(plist.get(schedule1.get(i).transaction-1).getX()-varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(i).transaction-1).getX()-varx));
										drawPath.lineTo(plist.get(schedule1.get(j).transaction-1).getX()+varx, (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx));
										canvas.drawPath(drawPath, paint);
//										
									   
									   
									   //canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
										canvas.drawCircle((float) (plist.get(schedule1.get(j).transaction-1).getX()+varx  ) , (float) makelinegetY(pp1, pp2, plist.get(schedule1.get(j).transaction-1).getX()+varx),rad, paint);
											   
								   }
								drawPath.reset();
								
//						   canvas.drawLine(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY(), plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY(), paint);   
						  
						   int trans1=0;
						   int trans2=0;
						   ///////////////////////////////////////////
						   for(int z=0;z<no_of_transaction*no_of_transaction;z++)
						   {
							   if(scheduleCount.get(z).tran1==schedule1.get(i).transaction && scheduleCount.get(z).tran2==schedule1.get(j).transaction)
							   {	
								//   Toast.makeText(context,"trans1 "+i+" trans2 "+j+" data "+schedule1.get(i).dataElem , Toast.LENGTH_SHORT).show();
								   
								   boolean cond=false;
								   for(int a=0;a<scheduleCount.get(z).variables.size();a++)
								   {
									   if(scheduleCount.get(z).variables.get(a).equals(schedule1.get(i).dataElem))
									   {
										   cond=true;
										   
									   }
									   
								   }
								   
								   if(!cond)
								   {
								
									   Point p1=new Point(plist.get(schedule1.get(i).transaction-1).getX(), plist.get(schedule1.get(i).transaction-1).getY());
									   Point p2=new Point(plist.get(schedule1.get(j).transaction-1).getX(), plist.get(schedule1.get(j).transaction-1).getY());
									   if(diffx(p1,p2))
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()-vard) , paint);
	   
									   }
									   else
									   {
										   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()+vard  + scheduleCount.get(z).count+8 ) , (float) makelinegetY(p1, p2, plist.get(schedule1.get(j).transaction-1).getX()+vard) , paint);
											
										   
									   }

//								   Toast.makeText(context,"conflict "+schedule1.get(i).dataElem, Toast.LENGTH_SHORT).show();
							//		   canvas.drawText(""+schedule1.get(i).dataElem, (float) (plist.get(schedule1.get(j).transaction-1).getX()-vard  + scheduleCount.get(z).count+8 ) , (float) (plist.get(schedule1.get(j).transaction-1).getY()) , paint);
									 
//									   canvas.drawText(""+schedule1.get(i).dataElem, (float) ((plist.get(schedule1.get(i).transaction-1).getX()+plist.get(schedule1.get(j).transaction-1).getX())/2  + scheduleCount.get(z).count+8  ) , (float) ((plist.get(schedule1.get(i).transaction-1).getY()+plist.get(schedule1.get(j).transaction-1).getY())/2) , paint);
									   scheduleCount.get(z).count=scheduleCount.get(z).count+9;
									   scheduleCount.get(z).variables.add(schedule1.get(i).dataElem);
									   
								   }
								   
								   }
							   
						   }
				       ////////////////////////////////////////////////////////////
				       }
				}
			       }
			
			
			
			
			}
		
		
		
		
		
		
		
		
		
		
		
		
		
		}

	 
	 

	}