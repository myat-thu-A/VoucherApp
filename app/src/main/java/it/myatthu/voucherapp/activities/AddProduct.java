package it.myatthu.voucherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import it.myatthu.voucherapp.R;
import it.myatthu.voucherapp.databinding.ActivityAddProductBinding;

public class AddProduct extends AppCompatActivity {
    private ActivityAddProductBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListeners();
    }

    private void initListeners() {
        binding.btAdd.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            String price = binding.etPrice.getText().toString();
            String quantity = binding.etQuantity.getText().toString();
            if(!name.isEmpty() && !price.isEmpty() && !quantity.isEmpty()) {
                Double priceDouble = Double.valueOf(price);
                Integer quantityInt = Integer.valueOf(quantity);
                Intent intent = new Intent();
                intent.putExtra("name", name);
                intent.putExtra("price", priceDouble);
                intent.putExtra("quantity", quantityInt);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(this, "Please fill all edit text", Toast.LENGTH_SHORT).show();
            }
        });
    }
}