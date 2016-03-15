package com.tutorial.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
///change
public class MainActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

final LinearLayout layout = (LinearLayout)findViewById(R.id.linearMain);
final Button btn = (Button)findViewById(R.id.second);

final Controller ct = (Controller) getApplicationContext();
		int productsize = ct.getProductArraylistsize();

		//if statement so that everytime MainActivity loads our view the productarraylist only adds items if the arraylis has 0 items
		//so basically the logic is that we don't want products to continually be added to the array everytime the screen loads just when the
		///program loads for tee first time
		if(productsize == 0)
		{

			ModelProducts products = null;
			///Here we can manually add product to our arraylist
			products = new ModelProducts("Red Super Bright LEDs 5pcs" , "Super Bright Red leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("Green Super Bright LEDs 5pcs" , "Super Bright Green leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("Blue Super Bright LEDs 5pcs" , "Super Bright Blue leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("Common Anode RGB Super Bright LEDs 5pcs" , "Super Bright Common anode RGB leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("Common Cathode RGB Super Bright LEDs 5pcs" , "Super Bright Common cathode RGB leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("White Super Bright LEDs 5pcs" , "Super Bright White leds 3.2 volts", 2);
			ct.setProducts(products);
			products = new ModelProducts("Amber Super Bright LEDs 5pcs" , "Super Bright Amber leds 3.2 volts", 2);
			ct.setProducts(products);
			////this code can test if we can still use productSize as a variable instead of defining a new one called newproducSize
			///productSize = ct.getProductArraylistsize();
			////hmmm weird that didnt  work
		}

///define the size of the array with the products after they are added
int newproductSize = ct.getProductArraylistsize();




         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);

///lets use a for loop to read the array list then produce the buttons and and descriptions of all of the products in the arraylist
         for (int j=0;j< newproductSize;j++){


        	String pName = ct.getProducts(j).getProductName();
        	int pPrice = ct.getProducts(j).getProductPrice();
			 String pDisc = ct.getProducts(j).getProductDesc();

        	 LinearLayout la = new LinearLayout(this);

        	 la.setOrientation(LinearLayout.HORIZONTAL);

			 ///output the text
			 ///make j inclusive so it eliminates "0" when listing products to users
			 int inclusiveJ = j+1;

        	 TextView tv = new TextView(this);

        	 tv.setText(inclusiveJ+". "+pName+" ");

        	 la.addView(tv);

             TextView tv1 = new TextView(this);

        	 tv1.setText("$" + pPrice + " ");

        	 la.addView(tv1);

			 TextView tv2 = new TextView (this);
			 tv1.setText(" Info: "+pDisc+" ");

			 la.addView(tv2);
			 final int index = j;
			 Log.i("TAG", "index:"+index);
			 ModelProducts productsObject = ct.getProducts(index);



			 ///setup the buttons
        	  final Button btn1 = new Button(this);

			 if(!ct.getCart().CheckProductInCart(productsObject)){btn1.setText("Add to Cart");}else{btn1.setText("Already In cart");}
			 btn1.setId(j+1);



        	        btn1.setLayoutParams(params);


        	   btn1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					// TODO Auto-generated method stub

					// we can log parts of the program so we can see a readout in the logcat



					ModelProducts productsObject = ct.getProducts(index);

					/// if item is not in cart ad the item
					if(!ct.getCart().CheckProductInCart(productsObject)){

					btn1.setText("Item has been added");

					ct.getCart().setProducts(productsObject);

					//toast the number of items in the cart to the user
					Toast.makeText(getApplicationContext(), "Number of Items:" +ct.getCart().getCartsize(),Toast.LENGTH_SHORT).show();

					}else{

				    Toast.makeText(getApplicationContext(), "Products"+(index+1)+"Already in cart",Toast.LENGTH_SHORT ).show();

					}

				}

		});

        	 la.addView(btn1);

			 layout.addView(la);


          }

         btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent in = new Intent(getBaseContext(),Screen2.class);
				 startActivity(in);



			}
		});

    }
}