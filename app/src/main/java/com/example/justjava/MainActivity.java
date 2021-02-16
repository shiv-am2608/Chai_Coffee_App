package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField.getText().toString();
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, "xyz");
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for "+name);
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(hasWhippedCream,hasChocolate,name) );
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        //displayMessage(createOrderSummary(hasWhippedCream,hasChocolate,name));
    }
    private String createOrderSummary(boolean hasWhippedCream,boolean hasChocolate,String name){
        String priceMessage="Name:"+name+"\nAdd Whipped Cream?"+hasWhippedCream+"\nAdd Chocolate?"+hasChocolate+"\n";
        priceMessage+="Quantity:"+quantity+"\n";
        priceMessage+="Total Price: ₹"+calculatePrice(hasWhippedCream,hasChocolate)+"\nThank You!!";
        return priceMessage;
    }
    public int calculatePrice(boolean hasWhippedCream,boolean hasChocolate)
    {
        int price=5;
        if(hasWhippedCream)
            price+=1;
        if(hasChocolate)
            price+=2;
        return quantity*price;
    }
    public void increment(View view)
    {
        if(quantity==100){
            Toast.makeText(this,"You cannot have more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }
    public void decrement(View view)
    {
        if(quantity==1) {
            Toast.makeText(this,"You cannot have less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    /**
     * This method displays the given text on the screen.
     */
    /*To display order Summary in-app
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }*/
}