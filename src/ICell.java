import java.awt.Color;

// the reasoning for this is that the game is a 2D grid of cells and for cells on the corner if we
// were to ask for there top, left, right, or bottom cell we would get an error. So we need to
// represent the cells that are not there with a new class and not j null.

/**
 * Replicates the FloodIt game, where it creates a 2D grid of
 * cells and the user has to flood the whole board with one color.
 */
interface ICell {


  boolean flooded = false;

  void floodCell(Color color);

}