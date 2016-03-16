package com.tutorial.cart;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;


public class Screen2 extends Activity implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener,
		LocationListener {


	Button show;
	TextView la, lon, msg;
	LocationManager locationManager;
	String PROVIDER = LocationManager.GPS_PROVIDER;

	private int zipCode = 0;
	public String zip;
	public String amountStr;
	private Spinner spinner1;
	private Button btnSubmit;
	public String item;
	public Double itemTax = 0.00;
	public Double grandTotal = 0.00;
	public Double amount = 0.00;
	public Double itemTaxTotal = 0.00;
	public Double zipTaxTotal = 0.00;

	public Double state = 0.00;
	private GoogleApiClient mLocationClient;

	public void geoLocate(View view) throws IOException, ParseException {



//        reset of values.  final product would be optimized
		String t = "Click TaxCalc";

		TextView itd = (TextView) findViewById(R.id.taxRateTotal);
		itd.setText(t);

		String zct = Double.toString(state);
		TextView srt = (TextView) findViewById(R.id.taxDisplayZip);
		srt.setText(t);

		TextView stext = (TextView) findViewById(R.id.totalStateAmount);
		stext.setText(t);

		TextView ittext = (TextView) findViewById(R.id.totalItemAmount);
		ittext.setText(t);

		TextView total = (TextView) findViewById(R.id.totalAmount);
		total.setText(t);

//        checking connectivity status

		if (AppStatus.getInstance(this).isOnline()) {

			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			//Lets only do something if the geocoder is known to be present
			if(Geocoder.isPresent()){
				//define the geocoder object
				Geocoder gc = new Geocoder(this);
				////ALLWAYS APROACH HARDWARE WITH CAUTION....... TRY and CATCH
				try {///Remember hardware and api calls should usually be done with try catch statements
					//only do something if the location provider is available ore else the program WILL crash
					//If its not available not big deal nothing happens
					if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
						Location currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						List<Address> addresses = gc.getFromLocation(currentLocation.getLatitude(), currentLocation.getLongitude(), 1);
						String locality = addresses.get(0).getLocality();
						String zip = addresses.get(0).getPostalCode();

						TextView text = (TextView) findViewById(R.id.zipDisplay);
						text.setText(zip + "Locality: " + locality);
						zipCode = ((Number) NumberFormat.getInstance().parse(zip)).intValue();
						Toast.makeText(this, "You are in " + locality + " , " + zipCode, Toast.LENGTH_SHORT).show();
					}
				}catch(IOException| IllegalStateException e) {
					Log.e("GPS", "Failed To get location services");throw e;}
			}
		} else {

			Toast.makeText(this, "The Use GPS will not work unless connected to Internet", Toast.LENGTH_SHORT).show();
		}

	}

    public void inputZip(View view) throws ParseException {
        TextView et = (TextView) findViewById(R.id.editText1);
        String zip = et.getText().toString();

        //        reset of values.  final product would be optimized
        String t = "Click TaxCalc";

        TextView itd = (TextView) findViewById(R.id.taxRateTotal);
        itd.setText(t);

        String zct = Double.toString(state);
        TextView srt = (TextView) findViewById(R.id.taxDisplayZip);
        srt.setText(t);

        TextView stext = (TextView) findViewById(R.id.totalStateAmount);
        stext.setText(t);

        TextView ittext = (TextView) findViewById(R.id.totalItemAmount);
        ittext.setText(t);

        TextView total = (TextView) findViewById(R.id.totalAmount);
        total.setText(t);

        // error handling if nothing is entered

        if (zip.equals("")) {

            TextView text = (TextView) findViewById(R.id.zipDisplay);
            text.setText("Enter Zip");
            zipCode = 0;
        } else {
            TextView text = (TextView) findViewById(R.id.zipDisplay);
            text.setText(zip);
            zipCode = ((Number) NumberFormat.getInstance().parse(zip)).intValue();
        }

    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.screen2);
		View linearLayout = findViewById(R.id.layout1);
		show = (Button) findViewById(R.id.btn);
		msg = (TextView) findViewById(R.id.msg);
		la = (TextView) findViewById(R.id.textlat);
		lon = (TextView) findViewById(R.id.textlong);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showLocation(location);
			}
		});
		TextView valueTV = new TextView(this);
		msg.setText("");
		valueTV.setId(5);
		valueTV.setLayoutParams(new LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT));

		((LinearLayout) linearLayout).addView(valueTV);

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
			show += inclusiveI + ". " + "Product Name: " + pName + "\n " + "   Discription :  " + pDisc + "\n" + "    Price :  " + pPrice + "\n" +
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
		String pTotal = "\n SUBTOTAL: $" + subTotal + "\n Discount: $" + bigDiscount +"\n"+"TOTAL Befor Tax: $"+finalTotal;
		showCartContent.setText(show + pTotal);
        amount=finalTotal;

	}
    public void calcGo(View view) throws ParseException {

//        hard coding of zipcode taxes.  This would become a separate class in a final product
        if (zipCode < 10001) {
            state = .01;
        } else if (zipCode < 20001) {
            state = .02;
        } else if (zipCode < 30001) {
            state = .03;
        } else if (zipCode < 40001) {
            state = .04;
        } else if (zipCode < 50001) {
            state = .05;
        } else {
            state = .06;

        }


//        hardcoding of item tax.  this would be a separate class in final product
        itemTax = .03;

        zipTaxTotal = amount * state;
        itemTaxTotal = amount * itemTax;
        grandTotal = amount + itemTaxTotal + zipTaxTotal;

        String zipTaxTotalF = String.format("%.2f", zipTaxTotal);
        String itemTaxTotalF = String.format("%.2f", itemTaxTotal);
        String grandTotalF = String.format("%.2f", grandTotal);

        String it = Double.toString(itemTax);
        TextView itd = (TextView) findViewById(R.id.taxRateTotal);
        itd.setText(it + "%");

        String zct = Double.toString(state);
        TextView srt = (TextView) findViewById(R.id.taxDisplayZip);
        srt.setText(zct + "%");

        TextView text = (TextView) findViewById(R.id.totalStateAmount);
        text.setText("$ " + zipTaxTotalF);

        TextView ittext = (TextView) findViewById(R.id.totalItemAmount);
        ittext.setText("$ " + itemTaxTotalF);

        TextView total = (TextView) findViewById(R.id.totalAmount);
        total.setText("$ " + grandTotalF);
    }

	private void showLocation(Location location) {
		if (location == null) {

			Toast.makeText(getApplicationContext(), "Currently you are located at:", Toast.LENGTH_LONG).show();
		} else {
			la.setText( " " + location.getLatitude());
			lon.setText(" " + location.getLongitude());
		}
	}
	private LocationListener listener = new LocationListener() {
		@Override
		public void onLocationChanged(Location location)
		{
			showLocation(location);


		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {
			;
		}
	};

	@Override
	protected void onPause() {

		super.onPause();
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.removeUpdates(listener);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.requestLocationUpdates(PROVIDER, 0, 0, listener);


	}

	@Override
	public void onConnected(Bundle bundle) {

	}

	@Override
	public void onConnectionSuspended(int i) {

	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {

	}
}
