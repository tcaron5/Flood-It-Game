import java.util.ArrayList;

import javalib.impworld.*;

import java.awt.Color;

import javalib.worldimages.*;

import java.util.Random;
/**
 * Represents the game world.
 */
class FloodItWorld extends World {
  // All the cells of the game
  ArrayList<ArrayList<Cell>> board;
  int colorCount;
  int size;

  ArrayList<Cell> floodingList = new ArrayList<Cell>();

  ArrayList<Cell> beenThereList = new ArrayList<Cell>();

  // Number of clicks remaining before the player loses the game
  double stepsRemaining;
  ArrayList<Color> colorsList = new ArrayList<Color>();
  ArrayList<Color> colorsList2 = new ArrayList<Color>();
  Random rand = new Random();


  FloodItWorld(int size, int colorCount) {
    this.colorCount = colorCount;
    this.size = size;
    this.stepsRemaining = Math.floor(25 * ((2 * size) * colorCount) / 145);

    // This is where we create the list of colors that will be used in the game

    // This is where we add the colors to the list it only goes up to RGB 255 because we save the
    // all white color for the MTCell
    for (int i = 0; i < colorCount; i++) {
      colorsList.add(new Color(rand.nextInt(255),
              rand.nextInt(255), rand.nextInt(255)));
    }

    // Where we create the board of cells
    this.board = new ArrayList<ArrayList<Cell>>();
    for (int i = 0; i < size; i++) {
      ArrayList<Cell> row = new ArrayList<Cell>();
      for (int j = 0; j < size; j++) {
        row.add(new Cell(i, j, colorsList.get(rand.nextInt(colorCount))));


      }
      this.board.add(row);

    }


    // Where we initialize the neighbors of each cell in the game world
    // Creates new MTCell if there is no cell there.
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j == 0 && i == 0) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (j == size - 1 && i == size - 1) {
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);


        } else if (j == 0 && i == size - 1) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);


        } else if (j == size - 1 && i == 0) {
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);


        } else if (j == 0) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (j == size - 1) {
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (i == 0) {
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);

        } else if (i == size - 1) {
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);


        } else {
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);
        }


      }
    }

    this.board.get(0).get(0).flooded = true;
    System.out.println("bout to flood");
    System.out.println("true there is a flooded neighbor");
    System.out.println("flooding this board");
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        System.out.println(this.board.get(i).get(j).flooded);


      }
    }


    this.bigBang(this.size * 50, this.size * 50, 1);
  }


  /**
   * Draws the game world.
   *
   * @return the image of the game world
   */
  public WorldScene makeScene() {
    WorldScene scene = new WorldScene(this.size * 15, this.size * 15);


    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        scene.placeImageXY(this.board.get(i).get(j).drawCell(),
                this.board.get(i).get(j).x, this.board.get(i).get(j).y);
      }
    }
    this.colorsList2.add(this.board.get(0).get(0).color);


    scene.placeImageXY(new TextImage("Steps Remaining: "
                    + (stepsRemaining), this.size * 3, Color.BLACK),
            this.size * 21, this.size * 20);


    if (this.isGameOver() && stepsRemaining == 0) {
      return this.lastScene("SIKE! barely made it");
    }
    if (this.isGameOver() && stepsRemaining > 0) {
      return this.lastScene("you win");
    }
    if (stepsRemaining == 0 && !this.isGameOver()) {
      return this.lastScene("you lose");
    }


    return scene;


  }


  /**
   * Draws the last scene of the game.
   *
   * @param s the string to be displayed
   * @return the image of the last scene
   */
  public WorldScene lastScene(String s) {
    WorldScene scene = new WorldScene(this.size * 15, this.size * 15);

    scene.placeImageXY(new TextImage(s, this.size * 5, Color.BLACK),
            this.size * 21, this.size * 20);


    return scene;
  }

  /**
   * Handles mouse clicks.
   *
   * @param pos    the position of the click
   * @param button the button that was clicked
   */
  public void onMousePressed(Posn pos, String button) {

    stepsRemaining--;

    // This is where we set the neighbors of each cell in the game world to be the cells around it
    // in the game world or a new MTCell if there is no cell there.
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (j == 0 && i == 0) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (j == size - 1 && i == size - 1) {
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);

        } else if (j == 0 && i == size - 1) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);


        } else if (j == size - 1 && i == 0) {
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);


        } else if (j == 0) {
          this.board.get(i).get(j).left = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (j == size - 1) {
          this.board.get(i).get(j).right = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);

        } else if (i == 0) {
          this.board.get(i).get(j).top = new MTCell(Color.WHITE, false);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);

        } else if (i == size - 1) {
          this.board.get(i).get(j).bottom = new MTCell(Color.WHITE,
                  false);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);


        } else {
          this.board.get(i).get(j).left = this.board.get(i).get(j - 1);
          this.board.get(i).get(j).right = this.board.get(i).get(j + 1);
          this.board.get(i).get(j).top = this.board.get(i - 1).get(j);
          this.board.get(i).get(j).bottom = this.board.get(i + 1).get(j);
        }


      }
    }


    if (button.equals("LeftButton")) {
      System.out.println("clicked");
      //System.out.print("flooded - " + this.board.get(pos.x).get(pos.y).flooded + " ");
      //System.out.println("neighbor flood" + this.board.get(pos.x).get(pos.y).isNeighborFlooded());

      for (int i = 0; i < this.size; i++) {
        for (int j = 0; j < this.size; j++) {
          System.out.println("clicked" + pos.x + " " + pos.y + " "
                  + this.board.get(i).get(j).x + " "
                  + this.board.get(i).get(j).y);

          System.out.print("isClicked" + this.board.get(i).get(j).isClicked(pos) + " ");

          System.out.print("flooded - " + this.board.get(i).get(j).flooded + " ");
          //System.out.println("neighbor flood" + this.board.get(i).get(j).isNeighborFlooded());
          if (this.board.get(i).get(j).isClicked(pos)) {
            System.out.println("clicked" + i + " " + j);


            if (this.board.get(i).get(j).isClicked(pos)
                    && this.board.get(i).get(j).color != Color.WHITE
                    && !this.board.get(i).get(j).flooded) {
              colorsList2.add(this.board.get(i).get(j).color);
              this.board.get(0).get(0).color = this.board.get(i).get(j).color;


              // this important this.board.get(0).get(0).floodCell(this.board.get(i).get(j).color);
              System.out.println("clicked entered to" + i + " " + j);
            }
          }
        }
      }
    }
  }


  /**
   * Determines if the game is over.
   *
   * @return whether the game is over
   */
  public boolean isGameOver() {
    int counter = 0;

    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        if (this.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }
    return counter == this.size * this.size;
  }


  /**
   * Initializes the game world.
   */
  void initBoard() {

    FloodItWorld f = new FloodItWorld(this.size, this.colorCount);
    stepsRemaining = Math.floor(25 * ((2 * size) * colorCount) / 145);

    /*for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        this.board.get(i).get(j).flooded = false;
      }
    }*/

  }

  /**
   * Handles key events.
   *
   * @param key the key that was pressed
   */
  public void onKeyEvent(String key) {
    if (key.equals("r")) {
      initBoard();
    }
  }

  /**
   * Determines if the cell floods.
   *
   * @param xBoard the x coordinate of the cell
   * @param yBoard the y coordinate of the cell
   * @return whether the cell floods
   */
  public boolean floods(int xBoard, int yBoard) {
    if (xBoard > 0 && xBoard < this.size - 1 && yBoard > 0 && yBoard < this.size - 1) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard == 0 && yBoard == 0) {
      if (this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard == 0 && yBoard == this.size - 1) {
      if (this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded) {
        return true;
      }
    }
    if (xBoard == this.size - 1 && yBoard == 0) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard == this.size - 1 && yBoard == this.size - 1) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded) {
        return true;
      }
    }
    if (xBoard == 0 && yBoard > 0 && yBoard < this.size - 1) {
      if (this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard == this.size - 1 && yBoard > 0 && yBoard < this.size - 1) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard > 0 && xBoard < this.size - 1 && yBoard == 0) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard + 1).flooded) {
        return true;
      }
    }
    if (xBoard > 0 && xBoard < this.size - 1 && yBoard == this.size - 1) {
      if (this.board.get(xBoard - 1).get(yBoard).flooded
              || this.board.get(xBoard + 1).get(yBoard).flooded
              || this.board.get(xBoard).get(yBoard - 1).flooded) {
        return true;
      }
    }

    return false;

  }

  /**
   * Handles tick events.
   */
  public void onTick() {


    for (int k = 0; k < this.size; k++) {
      for (int l = 0; l < this.size; l++) {
        if (!this.board.get(k).get(l).flooded
                && this.board.get(k).get(l).color == this.board.get(0).get(0).color
                && this.floods(k, l)
                && this.board.get(0).get(0) != this.board.get(k).get(l)) {
          //System.out.println("check floods");
          this.board.get(k).get(l).flooded = true;
          this.board.get(k).get(l).color = (this.board.get(0).get(0).color);

        }
      }
    }


    for (int k = 0; k < this.size; k++) {
      for (int l = 0; l < this.size; l++) {
        if (this.board.get(k).get(l).flooded
                && this.board.get(0).get(0) != this.board.get(k).get(l)) {
          //System.out.println("check floods");
          this.board.get(k).get(l).flooded = true;
          this.board.get(k).get(l).color = (this.board.get(0).get(0).color);
        }
      }
    }


  }


}