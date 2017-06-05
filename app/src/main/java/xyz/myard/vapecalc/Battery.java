package xyz.myard.vapecalc;

class Battery {
    String name;
    //String brand;
    String size;

    Integer capacity;
    Integer current;

    Battery(String name, String brand, String size, int capacity, int current){
        this.name = name;
        //this.brand = brand;
        this.size = size;
        this.capacity = capacity;
        this.current = current;
    }
}
