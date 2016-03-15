package com.tutorial.cart;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Screen2 extends Activity{

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen2);
////change

		TextView showCartContent = (TextView) findViewById(R.id.showcart);


		final Controller ct = (Controller) getApplicationContext();

		final int CartSize = ct.getCart().getCartsize();

		String show = "";
		/// display the items in the cart with a loop
		

		for (int i = 0; i < CartSize; i++) {

			String pName = ct.getCart().getProducts(i).getProductName();

			int pPrice = ct.getCart().getProducts(i).getProductPrice();

			String pDisc = ct.getCart().getProducts(i).getProductDesc();
				///just so the list is itemized 1 through max rather than 0 through max
				int inclusiveI = i+1;
			show += inclusiveI + ". " + "Product Name: " + pName + "\n " + "   Description :  " + pDisc + "\n" + "    Price :  " + pPrice + "\n" +
					"---------------------------------------------------------------------------------- \n";


		}
		/// We must create some logic with an algorithm to display the subtotal, the discount amount, and the total with the discount
		int subTotal = CartSize * 2;
		//Discount math
		Double bigDiscount = null;
		//Condition to determine the discount amount
		if (CartSize >= 5) {
			bigDiscount = subTotal * .2;
		}else
			{bigDiscount=0.00;}
		//Final Total Math
		double finalTotal = subTotal- bigDiscount;
		String pTotal = "\n SUBTOTAL: $" + subTotal + "\n Discount: $" + bigDiscount +"\n"+"FINAL TOTAL: $"+finalTotal;
		showCartContent.setText(show + pTotal);


	}///end of onCreate
	
	 
	
	

}


