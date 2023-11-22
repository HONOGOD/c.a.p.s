package cpas.model;

import java.util.ArrayList;
import java.util.List;

public class Food {
    private String storeName;  // Corresponds to store_namecol
    private String storeAddress;  // Corresponds to store_address
    private String storeTell;  // Corresponds to store_tell
    private List<Menu> menus = new ArrayList<>();

    // Getter and setter for storeName
    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    // Getter and setter for storeAddress
    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    // Getter and setter for storeTell
    public String getStoreTell() {
        return storeTell;
    }

    public void setStoreTell(String storeTell) {
        this.storeTell = storeTell;
    }

    // Getter for menus
    public List<Menu> getMenus() {
        return menus;
    }

    // Method to add menu to the menus list
    public void addMenu(String menu, String price) {
        this.menus.add(new Menu(menu, price));
    }

    public static class Menu {
        private String name;  // Corresponds to store_menu
        private String price;  // Corresponds to menu_price

        public Menu(String name, String price) {
            this.name = name;
            this.price = price;
        }

        // Getter for name
        public String getName() {
            return name;
        }

        // Getter for price
        public String getPrice() {
            return price;
        }
    }
}
