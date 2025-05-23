package utils;

public class Items {
   // Fields to store item details
   private String name;
   private int value;
   private int weight;

   // Constructor to initialize an item with a name, value, and weight.
   public Items(String name, int value, int weight) {
      this.name = name;
      this.value = value;
      this.weight = weight;
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
}