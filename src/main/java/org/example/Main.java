package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final List<Product> catalog = new ArrayList<>();
    private static final List<Order> orderHistory = new ArrayList<>();
    private static final Cart cart = new Cart();

    public static void main(String[] args) {
        // 1. Ініціалізація категорій (як у презентації)
        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        // 2. Ініціалізація товарів (як у презентації)
        catalog.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Видалити товар з кошика");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Історія замовлень");
            System.out.println("7 - Пошук товарів за назвою або категорією");
            System.out.println("0 - Вийти");
            System.out.print("Ваш вибір: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть числове значення.");
                continue;
            }

            switch (choice) {
                case 1:
                    printCatalog(catalog);
                    break;

                case 2:
                    System.out.print("Введіть ID товару для додавання до кошика: ");
                    try {
                        int addId = Integer.parseInt(scanner.nextLine());
                        Product foundProduct = findProductById(addId);
                        if (foundProduct != null) {
                            cart.addProduct(foundProduct);
                            System.out.println("Товар успішно додано до кошика!");
                        } else {
                            System.out.println("Товар з таким ID не знайдено");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некоректний ID товару.");
                    }
                    break;

                case 3:
                    System.out.println(cart);
                    break;

                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Немає чого видаляти.");
                        break;
                    }
                    System.out.println(cart);
                    System.out.print("Введіть ID товару для видалення з кошика: ");
                    try {
                        int removeId = Integer.parseInt(scanner.nextLine());
                        if (cart.removeProductById(removeId)) {
                            System.out.println("Товар успішно видалено з кошика!");
                        } else {
                            System.out.println("Товар з таким ID не знайдено у кошику.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Некоректний ID.");
                    }
                    break;
                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(order);
                        cart.clear();
                    }
                    break;

                case 6:
                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("\n--- ІСТОРІЯ ЗАМОВЛЕНЬ ---");
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.printf("\nЗамовлення #%d:\n", (i + 1));
                            System.out.println(orderHistory.get(i));
                        }
                    }
                    break;

                case 7:
                    System.out.print("Введіть пошуковий запит (назва або категорія): ");
                    String query = scanner.nextLine().trim().toLowerCase();
                    List<Product> results = new ArrayList<>();
                    for (Product product : catalog) {
                        boolean matchName = product.getName().toLowerCase().contains(query);
                        boolean matchCategory = product.getCategory() != null &&
                                product.getCategory().getName().toLowerCase().contains(query);
                        if (matchName || matchCategory) {
                            results.add(product);
                        }
                    }

                    if (results.isEmpty()) {
                        System.out.println("Товарів за запитом '" + query + "' не знайдено.");
                    } else {
                        System.out.println("Результати пошуку:");
                        printCatalog(results);
                    }
                    break;

                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;

                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                    break;
            }
        }
    }

    private static void printCatalog(List<Product> products) {
        for (Product product : products) {
            System.out.println(product);
        }
    }

    private static Product findProductById(int id) {
        for (Product product : catalog) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }
}