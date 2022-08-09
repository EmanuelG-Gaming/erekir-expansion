package erekir.room;

public class Room{
   /** Position, in tiles. */
   public int x, y;
   /** Dimension, in tiles. */
   public int width, height;
   
   public Room(int x, int y, int width, int height) {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
   }
}