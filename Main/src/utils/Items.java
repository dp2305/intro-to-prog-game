package utils;

public class Items {
   // Fields to store item details
   private String name;
   private int value;
   private int weight;
   // Function number is what the item provides, eg. for ammo, it is the number of bullets, for healing items, it is the amount of health it heals
   private int functionNumber;

   // Constructor to initialize an item with a name, value, and weight.
   public Items(String name, int value, int weight, int functionNumber) {
      this.name = name;
      this.value = value;
      this.weight = weight;
      this.functionNumber = functionNumber;
   }

   // Gets
   public String getName() {
      return name;
   }

   public int getValue() {
      return value;
   }

   public int getWeight() {
      return weight;
   }

   public int getFunctionNumber() {
      return functionNumber;
   }

   // Sets
   public void setName(String name) {
      this.name = name;
   }

   public void setValue(int value) {
    this.value = value;
   }

   public void setWeight(int weight) {
      this.weight = weight;
   }

   public void setFunctionNumber(int functionNumber) {
      this.functionNumber = functionNumber;
   }
}