package com.tutorial.cart;

import java.util.ArrayList;

import android.app.Application;

public class Controller extends Application{
	// we will setup an array list with all of our products
	
private ArrayList<ModelProducts> myproducts = new ArrayList<ModelProducts>();
	
	 private ModelCart myCart = new ModelCart();
	
	
	public ModelProducts getProducts(int pPosition){
		
		return myproducts.get(pPosition);
		
		
		}
	
	public void  setProducts(ModelProducts products){
		
		
		myproducts.add(products);
		
		
	}
	
	
	 public ModelCart getCart(){
		 
		 return myCart;
		 
		 
	 }
	

	 public int  getProductArraylistsize(){
		 
		 return myproducts.size();
		 
	
	
	 }

}
