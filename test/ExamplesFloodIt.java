import java.awt.Color;

import javalib.worldimages.*;

import org.junit.Assert;
import org.junit.Test;



/**
 * Tests for the FloodIt game.
 */
public class ExamplesFloodIt {


  // test the drawCell method

  FloodItWorld f1 = new FloodItWorld(5, 3);


  FloodItWorld f2 = new FloodItWorld(10, 5);

  FloodItWorld world = new FloodItWorld(2, 4);
  FloodItWorld f = new FloodItWorld(10, 5);

  @Test
  public void testDrawCell() {
    WorldImage cellImage = new RectangleImage(15, 15, OutlineMode.SOLID, Color.RED);
    Assert.assertEquals(new Cell(0, 0, Color.RED).drawCell(), cellImage);
    Assert.assertEquals(new Cell(0, 0, Color.BLUE).drawCell(),
            new RectangleImage(15, 15,
                    OutlineMode.SOLID, Color.BLUE));
    Assert.assertEquals(new Cell(0, 0, Color.GREEN).drawCell(),
            new RectangleImage(15, 15,
                    OutlineMode.SOLID, Color.GREEN));
    Assert.assertEquals(new Cell(0, 0, Color.YELLOW).drawCell(),
            new RectangleImage(15, 15,
                    OutlineMode.SOLID, Color.YELLOW));
    Assert.assertEquals(new Cell(0, 4, Color.ORANGE).drawCell(),
            new RectangleImage(15, 15,
                    OutlineMode.SOLID, Color.ORANGE));

  }

  @Test
  public void testMTCell() {
    WorldImage cellImage = new RectangleImage(15, 15, OutlineMode.SOLID, Color.WHITE);
    Assert.assertEquals(new MTCell(Color.WHITE, false).drawCell(), cellImage);
    Assert.assertEquals(new MTCell(Color.WHITE, true).drawCell(),
            new RectangleImage(15, 15,
                    OutlineMode.SOLID, Color.WHITE));

  }

