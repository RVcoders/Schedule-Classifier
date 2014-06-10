package com.rvcoder.scheduleclassifier;

import java.io.Serializable;

public class ScheduleItem implements Serializable{

	
	public int transaction;
    /**
	 * @return the transaction
	 */
	public int getTransaction() {
		return transaction;
	}
	/**
	 * @param transaction the transaction to set
	 */
	public void setTransaction(int transaction) {
		this.transaction = transaction;
	}
	/**
	 * @return the dataElem
	 */
	public String getDataElem() {
		return dataElem;
	}
	/**
	 * @param dataElem the dataElem to set
	 */
	public void setDataElem(String dataElem) {
		this.dataElem = dataElem;
	}
	/**
	 * @return the operation
	 */
	public int getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public String dataElem;
    public int operation;
    @Override
    public String toString() 
    {
    	String fullstring="";
    	if(operation==0)
    		fullstring+="r";
    	else if(operation==1)
    		fullstring+="w";
    	else if(operation==2)
    		fullstring+="c";
    	
    	fullstring+=transaction;
    	if(dataElem!=null)
    		fullstring+=dataElem;
    	
    	return fullstring; 
    }
}
