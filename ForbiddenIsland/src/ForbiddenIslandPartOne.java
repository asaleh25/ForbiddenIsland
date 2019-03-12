//
//// Assignment 9
//// Abdullah Al-Saleh
//// aalsaleh
//// Wu, Guanting
//// wukyle
//
//import java.util.*;
//import tester.*;
//import javalib.impworld.*;
//import java.awt.Color;
//import javalib.worldimages.*;
//
//// iterator for IList
//class IListIterator<T> implements Iterator<T> {
//  IList<T> list;
//
//  IListIterator(IList<T> list) {
//    this.list = list;
//  }
//
//  // is there a next
//  public boolean hasNext() {
//    return this.list.isCons();
//  }
//
//  // return the next
//  public T next() {
//    ConsList<T> item = this.list.asConsList();
//
//    T curr = item.first;
//    this.list = item.rest;
//
//    return curr;
//  }
//}
//
//// represents a list
//interface IList<T> extends Iterable<T> {
//  // adds an element to the list
//  void add(T t);
//
//  // checks if list is a cons
//  boolean isCons();
//
//  // cast list as a cons
//  ConsList<T> asConsList();
//}
//
//// represents and empty list
//class MtList<T> implements IList<T> {
//
//  // add item to mt
//  public void add(T t) {
//    return;
//  }
//
//  // check if mt is cons
//  public boolean isCons() {
//    return false;
//  }
//
//  // cant cast mt as cons
//  public ConsList<T> asConsList() {
//    throw new IllegalArgumentException("Can't cast empty list as cons list");
//  }
//
//  // iterates empty list
//  public Iterator<T> iterator() {
//    return new IListIterator<T>(this);
//  }
//
//}
//
//// represents a non-empty list
//class ConsList<T> implements IList<T> {
//  T first;
//  IList<T> rest;
//
//  ConsList(T first, IList<T> rest) {
//    this.first = first;
//    this.rest = rest;
//  }
//
//  // add item to cons
//  public void add(T t) {
//    this.first = t;
//    this.rest = this;
//  }
//
//  // check if cons is cons
//  public boolean isCons() {
//    return true;
//  }
//
//  // casts cons as cons
//  public ConsList<T> asConsList() {
//    return (ConsList<T>) this;
//  }
//
//  // iterates conslist
//  public Iterator<T> iterator() {
//    return new IListIterator<T>(this);
//  }
//}
//
//// Represents a single square of the game area
//class Cell {
//  // represents the size of the cell
//  static final int CELL_SIZE = 10;
//  // represents absolute height of this cell, in feet
//  double height;
//  // In logical coordinates, with the origin at the top-left corner of the screen
//  int x;
//  int y;
//  // the four adjacent cells to this one
//  Cell left;
//  Cell top;
//  Cell right;
//  Cell bottom;
//  // reports whether this cell is flooded or not
//  boolean isFlooded;
//
//  // Constructor
//  Cell(double height, int x, int y) {
//    this.height = height;
//    this.x = x;
//    this.y = y;
//    this.top = this;
//    this.right = this;
//    this.left = this;
//    this.bottom = this;
//    this.isFlooded = false;
//  }
//
//  // updates the top neighbor
//  void updateTop(Cell c) {
//    this.top = c;
//  }
//
//  // updates the bottom neighbor
//  void updateBottom(Cell c) {
//    this.bottom = c;
//  }
//
//  // updates the left neighbor
//  void updateLeft(Cell c) {
//    this.left = c;
//  }
//
//  // updates the right neigbor
//  void updateRight(Cell c) {
//    this.right = c;
//  }
//
//  // to determine if this cell is oceancell
//  boolean isOceanCell() {
//    return false;
//  }
//
//  // creates the cell image
//  WorldImage cellImage(int waterHeight) {
//    Color cellColor = Color.BLUE;
//
//    if (this.isFlooded) {
//      cellColor = new Color(0, 0,
//          (int) Math.min(254, Math.max(0, 254 - (waterHeight - this.height) * 5)));
//    }
//    else if (!this.isFlooded && this.height <= waterHeight) {
//      int r = Math.min(255, (int) (this.height * (255 / (ForbiddenIslandWorld.ISLAND_SIZE / 2))));
//
//      cellColor = new Color(r, Math.max(0, 200 - r), 0);
//    }
//    else {
//      cellColor = new Color((int) Math.min(255, Math.max(0, (this.height - waterHeight) * 8)),
//          (int) Math.min(255, Math.max(0, (210 + (this.height - waterHeight) * 3))),
//          (int) Math.min(255, Math.max(0, (this.height - waterHeight) * 8)));
//    }
//    return new RectangleImage(CELL_SIZE, CELL_SIZE, OutlineMode.SOLID, cellColor);
//  }
//
//  // renders the cell
//  public WorldScene renderCellAt(WorldScene background, int waterHeight) {
//    background.placeImageXY(cellImage(waterHeight), this.x * CELL_SIZE, this.y * CELL_SIZE);
//    return background;
//  }
//
//  // floods this cell
//  void floodCell() {
//    this.isFlooded = true;
//  }
//
//  // determines if this cell is a coast cell
//  boolean isCoastCell() {
//    return (!this.isFlooded && this.left.isFlooded || this.right.isFlooded || this.top.isFlooded
//        || this.bottom.isFlooded);
//  }
//
//  // checks if two cells are the same
//  boolean sameCell(Cell given) {
//    return this.height == given.height && this.x == given.x && this.y == given.y;
//  }
//}
//
//class OceanCell extends Cell {
//  // Constructor
//  OceanCell(double height, int x, int y) {
//    super(height, x, y);
//    this.isFlooded = true;
//  }
//
//  // to determine of this cell is oceanCell
//  boolean isOceanCell() {
//    return true;
//  }
//}
//
//// represents the player
//class Player {
//  Cell currentLoc;
//  int x;
//  int y;
//  int partsCount;
//
//  // Constructor
//  Player(Cell currentLoc) {
//    this.currentLoc = currentLoc;
//    this.x = currentLoc.x;
//    this.y = currentLoc.y;
//    this.partsCount = 0;
//  }
//
//  // update the player's current location
//  void updateCurrentLocation(Cell c) {
//    this.currentLoc = c;
//    this.x = c.x;
//    this.y = c.y;
//  }
//
//  // retrieve the player icon from file
//  public WorldImage playerImage() {
//    return new FromFileImage("pilot.png");
//  }
//
//  // renders the player image
//  WorldScene renderPlayerAt(WorldScene background) {
//    background.placeImageXY(this.playerImage(), this.x * Cell.CELL_SIZE, this.y * Cell.CELL_SIZE);
//    return background;
//  }
//}
//
//// represents everything the player needs to pick up
//class Target {
//  Cell location;
//  int x;
//  int y;
//
//  // constructor
//  Target(Cell location) {
//    this.location = location;
//    this.x = location.x;
//    this.y = location.y;
//  }
//
//  // retrieves the target image from file
//  WorldImage targetImage() {
//    return new FromFileImage("gear.png");
//  }
//
//  // renders the target image
//  WorldScene renderTargetAt(WorldScene background) {
//    background.placeImageXY(this.targetImage(), this.x * Cell.CELL_SIZE, this.y * Cell.CELL_SIZE);
//    return background;
//  }
//}
//
//class HelicopterTarget extends Target {
//  // Constructor
//  HelicopterTarget(Cell location) {
//    super(location);
//  }
//
//  // retrieves the helicopter image from file
//  WorldImage targetImage() {
//    return new FromFileImage("helicopter.png");
//  }
//}
//
//class ForbiddenIslandWorld extends World {
//  // control for game size
//  static final int ISLAND_SIZE = 64;
//  // All the cells of the game, including the ocean
//  IList<Cell> board;
//  // All the cells of the game that form the coastline
//  IList<Cell> coastList;
//  // List of all valid cells for player spawning
//  ArrayList<Cell> validCells;
//  // List of all targets
//  ArrayList<Target> targets;
//  // the current height of the ocean
//  int waterHeight;
//  // to track the number of ticks that have passed
//  int count;
//  // the number of parts the player must collect
//  int numParts;
//  // the number of parts the player has collected already
//  int collectedParts;
//  // 2D ArrayList representing heights of every cell
//  // ArrayList<ArrayList<Double>> heights;
//  // 2D ArrayList of cells
//  ArrayList<ArrayList<Cell>> cells;
//  // The player
//  Player player;
//  // The helicopter
//  HelicopterTarget helicopter;
//
//  ForbiddenIslandWorld() {
//    ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();
//    this.initializeLists();
//    heights = this.makeMountain();
//    this.createWorld(heights);
//  }
//
//  void initializeLists() {
//    this.cells = new ArrayList<ArrayList<Cell>>();
//    this.board = new MtList<Cell>();
//    this.coastList = new MtList<Cell>();
//    this.validCells = new ArrayList<Cell>();
//    this.targets = new ArrayList<Target>();
//  }
//
//  void createWorld(ArrayList<ArrayList<Double>> heights) {
//
//    this.waterHeight = 0;
//    this.count = 0;
//    this.numParts = 3;
//    this.collectedParts = 0;
//
//    this.initCells(heights);
//    this.linkCells();
//    this.makeBoardList();
//    this.makeCoastList();
//
//    this.makeValidCellsList();
//
//    this.player = this.makePlayer();
//    this.targets = this.makeTargets(this.numParts);
//    this.helicopter = this.makeHelicopterTarget();
//  }
//
//  // creates a 2D grid of heights representing a regular mountain
//  ArrayList<ArrayList<Double>> makeMountain() {
//    ArrayList<ArrayList<Double>> columns = new ArrayList<ArrayList<Double>>();
//    for (int j = 0; j < ISLAND_SIZE; j++) {
//      ArrayList<Double> row = new ArrayList<Double>();
//      for (int i = 0; i < ISLAND_SIZE; i++) {
//        row.add(Double.valueOf((ISLAND_SIZE / 2) - (Math.abs((ISLAND_SIZE / 2) - i))
//            - Math.abs((ISLAND_SIZE / 2) - j)));
//      }
//      columns.add(row);
//    }
//    return columns;
//  }
//
//  // creates a 2D grid of cells representing a random island
//  ArrayList<ArrayList<Double>> makeRandomIsland() {
//    Random heightRandomizer = new Random();
//
//    ArrayList<ArrayList<Double>> columns = new ArrayList<ArrayList<Double>>();
//    for (int j = 0; j < ISLAND_SIZE; j++) {
//      ArrayList<Double> row = new ArrayList<Double>();
//
//      for (int i = 0; i < ISLAND_SIZE; i++) {
//        if ((Double.valueOf(ISLAND_SIZE / 2 - Math.abs((ISLAND_SIZE / 2) - i)
//            - Math.abs(ISLAND_SIZE / 2 - j))) <= 0) {
//          row.add(Double.valueOf(
//              ISLAND_SIZE / 2 - Math.abs((ISLAND_SIZE / 2) - i) - Math.abs((ISLAND_SIZE / 2) - j)));
//        }
//
//        else {
//          row.add(Double.valueOf(heightRandomizer.nextInt(ISLAND_SIZE / 2) + 1));
//        }
//      }
//      columns.add(row);
//    }
//    return columns;
//  }
//
//  // creates a 2D grid of cells representing random terrain
//  ArrayList<ArrayList<Double>> makeRandomTerrain() {
//    // initialize ArrayList<ArrayList<Double>> to contain ISLAND_SIZE + 1
//    // rows and colums of 0
//    ArrayList<ArrayList<Double>> columns = new ArrayList<ArrayList<Double>>();
//    for (int j = 0; j < ISLAND_SIZE + 1; j++) {
//      ArrayList<Double> row = new ArrayList<Double>();
//      for (int i = 0; i < ISLAND_SIZE + 1; i++) {
//        row.add(Double.valueOf(0));
//      }
//      columns.add(row);
//    }
//
//    // set values of four corners to zero
//    columns.get(0).set(0, 0.0);
//    columns.get(0).set(ISLAND_SIZE, 0.0);
//    columns.get(ISLAND_SIZE).set(0, 0.0);
//    columns.get(ISLAND_SIZE).set(ISLAND_SIZE, 0.0);
//
//    // initialize center of grid to the max height of the island
//    columns.get(ISLAND_SIZE / 2).set((ISLAND_SIZE / 2), Double.valueOf(ISLAND_SIZE / 2));
//
//    // initialize middles of edges to 1
//    columns.get(0).set((ISLAND_SIZE / 2), 1.0);
//    columns.get(ISLAND_SIZE).set((ISLAND_SIZE / 2), 1.0);
//    columns.get(ISLAND_SIZE / 2).set(0, 1.0);
//    columns.get(ISLAND_SIZE / 2).set(ISLAND_SIZE, 1.0);
//
//    // generates the four quadrants
//    // top left quadrant
//    generateQuadrant(0, 0, (ISLAND_SIZE / 2), (ISLAND_SIZE / 2), columns);
//    // top right quadrant
//    generateQuadrant((ISLAND_SIZE / 2), 0, ISLAND_SIZE, (ISLAND_SIZE / 2), columns);
//    // bottom right quadrant
//    generateQuadrant((ISLAND_SIZE / 2), (ISLAND_SIZE / 2), ISLAND_SIZE, ISLAND_SIZE, columns);
//    // bottom left quadrant
//    generateQuadrant(0, (ISLAND_SIZE / 2), (ISLAND_SIZE / 2), ISLAND_SIZE, columns);
//
//    return columns;
//  }
//
//  // creates a 2D grid of cells mapping each cell to its appropriate height
//  void initCells(ArrayList<ArrayList<Double>> heights) {
//    for (int j = 0; j < ISLAND_SIZE; j++) {
//      ArrayList<Cell> row = new ArrayList<Cell>();
//      for (int i = 0; i < ISLAND_SIZE; i++) {
//        if (heights.get(j).get(i) <= 0) {
//          row.add(new OceanCell(heights.get(j).get(i), j, i));
//        }
//        else {
//          row.add(new Cell(heights.get(j).get(i), j, i));
//        }
//      }
//      this.cells.add(row);
//    }
//  }
//
//  // links each cell with its appropriate neighbor
//  void linkCells() {
//    for (int j = 0; j < ISLAND_SIZE; j++) {
//      for (int i = 0; i < ISLAND_SIZE; i++) {
//        Cell c = this.cells.get(j).get(i);
//
//        // Only update the left neighbor if current cell
//        // is not on the right edge of board
//        if (i > 0) {
//          c.updateTop(cells.get(j).get(i - 1));
//        }
//
//        // Only update the right neighbor if current cell
//        // is not on the left edge of board
//        if (i < ISLAND_SIZE - 1) {
//          c.updateBottom(cells.get(j).get(i + 1));
//        }
//
//        // Only update top neighbor if current cell
//        // is not at top of board
//        if (j > 0) {
//          c.updateLeft(cells.get(j - 1).get(i));
//        }
//
//        // Only update bottom neighbor if current cell
//        // is not on the bottom of the board
//        if (j < ISLAND_SIZE - 1) {
//          c.updateRight(cells.get(j + 1).get(i));
//        }
//      }
//    }
//  }
//
//  // transforms the 2D ArrayList of Cells into an IList
//  void makeBoardList() {
//    for (int j = 0; j < ISLAND_SIZE; j++) {
//      for (int i = 0; i < ISLAND_SIZE; i++) {
//        this.board = new ConsList<Cell>(this.cells.get(j).get(i), this.board);
//        // board.add(this.cells.get(j).get(i));
//      }
//    }
//  }
//
//  @Override
//  public WorldScene makeScene() {
//    WorldScene canvas = new WorldScene(ISLAND_SIZE * Cell.CELL_SIZE, ISLAND_SIZE * Cell.CELL_SIZE);
//    // draw the cells
//    for (Cell curr : this.board) {
//      curr.renderCellAt(canvas, this.waterHeight);
//    }
//
//    // draws the player
//    this.player.renderPlayerAt(canvas);
//
//    // draws the targets
//    for (Target curr : this.targets) {
//      curr.renderTargetAt(canvas);
//    }
//
//    // draws the helicopter
//    this.helicopter.renderTargetAt(canvas);
//
//    return canvas;
//  }
//
//  // generates the quadrants to create a random terrain island
//  void generateQuadrant(int minX, int minY, int maxX, int maxY,
//      ArrayList<ArrayList<Double>> heights) {
//    Random rand = new Random();
//
//    int midpointX = (minX + maxX) / 2;
//    int midpointY = (minY + maxY) / 2;
//
//    double nudge = (rand.nextInt(Math.abs(maxX + 1 - minX) / 2) - 0.27 * (maxX - minX));
//
//    if (minX + 1 >= maxX || minY + 1 >= maxY) {
//      return;
//    }
//
//    double t = nudge + (heights.get(minX).get(minY) + heights.get(maxX).get(minY)) / 2;
//    heights.get(midpointX).set(minY, t);
//
//    double l = nudge + (heights.get(minX).get(minY) + heights.get(minX).get(maxY) / 2);
//    heights.get(minX).set(midpointY, l);
//
//    double m = nudge + (heights.get(minX).get(minY) + heights.get(minX).get(minY)
//        + heights.get(maxX).get(minY) + heights.get(maxX).get(maxY)) / 4;
//    heights.get(midpointX).set(midpointY, m);
//
//    // top left quadrant
//    generateQuadrant(minX, minY, midpointX, midpointY, heights);
//    // top right quadrant
//    generateQuadrant(midpointX, minY, maxX, midpointY, heights);
//    // bottom right quadrant
//    generateQuadrant(midpointX, midpointY, maxX, maxY, heights);
//    // bottom left quadrant
//    generateQuadrant(minX, midpointY, midpointX, maxY, heights);
//  }
//
//  // floods all cells below water level at coastline
//  void flood() {
//    for (Cell curr : this.coastList) {
//      if (curr.height <= this.waterHeight) {
//        curr.floodCell();
//      }
//    }
//  }
//
//  // creates a list of all the cells that form the coast
//  void makeCoastList() {
//    for (Cell curr : this.board) {
//      if (curr.isCoastCell()) {
//        this.coastList = new ConsList<Cell>(curr, this.coastList);
//      }
//    }
//  }
//
//  // handles player movement and board resets
//  public void onKeyEvent(String k) {
//    if (k.equals("w") && !player.currentLoc.top.isFlooded) {
//      this.player.updateCurrentLocation(player.currentLoc.top);
//      pickupTarget();
//    }
//
//    else if (k.equals("a") && !player.currentLoc.left.isFlooded) {
//      this.player.updateCurrentLocation(player.currentLoc.left);
//      pickupTarget();
//    }
//
//    else if (k.equals("s") && !player.currentLoc.bottom.isFlooded) {
//      this.player.updateCurrentLocation(player.currentLoc.bottom);
//      pickupTarget();
//    }
//
//    else if (k.equals("d") && !player.currentLoc.right.isFlooded) {
//      this.player.updateCurrentLocation(player.currentLoc.right);
//      pickupTarget();
//    }
//
//    if (k.equals("m")) {
//      initializeLists();
//      ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();
//      heights = makeMountain();
//      createWorld(heights);
//    }
//    else if (k.equals("r")) {
//      initializeLists();
//      ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();
//      heights = makeRandomIsland();
//      createWorld(heights);
//    }
//    else if (k.equals("t")) {
//      initializeLists();
//      ArrayList<ArrayList<Double>> heights = new ArrayList<ArrayList<Double>>();
//      heights = makeRandomTerrain();
//      createWorld(heights);
//    }
//    else {
//      return;
//    }
//  }
//
//  // determines if the player has picked up a target
//  void pickupTarget() {
//    int index = -1;
//    for (Target curr : this.targets) {
//      if (curr.location.sameCell(this.player.currentLoc)) {
//        this.player.partsCount = this.player.partsCount + 1;
//        index = this.targets.indexOf(curr);
//      }
//    }
//
//    if (index != -1) {
//      this.targets.remove(index);
//      this.collectedParts += 1;
//    }
//  }
//
//  // updates the board on tick
//  public void onTick() {
//    this.count += 1;
//    while (count == 10) {
//      this.waterHeight += 1;
//      this.count = 0;
//
//      flood();
//      makeCoastList();
//      makeScene();
//    }
//  }
//
//  // makes the player
//  Player makePlayer() {
//    return new Player(this.validCell());
//  }
//
//  // makes the targets
//  ArrayList<Target> makeTargets(int num) {
//    for (int i = 0; i < num; i++) {
//      this.targets.add(new Target(this.validCell()));
//    }
//    return this.targets;
//  }
//
//  // makes the helicopter
//  HelicopterTarget makeHelicopterTarget() {
//    return new HelicopterTarget(this.cells.get(ISLAND_SIZE / 2).get(ISLAND_SIZE / 2));
//  }
//
//  // creates an array list of valid cells
//  // primarily to determine player spawn points
//  void makeValidCellsList() {
//    for (Cell curr : this.board) {
//      if (!curr.isCoastCell() && curr.height > 0) {
//        validCells.add(curr);
//      }
//    }
//  }
//
//  // generates a random valid cell for player to spawn on
//  Cell validCell() {
//    Random cellRandomizer = new Random();
//    Cell current = this.validCells.get(cellRandomizer.nextInt(this.validCells.size()));
//    this.validCells.remove(this.validCells.indexOf(current));
//    return current;
//  }
//
//  // produces the win or loss text
//  WorldScene endScreen(String s) {
//    WorldScene canvas = this.makeScene();
//    canvas.placeImageXY(new TextImage(s, 40, FontStyle.BOLD_ITALIC, Color.BLACK), 320, 320);
//    return canvas;
//  }
//
//  // checks if the player won or lost
//  public WorldEnd worldEnds() {
//    // Lose condition
//    if (this.player.currentLoc.isFlooded) {
//      return new WorldEnd(true, this.endScreen("Game Over. Better Luck Time."));
//    }
//    // Win condition
//    else if (this.player.partsCount == this.numParts
//        && this.player.currentLoc.sameCell(this.helicopter.location)) {
//      return new WorldEnd(true, this.endScreen("Winna Winna!!"));
//    }
//    else {
//      return new WorldEnd(false, this.makeScene());
//    }
//  }
//
//}
//
//class ExamplesForbiddenIsland {
//
//  void testGame(Tester t) {
//    ForbiddenIslandWorld world = new ForbiddenIslandWorld();
//    world.makeScene();
//    world.bigBang(Cell.CELL_SIZE * ForbiddenIslandWorld.ISLAND_SIZE - 6,
//        Cell.CELL_SIZE * ForbiddenIslandWorld.ISLAND_SIZE - 6, 0.2);
//  }
//
//  // instantiation
//  ForbiddenIslandWorld world1;
//  Cell cell1;
//  Cell cell2;
//  Cell cell3;
//  Cell cell4;
//  OceanCell oCell1;
//  OceanCell oCell2;
//  IList<Integer> list1;
//  IList<Integer> list2;
//  Player player1;
//
//  // initialize data
//  void init() {
//    world1 = new ForbiddenIslandWorld();
//
//    cell1 = new Cell(0, 0, 0);
//    cell2 = new Cell(3, 3, 3);
//    cell3 = new Cell(4, 5, 6);
//    cell4 = new Cell(8, 7, 3);
//
//    oCell1 = new OceanCell(0, 0, 0);
//    oCell2 = new OceanCell(62, 17, 45);
//
//    list1 = new ConsList<Integer>(1, new ConsList<Integer>(2, new MtList<Integer>()));
//    list2 = new MtList<Integer>();
//
//    player1 = new Player(cell2);
//  }
//
//  // tests for as conslist
//  void testAsConsList(Tester t) {
//    this.init();
//
//    t.checkException(new IllegalArgumentException("Can't cast empty list as cons list"),
//        new MtList<Integer>(), "asConsList");
//    t.checkExpect(this.list1.asConsList(), (ConsList<Integer>) this.list1);
//  }
//
//  // tests for isCons
//  void testIsCons(Tester t) {
//    this.init();
//    t.checkExpect(this.list2.isCons(), false);
//    t.checkExpect(this.list1.isCons(), true);
//  }
//
//  // tests for iterator
//  void testIterator(Tester t) {
//    this.init();
//    Iterator<Integer> it1 = this.list1.iterator();
//    t.checkExpect(it1.hasNext(), true);
//    Iterator<Integer> it2 = this.list2.iterator();
//    t.checkExpect(it2.hasNext(), false);
//  }
//
//  // tests for next
//  void testNext(Tester t) {
//    this.init();
//    Iterator<Integer> it1 = this.list1.iterator();
//    t.checkExpect(it1.next(), 1);
//    t.checkExpect(it1.next(), 2);
//    t.checkExpect(it1.hasNext(), false);
//    t.checkException(new IllegalArgumentException("Can't cast empty list as cons list"), it1,
//        "next");
//  }
//
//  // tests for methods that update neighbors
//  void testUpdateNeighbors(Tester t) {
//    this.init();
//
//    t.checkExpect(cell1.top, cell1);
//    t.checkExpect(cell1.bottom, cell1);
//    t.checkExpect(cell1.right, cell1);
//    t.checkExpect(cell1.left, cell1);
//
//    cell1.updateTop(cell2);
//    cell1.updateBottom(cell3);
//    cell1.updateRight(cell4);
//    cell1.updateLeft(cell2);
//
//    t.checkExpect(cell1.top, cell2);
//    t.checkExpect(cell1.bottom, cell3);
//    t.checkExpect(cell1.right, cell4);
//    t.checkExpect(cell1.left, cell2);
//  }
//
//  // test fixture for makeMountain
//  void testMakeMountain(Tester t) {
//    this.init();
//
//    t.checkExpect(world1.cells.size(), 64);
//
//    t.checkExpect(world1.cells.get(0).get(0).height <= 0, true);
//    t.checkExpect(world1.cells.get(0).get(0).height < world1.cells.get(32).get(32).height, true);
//
//    t.checkExpect(world1.cells.get(0).get(0).isOceanCell(), true);
//    t.checkExpect(world1.cells.get(30).get(30).isOceanCell(), false);
//    t.checkExpect(world1.cells.get(38).get(24).isOceanCell(), false);
//
//    t.checkExpect(world1.cells.get(0).get(0).isFlooded, true);
//    t.checkExpect(world1.cells.get(30).get(30).isFlooded, false);
//    t.checkExpect(world1.cells.get(38).get(24).isFlooded, false);
//  }
//
//  // test fixture for makeRandomIsland
//  void testMakeRandomIsland(Tester t) {
//    this.init();
//
//    // world1.makeRandomIsland();
//
//    t.checkExpect(world1.cells.size(), 64);
//
//    t.checkExpect(world1.cells.get(0).get(0).height <= 0, true);
//    t.checkExpect(world1.cells.get(0).get(0).height < world1.cells.get(32).get(32).height, true);
//
//    t.checkExpect(world1.cells.get(0).get(0).isOceanCell(), true);
//    t.checkExpect(world1.cells.get(30).get(30).isOceanCell(), false);
//    t.checkExpect(world1.cells.get(38).get(24).isOceanCell(), false);
//
//    t.checkExpect(world1.cells.get(0).get(0).isFlooded, true);
//    t.checkExpect(world1.cells.get(30).get(30).isFlooded, false);
//    t.checkExpect(world1.cells.get(38).get(24).isFlooded, false);
//
//    t.checkExpect(
//        world1.cells.get(38).get(42).height < 32 && world1.cells.get(38).get(42).height > 0, true);
//  }
//
//  // test fixture for floodCell
//  void testFloodCell(Tester t) {
//    this.init();
//
//    t.checkExpect(this.cell2.isFlooded, false);
//
//    this.cell2.floodCell();
//    t.checkExpect(this.cell2.isFlooded, true);
//  }
//
//  // test fixture for isCoastCell
//  void testIsCoastCell(Tester t) {
//    this.init();
//
//    t.checkExpect(this.cell2.isCoastCell(), false);
//
//    this.cell2.updateBottom(this.oCell1);
//    t.checkExpect(this.cell2.isCoastCell(), true);
//  }
//
//  // test for validCell
//  void testValidCell(Tester t) {
//    this.init();
//
//    t.checkExpect(world1.validCells.size(), 1857);
//
//    world1.validCell();
//
//    t.checkExpect(world1.validCells.size(), 1856);
//  }
//
//  // test for updateCurrentLoc
//  void testUpdateCurrentLoc(Tester t) {
//    this.init();
//
//    t.checkExpect(player1.currentLoc, cell2);
//
//    player1.updateCurrentLocation(cell3);
//
//    t.checkExpect(player1.currentLoc, cell3);
//  }
//}