package com.example.chaicoffee;

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
 * This app sends an order form to order tea and coffee through email.
 * Base Price of Tea->Rs.10
 * Add On of Masala->Rs.2
 * Add On of Ginger->Rs.3
 *
 * Base Price of Coffee->Rs.5
 * Add On of Whipped Cream->Rs.1
 * Add On of Chocolate->Rs.2
 */
public class MainActivity extends AppCompatActivity {
    int chai_quantity=1;
    int coffee_quantity=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Functionality of submit order button
    public void submitOrder(View view) {
        //Getting "Name" of the Customer from EditTextfield
        EditText nameField=(EditText)findViewById(R.id.name_field);
        String name=nameField.getText().toString();

        //Getting "Email ID" of the Customer from EditTextfield
        EditText emailField=(EditText)findViewById(R.id.email_field);
        String email[]=new String[1];
        email[0]=emailField.getText().toString();

        //To Check if Customer wants Masala Add On to Chai
        CheckBox masalaCheckBox = (CheckBox)findViewById(R.id.masala_checkbox);
        boolean hasMasala=masalaCheckBox.isChecked();

        //To Check if Customer wants Ginger Add On to Chai
        CheckBox gingerCheckBox = (CheckBox)findViewById(R.id.ginger_checkbox);
        boolean hasGinger=gingerCheckBox.isChecked();

        //To Check if Customer wants WhippedCream Toppings to Coffee
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream=whippedCreamCheckBox.isChecked();

        //To Check if Customer wants Chocolate Toppings to Coffee
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate=chocolateCheckBox.isChecked();

        //To Send The invoice through E-Mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, email);//providing the email address to submit
        intent.putExtra(Intent.EXTRA_SUBJECT, "Chai Coffee order for "+name);//providing subject of E-Mail
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(hasWhippedCream,hasChocolate,name,hasMasala,hasGinger) );//providing Invoice as Message Body of the email
        //to check if there is an app in phone to handle Email
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    //function to create order summary
    private String createOrderSummary(boolean hasWhippedCream,boolean hasChocolate,String name,boolean hasMasala,boolean hasGinger){
        String priceMessage="Name:"+name+"\n";
        priceMessage+="\nTea:\t Quantity:"+chai_quantity+"\n";
        if(hasGinger || hasMasala)
            priceMessage+="Add Ons:\n";
        if(hasMasala)
            priceMessage+="Masala\n";
        if(hasGinger)
            priceMessage+="Ginger\n";
        priceMessage+="\nCoffee:\t Quantity:"+coffee_quantity+"\n";
        if(hasWhippedCream || hasChocolate)
            priceMessage+="Toppings:\n";
        if(hasWhippedCream)
            priceMessage+="Whipped Cream\n";
        if(hasChocolate)
            priceMessage+="Chocolate\n";
        priceMessage+="\nTotal Price: ₹"+calculatePrice(hasWhippedCream,hasChocolate,hasMasala,hasGinger)+"\nThank You!!";
        return priceMessage;
    }

    //function to calculate final price of the order
    public int calculatePrice(boolean hasWhippedCream,boolean hasChocolate,boolean hasMasala,boolean hasGinger)
    {
        int chai_price=10,coffee_price=5;//Base Price of Tea and Coffee
        if(hasMasala)
            chai_price+=2;//Adding Rs.2 for Masala Add On to Chai
        if(hasGinger)
            chai_price+=3;//Adding Rs.3 for Ginger Add-On to Chai
        if(hasWhippedCream)
            coffee_price+=1;//Adding Rs.1 for Whipped Cream Toppings to Coffee
        if(hasChocolate)
            coffee_price+=2;//Adding Rs.2 for Whipped Cream Toppings to Coffee
        return (coffee_quantity*coffee_price)+(chai_quantity*chai_price); //returning final price
    }

    //to increment quantity of chai
    public void incrementChai(View view)
    {
        //to display a message if customer tries to exceed upper limit ie 100
        if(chai_quantity==100){
            Toast.makeText(this,"You cannot have more than 100 cups of tea",Toast.LENGTH_SHORT).show();
            return;
        }
        chai_quantity++;//increments of quantity of chai
        displayChai(chai_quantity);//to display updated quantity of chai
    }

    //to decerement quantity of chai
    public void decrementChai(View view)
    {
        //to display a message if customer tries to exceed lower limit ie 1
        if(chai_quantity==1) {
            Toast.makeText(this,"You cannot have less than 1 cup of tea",Toast.LENGTH_SHORT).show();
            return;
        }
        chai_quantity--;//to decrement quantity of chai
        displayChai(chai_quantity);//to display updated quantity of chai
    }
    //to increment quantity of coffee
    public void incrementCoffee(View view)
    {
        //to display a message if customer tries to exceed upper limit ie 100
        if(coffee_quantity==100){
            Toast.makeText(this,"You cannot have more than 100 cups of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        coffee_quantity++;//increments quantity of coffee
        displayCoffee(coffee_quantity);//displays the updated quantity
    }

    //to decrement quantity of coffee
    public void decrementCoffee(View view)
    {
        //to display a message if customer tries to exceed lower limit ie 1
        if(coffee_quantity==1) {
            Toast.makeText(this,"You cannot have less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        coffee_quantity--;//decrements quantity of coffee
        displayCoffee(coffee_quantity);//displays the updated quantity
    }

    //to display the quantity of Chai
    private void displayChai(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.chaiquantity_text_view);
        quantityTextView.setText("" + number);
    }

    //to display the quantity of Coffee
    private void displayCoffee(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    //to reset everything
    public void resetOrder(View view)
    {
        //to reset the name field
        EditText nameField=(EditText)findViewById(R.id.name_field);
        nameField.setText("");

        //to reset the E-Mail Id field
        EditText emailField=(EditText)findViewById(R.id.email_field);
        emailField.setText("");

        //to reset "Masala" Add On in Tea
        CheckBox masalaCheckBox = (CheckBox)findViewById(R.id.masala_checkbox);
        if(masalaCheckBox.isChecked())
            masalaCheckBox.toggle();

        //to reset "Ginger" Add On in Tea
        CheckBox gingerCheckBox = (CheckBox)findViewById(R.id.ginger_checkbox);
        if(gingerCheckBox.isChecked())
            gingerCheckBox.toggle();

        //to reset "Whipped Cream" Add On in Coffee
        CheckBox whippedCreamCheckBox = (CheckBox)findViewById(R.id.whipped_cream_checkbox);
        if(whippedCreamCheckBox.isChecked())
            whippedCreamCheckBox.toggle();

        //to reset "Chocolate" Add On in Coffee
        CheckBox chocolateCheckBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        if(chocolateCheckBox.isChecked())
            chocolateCheckBox.toggle();

        chai_quantity=1;//reset quantity of chai
        coffee_quantity=1;//reset quantity of coffee
        
        displayChai(chai_quantity);//display the reset value of tea at counter
        displayCoffee(coffee_quantity);//display the reset value of coffee at counter
    }
}