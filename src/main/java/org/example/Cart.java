package org.example;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Cart {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProductById(int id) {
        return products.removeIf(product -> product.getId() == id);
    }

    public void clear() {
        products.clear();
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products);
    }

    public double getTotalPrice() {
        double total = 0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    @Override
    public String toString() {
        if (products.isEmpty()) {
            return "Кошик порожній.";
        }
        StringBuilder sb = new StringBuilder("Кошик містить:\n");
        for (Product product : products) {
            sb.append(product.toString()).append("\n");
        }
        sb.append(String.format("Загальна вартість: %.2f", getTotalPrice()));
        return sb.toString();
    }
}