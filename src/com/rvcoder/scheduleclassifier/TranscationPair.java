package com.rvcoder.scheduleclassifier;

import java.util.ArrayList;

public class TranscationPair {

	public int tran1;
	public int tran2;
	public int count;
	
	public ArrayList<String> variables;
	
	public TranscationPair()
	{
		tran1=0;
		tran2=0;
		count=0;
		variables=new ArrayList<String>();
		
		
	}
	
	public int getTran1() {
		return tran1;
	}
	public void setTran1(int tran1) {
		this.tran1 = tran1;
	}
	public int getTran2() {
		return tran2;
	}
	public void setTran2(int tran2) {
		this.tran2 = tran2;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	
	
}
