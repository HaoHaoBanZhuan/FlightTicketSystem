package com.wbhz.flightsys.Dao;

import java.util.List;

import com.wbhz.flightsys.entity.Billing;


public interface BillingDao {
	public abstract void insert(Billing billing) ;
	public abstract List<Billing> queryAll();
	public abstract Billing getBillingByOrderNumber(int orderNumber) ;
	public abstract void deleteByOrderNumber(int orderNumber);
	
}
