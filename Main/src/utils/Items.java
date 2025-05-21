package utils;

public class Items {
   private String name;
   private int value;
   private int weight;

   public Items(String name, int value, int weight) {
      this.name = name;
      this.value = value;
      this.weight = weight;
   }

   public String getName() {
      return name;
   }

   public int getValue() {
      return value;
   }

   public int getWeight() {
      return weight;
   }

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