  @Test
  public void testLayout() {

    FloodItWorld world = new FloodItWorld(30, 30);

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size - 1; j++) {
        Assert.assertEquals(world.board.get(i).get(j + 1).left, world.board.get(i).get(j));
      }
    }

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size - 1; j++) {
        Assert.assertEquals(world.board.get(i).get(j + 1), world.board.get(i).get(j).right);
      }
    }

    for (int i = 0; i < world.size - 1; i++) {
      for (int j = 0; j < world.size; j++) {
        Assert.assertEquals(world.board.get(i + 1).get(j).top, world.board.get(i).get(j));
      }
    }

    for (int i = 0; i < world.size - 1; i++) {
      for (int j = 0; j < world.size; j++) {
        Assert.assertEquals(world.board.get(i).get(j).bottom, world.board.get(i + 1).get(j));
      }
    }
  }


  @Test
  public void testIsClicked() {
    Assert.assertFalse(f1.board.get(0).get(0).isClicked(new Posn(0, 0)));
    Assert.assertTrue(f1.board.get(0).get(0).isClicked(new Posn(1, 1)));
    Assert.assertFalse(f1.board.get(1).get(0).isClicked(new Posn(15, 15)));
    Assert.assertFalse(f1.board.get(0).get(1).isClicked(new Posn(16, 16)));
    Assert.assertFalse(f1.board.get(1).get(0).isClicked(new Posn(14, 14)));
    Assert.assertFalse(f1.board.get(1).get(0).isClicked(new Posn(15, 15)));
    Assert.assertFalse(f1.board.get(0).get(1).isClicked(new Posn(16, 16)));
    Assert.assertTrue(f1.board.get(0).get(0).isClicked(new Posn(14, 14)));

  }


  @Test
  public void testFloodItWorld() {
    f1.bigBang(75, 75, 0.1);
    f2.bigBang(150, 150, 0.1);
    world.bigBang(30, 30, 0.1);
    f.bigBang(150, 150, 0.1);
  }

  @Test
  public void testFloodCell() {
    f1.board.get(0).get(0).color = (Color.BLUE);
    f1.board.get(0).get(1).color = (Color.BLUE);
    f1.board.get(0).get(0).floodCell(Color.BLUE);
    f1.board.get(1).get(1).color = (Color.RED);
    f1.board.get(1).get(1).flooded = false;


    Assert.assertTrue(f1.board.get(0).get(0).flooded);
    Assert.assertEquals(new Cell(0, 0, Color.BLUE).color, Color.BLUE);
    new Cell(0, 0, Color.RED).floodCell(Color.BLUE);
    Assert.assertFalse(f1.board.get(1).get(1).flooded);
    Assert.assertEquals(new Cell(1, 1, Color.RED).color, Color.RED);
  }

  @Test
  public void testMakeSceneAlpha() {
    Assert.assertEquals(f1.makeScene().width, 75);
    Assert.assertEquals(f1.makeScene().height, 75);
    Assert.assertEquals(f2.makeScene().width, 150);
    Assert.assertEquals(f2.makeScene().height, 150);
  }


  @Test
  public void testOnTick() {

    //testing a 2x2 board

    FloodItWorld world = new FloodItWorld(2, 2);
    world.board.get(0).get(0).color = Color.RED;
    world.board.get(0).get(1).color = Color.GREEN;
    world.board.get(1).get(0).color = Color.BLUE;
    world.board.get(1).get(1).color = Color.YELLOW;
    world.board.get(0).get(0).flooded = true;


    FloodItWorld game = new FloodItWorld(5, 3);
    // set board to all same color except for one on the edge
    for (int i = 0; i < game.size; i++) {
      for (int j = 0; j < game.size; j++) {
        if (i == 0 && j == 0) {
          game.board.get(i).set(j, new Cell(i, j, Color.RED));
        } else if (i == 1) {
          game.board.get(i).set(j, new Cell(i, j, Color.GREEN));
        } else if (i == 2) {
          game.board.get(i).set(j, new Cell(i, j, Color.BLUE));
        } else if (i == 3) {
          game.board.get(i).set(j, new Cell(i, j, Color.YELLOW));
        } else if (i == 4) {
          game.board.get(i).set(j, new Cell(i, j, Color.RED));
        } else {
          game.board.get(i).set(j, new Cell(i, j, Color.BLUE));
        }
      }
    }

    game.board.get(0).get(0).flooded = true;

    game.onMousePressed(new Posn(game.board.get(1).get(0).x, game.board.get(1).get(0).y)
            , "LeftButton");
    game.onTick();
    Assert.assertTrue(game.board.get(0).get(0).flooded);
    Assert.assertEquals(game.board.get(0).get(0).color, Color.GREEN);
    Assert.assertTrue(game.board.get(1).get(0).flooded);
    Assert.assertEquals(game.board.get(1).get(0).color, Color.GREEN);
    Assert.assertFalse(game.board.get(2).get(0).flooded);
    Assert.assertEquals(game.board.get(2).get(0).color, Color.BLUE);
    Assert.assertFalse(game.board.get(3).get(0).flooded);
    Assert.assertEquals(game.board.get(3).get(0).color, Color.YELLOW);

    game.onMousePressed(new Posn(game.board.get(2).get(0).x, game.board.get(2).get(0).y)
            , "LeftButton");

    game.onTick();
    Assert.assertTrue(game.board.get(0).get(0).flooded);
    Assert.assertEquals(game.board.get(0).get(0).color, Color.BLUE);
    Assert.assertTrue(game.board.get(1).get(0).flooded);
    Assert.assertEquals(game.board.get(1).get(0).color, Color.BLUE);
    Assert.assertTrue(game.board.get(2).get(0).flooded);
    Assert.assertEquals(game.board.get(2).get(0).color, Color.BLUE);
    Assert.assertFalse(game.board.get(3).get(0).flooded);
    Assert.assertEquals(game.board.get(3).get(0).color, Color.YELLOW);
    Assert.assertFalse(game.board.get(4).get(0).flooded);
    Assert.assertEquals(game.board.get(4).get(0).color, Color.RED);

    game.onMousePressed(new Posn(game.board.get(3).get(0).x, game.board.get(3).get(0).y)
            , "LeftButton");
    game.onTick();

    Assert.assertTrue(game.board.get(0).get(0).flooded);
    Assert.assertEquals(game.board.get(0).get(0).color, Color.YELLOW);
    Assert.assertTrue(game.board.get(1).get(0).flooded);
    Assert.assertEquals(game.board.get(1).get(0).color, Color.YELLOW);
    Assert.assertTrue(game.board.get(2).get(0).flooded);
    Assert.assertEquals(game.board.get(2).get(0).color, Color.YELLOW);
    Assert.assertTrue(game.board.get(3).get(0).flooded);
    Assert.assertEquals(game.board.get(3).get(0).color, Color.YELLOW);
    Assert.assertFalse(game.board.get(4).get(0).flooded);
    Assert.assertEquals(game.board.get(4).get(0).color, Color.RED);

    game.onMousePressed(new Posn(game.board.get(4).get(0).x, game.board.get(4).get(0).y)
            , "LeftButton");
    game.onTick();

    Assert.assertTrue(game.board.get(0).get(0).flooded);
    Assert.assertEquals(game.board.get(0).get(0).color, Color.RED);
    Assert.assertTrue(game.board.get(1).get(0).flooded);
    Assert.assertEquals(game.board.get(1).get(0).color, Color.RED);
    Assert.assertTrue(game.board.get(2).get(0).flooded);
    Assert.assertEquals(game.board.get(2).get(0).color, Color.RED);
    Assert.assertTrue(game.board.get(3).get(0).flooded);
    Assert.assertEquals(game.board.get(3).get(0).color, Color.RED);
    Assert.assertTrue(game.board.get(4).get(0).flooded);
    Assert.assertEquals(game.board.get(4).get(0).color, Color.RED);
    Assert.assertTrue(game.board.get(4).get(0).flooded);
    Assert.assertEquals(game.board.get(4).get(4).color, Color.RED);

    for (int i = 0; i < game.size; i++) {
      for (int j = 0; j < game.size; j++) {
        if (game.board.get(i).get(j).flooded) {
          Assert.assertTrue(game.board.get(i).get(j).flooded);
          Assert.assertEquals(game.board.get(i).get(j).color, Color.RED);
        }
      }
    }

    // check initial state

    int counter = 0;

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size; j++) {
        if (world.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }

    Assert.assertEquals(counter, 1);

    //checking how many cells are flooded if 2 adjacent cells are the same color
    //before calling onTick.

    world.board.get(0).get(1).color = Color.RED;

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size; j++) {
        if (world.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }

    Assert.assertEquals(counter, 2);

    //checking amount of flooded cells after a tick

    world.onTick();

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size; j++) {
        if (world.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }

    Assert.assertEquals(counter, 4);

    //checking amount of flooded cells when more after another tick when more
    //adjacent cells are the same color

    world.board.get(1).get(0).color = Color.RED;

    world.onTick();

    for (int i = 0; i < world.size; i++) {
      for (int j = 0; j < world.size; j++) {
        if (world.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }

    Assert.assertEquals(counter, 7);

    //testing monochromatic 16x16 board

    FloodItWorld world2 = new FloodItWorld(16, 16);

    int counter2 = 0;

    world2.board.get(0).get(0).flooded = true;

    for (int i = 0; i < world2.size; i++) {
      for (int j = 0; j < world2.size; j++) {
        world2.board.get(i).get(j).color = Color.RED;
      }
    }

    for (int i = 0; i < world2.size; i++) {
      for (int j = 0; j < world2.size; j++) {
        if (world2.board.get(i).get(j).flooded) {
          counter2++;
        }
      }
    }

    Assert.assertEquals(counter2, 1);

    world2.onTick();
    counter = 0;
    for (int i = 0; i < world2.size; i++) {
      for (int j = 0; j < world2.size; j++) {
        if (world2.board.get(i).get(j).flooded) {
          counter++;
        }
      }
    }

    Assert.assertEquals(counter, 256);

  }


  @Test
  public void testSize() {
    Assert.assertEquals(f1.size, 5);
    Assert.assertEquals(f2.size, 10);

  }


  @Test
  public void testIsGameOver() {
    // Check that the game is not over initially
    Assert.assertFalse(f1.isGameOver());
    Assert.assertFalse(f2.isGameOver());

    // Flood the entire board with one color and check that the game is over

    for (int i = 0; i < f1.size; i++) {
      for (int j = 0; j < f1.size; j++) {
        f1.board.get(i).get(j).color = Color.RED;
        f1.board.get(i).get(j).flooded = true;
      }
    }

    Assert.assertTrue(f1.isGameOver());

    for (int i = 0; i < f2.size; i++) {
      for (int j = 0; j < f2.size; j++) {
        f2.board.get(i).get(j).color = Color.GREEN;
        f2.board.get(i).get(j).flooded = true;
      }
    }

    Assert.assertTrue(f2.isGameOver());
  }

  }