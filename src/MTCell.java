import java.awt.Color;

import javalib.worldimages.*;


/**
 * Represents a cell in the game world.
 */
class MTCell implements ICell {

  Color color;
  boolean flooded;


  MTCell(Color color, boolean flooded) {
    this.color = color;
    this.flooded = flooded;
  }


  /**
   * Draws the cell white, so that if called upon an empty cell
   * it would not be mistaken for a cell
   *
   * @return the image of the cell
   */
  public WorldImage drawCell() {
    return new RectangleImage(15, 15, OutlineMode.SOLID, this.color);
  }

  public boolean isNeighborFlooded() {
    return false;
  }

  public void floodCell(Color color) {
    // do nothing


  }


}