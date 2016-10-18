package com.okedroid.justjava;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    //variabel global quantity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("Pesan Kopi");
        getSupportActionBar().setSubtitle("Belajar di Udacity.com");
    }

    /*
      method ini dipanggil untuk penambahan/pengurangah jumlah kopi yang ingin di pesan
      dan juga kondisi jika kopi  = 1 maka tidak bisa di kurangi lagi

       */
    public void increment(View v) {
        if (quantity ==100){
            Toast.makeText(this, "Kamu tidak bisa order  lebih dari 100", Toast.LENGTH_SHORT).show();
            return;

        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View v) {
        if (quantity == 1) {
            Toast.makeText(this, "Kamu tidak bisa order kurang dari 1", Toast.LENGTH_SHORT).show();
            return;

        }

            quantity = quantity - 1;
            displayQuantity(quantity);

    }

    /*
          ketika method ini dipanggil maka akan menampilkan tampilan orderSUmmary
            Memanggil fungsi method onClick submitOrder dari resource id button ORDER di layout

           */
    public void submitOrder(View v) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocoLate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocoLate.isChecked();

        int price = calculatePrice(hasWhippedCream ,hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        //Kirim ke Email dengan menggunakan Implicit Intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for : "+name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }

        displayMessage(priceMessage);


    }


    /*
         method mereset  jumlah kopi jadi 1
         */
    public void resetQuantity(View v) {
        displayQuantity(1);
        displayMessage("" + 1);

        Button btn = (Button) findViewById(R.id.orderBtn);
        btn.setEnabled(false);

    }


   /*
        method mendefinisikan jumlah kopi

        */

    private void displayQuantity(int numberOfCoffes) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffes);
        quantityTextView.setTextSize(18);


    }

    /*
        method perhitungan jumlah kopi

        */
    private int calculatePrice(boolean addWhippedCream , boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        if (addChocolate ){
            basePrice = basePrice + 2;
        }


        return quantity * basePrice;
    }


    /*
     Mendefinisikan dan menginisialisasi variabel display message menampilkan dalam bentuk
     TextView

     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
        orderSummaryTextView.setTextSize(20);
        orderSummaryTextView.setTextColor(Color.DKGRAY);

    }

    /*
     Menampilkan  Hasil Tampilan Order dengan parameter tipe data integer dan Boolean dari
     CheckBox

        *@param Price
        *@param addWhippedCream
        *@param addChocolate
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {

            String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\nAdded Whipped Cream : " + addWhippedCream;
        priceMessage += "\nAdded Chocolate : " + addChocolate;
        priceMessage += "\nQuantity :" + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n"+getString(R.string.thank_you) ;

        return priceMessage;
    }


}


