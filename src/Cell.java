import java.awt.Color;

import javalib.worldimages.*;

/**
 * Represents a cell in the game world.
 */
class Cell implements ICell {
  //In logical coordinates, with the origin in the top-left corner of the screen
  int x;
  int y;
  Color color;
  boolean flooded;
  // the four adjacent cells to this one
  ICell top;
  ICell left;
  ICell right;
  ICell bottom;

  Cell(int x, int y, Color color) {
    this.x = (x * 15) + 8;
    this.y = (y * 15) + 8;
    this.color = color;
    this.flooded = false;

  }

  /**
   * Draws the cell.
   *
   * @return the image of the cell
   */
  public WorldImage drawCell() {
    return new RectangleImage(15, 15, OutlineMode.SOLID, this.color);
  }


  /**
   * Determines if the cell is clicked.
   *
   * @param pos the position of the click
   * @return whether the cell is clicked
   */
  boolean isClicked(Posn pos) {
    System.out.println("x cord" + pos.x);
    System.out.println("y cord" + pos.y);
    System.out.println("x cord" + (pos.x + 8));
    System.out.println("x cord" + (pos.x - 8));
    System.out.println("y cord" + (pos.y + 8));
    System.out.println("y cord" + (pos.y - 8));


    return (pos.x + 8 > this.x) && (pos.x - 8 < this.x)
            && (pos.y + 8 > this.y) && (pos.y - 8 < this.y);
  }

  /**
   * Floods the current cell.
   */
  public void floodCell(Color color) {
    this.color = color;
    this.flooded = true;
    /*this.top.floodNeighborCell(color);
    this.left.floodNeighborCell(color);
    this.right.floodNeighborCell(color);
    this.bottom.floodNeighborCell(color);*/

  }

  /**
   * Floods the neighboring cell.
   *
   * @return whether any neighboring cell is flooded
   */
  public boolean isNeighborFlooded() {
    System.out.println("flood top" + this.top.flooded);
    System.out.println("flood left" + this.left.flooded);
    System.out.println("flood right" + this.right.flooded);
    System.out.println("flood bottom" + this.bottom.flooded);

    return this.left.flooded || this.right.flooded || this.top.flooded || this.bottom.flooded;
  }


}
