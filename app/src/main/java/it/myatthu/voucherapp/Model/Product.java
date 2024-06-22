package it.myatthu.voucherapp.Model;

public record Product(
        String imageUrl,
        String name,
        Double price,
        Integer quantity
) {
}
