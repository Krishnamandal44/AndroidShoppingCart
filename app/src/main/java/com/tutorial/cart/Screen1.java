package com.tutorial.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Screen1 extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.screen1);
		 
		 ///define where we display our data about the items in the cart
    TextView showCartContent = (TextView)findViewById(R.id.showcart);
	
	final Button btn3 = (Button)findViewById(R.id.third);
	
	final Controller ct = (Controller)getApplicationContext();
	
	final int CartSize = ct.getCart().getCartsize();
	
	String show = "";
	///this is where we handle how the cart will act if there are not items in the cart
	
	 if(CartSize >0){
		 
		 for(int i=0;i<CartSize;i++){
			 
			String pName = ct.getCart().getProducts(i).getProductName(); 
			 
		    int  pPrice = ct.getCart().getProducts(i).getProductPrice(); 
			
		    String pDisc = ct.getCart().getProducts(i).getProductDesc();


			 show += i + ". " + "Product Name: " + pName + "\n " + "   Discription :  " + pDisc + "\n" + "    Price :  " + pPrice + "\n" +
					 "---------------------------------------------------------------------------------- \n";
			 
			 
			 
		 }
		 
		 
		 
	 } else
		 
		 show = "There is no Items in Shopping Cart";
	 
	  showCartContent.setText(show);
	  
	  
	  btn3.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			if(CartSize >0){
				
				
				Intent  i = new Intent(getBaseContext(),Screen2.class);
				
				startActivity(i);
			}else 
				
				
				Toast.makeText(getApplicationContext(), "Shopping Cart is Empty", Toast.LENGTH_SHORT).show();
				
				
			
			
		}
	});
		 
}

	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	
	
	
	

}
