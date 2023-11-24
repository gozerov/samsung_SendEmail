package ru.gozerov.sendemail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ru.gozerov.sendemail.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.commentEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendEmail();
                    return true;
                }
                else
                    return false;
            }
        });

        binding.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        ArrayList<String> productList = new ArrayList<>();
        if (binding.fruits.isChecked())
            productList.add("Fruits");
        if (binding.vegetables.isChecked())
            productList.add("Vegetables");
        if (binding.freezing.isChecked())
            productList.add("Freezing");
        if (binding.milk.isChecked())
            productList.add("Milk");
        if (binding.meat.isChecked())
            productList.add("Meat");
        if (binding.cakes.isChecked())
            productList.add("Cakes");
        StringBuilder products = new StringBuilder();
        for (String s : productList) {
            products.append(s);
            products.append("\n");
        }
        String shipment = (binding.radioShip.isChecked()) ? "Ship" : "Pickup";
        String comment = "Comment: " + binding.commentEditText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"google@mail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Order");
        intent.putExtra(Intent.EXTRA_TEXT, "Product list:\n" + products + "\n\n" + comment + "\n\n" + shipment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}