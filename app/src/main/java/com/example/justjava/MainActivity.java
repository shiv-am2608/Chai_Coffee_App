package com.example.justjava;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();
        displayMessage(createOrderSummary(hasWhippedCream,hasChocolate));
    }
    private String createOrderSummary(boolean hasWhippedCream,boolean hasChocolate){
        String priceMessage="Name:Shivam Agrawal\nAdd Whipped Cream?"+hasWhippedCream+"\nAdd Chocolate?"+hasChocolate+"\n";
        priceMessage+="Quantity:"+quantity+"\n";
        priceMessage+="Total Price: ₹"+calculatePrice()+"\nThank You!!";
        return priceMessage;
    }
    public int calculatePrice()
    {
        return quantity*5;
    }
    public void increment(View view)
    {
        quantity++;
        display(quantity);
    }
    public void decrement(View view)
    {
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
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}