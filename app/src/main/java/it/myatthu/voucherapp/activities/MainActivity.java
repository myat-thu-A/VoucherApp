package it.myatthu.voucherapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import it.myatthu.voucherapp.Adapter.MainAdapter;
import it.myatthu.voucherapp.Model.Product;
import it.myatthu.voucherapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private List<Product> productList = new ArrayList<>();
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initUI();
        initListeners();
    }

    private void initListeners() {
        binding.fabAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddProduct.class);
            startActivityForResult(intent, 123);
        });

        mainAdapter.setOnDeleteListener(product -> {
            productList.remove(product);
            updateListDate(productList);
        });

        binding.etFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                List<Product> filterProducts = List.copyOf(productList);

                if (!s.toString().isEmpty()) {
                    filterProducts = filterProducts.stream().filter(product ->
                            product.name().contains(s.toString())
                    ).collect(Collectors.toList());
                }
                mainAdapter.setProducts(filterProducts);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            Double price = data.getDoubleExtra("price", 0.0);
            Integer quantity = data.getIntExtra("quantity", 0);
            Product newProduct = new Product("", name, price, quantity);
            productList.add(newProduct);
            updateListDate(productList);
        }
    }

    private void updateListDate(List<Product> productList) {
        mainAdapter.setProducts(productList);
        Double totalAmount = 0.0;
        for (Product product : productList) {
            totalAmount += (product.price() * product.quantity());
        }
        binding.tvTotalAmount.setText(totalAmount.toString());
    }

    private void initUI() {
        mainAdapter = new MainAdapter();
        binding.rvProducts.setAdapter(mainAdapter);
        binding.rvProducts.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter.setProducts(productList);
    }
}