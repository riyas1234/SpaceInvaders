import tester.*;
import javalib.funworld.*;
import javalib.worldimages.*;

import java.awt.Color;
import java.util.function.*;
import java.util.Random;

// represents a predicate for type <T>
interface Predicate<T> {
  boolean apply(T inp);
}

//represents a list of type <T>
interface IList<T> {
  // filter this list using the given predicate
  IList<T> filter(Predicate<T> pred);

  // map a function onto every member of this list
  <U> IList<U> map(Function<T, U> converter);

  // combine the items in this list from right to left
  <U> U foldr(BiFunction<T, U, U> converter, U initial);

  // checks if a condition holds true for all items in this list
  boolean andmap(Predicate<T> condition);

  // checks if a condition holds true for at least one item in this list
  boolean ormap(Predicate<T> condition);

  // compute the length of this list
  int length();

  // checks if a given item is contained in a list
  boolean contains(T t);
}

//represents an Empty list of type <T>
class MtList<T> implements IList<T> {

  MtList() {}

  // filter this empty list using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    /*
     * Parameters:
     * pred... Predicate<T>
     * Methods of paramters:
     */
    return new MtList<T>();
  }

  // map a function onto every member of this empty list
  public <U> IList<U> map(Function<T, U> converter) {
    /*
     * Parameters:
     * converter... Function<T, U>
     * Methods of Parameters:
     */
    return new MtList<U>();
  }

  // combine the items in this empty list from right to left
  public <U> U foldr(BiFunction<T, U, U> converter, U initial) {
    /*
     * Parameters:
     * converter... BiFunction<T, U, U>
     * initial... U
     * Methods of parameters:
     */
    return initial;
  }

  // compute the length of this empty list
  public int length() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return 0;
  }

  // checks if a condition holds true for all items in this empty list
  public boolean andmap(Predicate<T> condition) {
    /*
     * Parameters:
     * condition... Predicate<T>
     * Methods on parameters:
     */
    return false;
  }

  // checks if a condition holds true for at least one item in this empty list
  public boolean ormap(Predicate<T> condition) {
    /*
     * Parameters:
     * condition... Predicate<T>
     * Methods on parameters:
     */
    return false;
  }

  // checks if a given item is contained in this empty list
  public boolean contains(T t) {
    /*
     * Parameters:
     * t... T
     * Methods of parameters:
     */
    return false;
  }
}

//represents a non empty list of type <T>
class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  /*
   * Fields:
   * this.first... T
   * this.rest... IList<T>
   * Methods:
   * this.filter(Predicate<T>)... IList<T>
   * this.map(Function<T, U>)... IList<U>
   * this.folder(BiFunction<T, U, U>, <U>)... U
   * this.andmap(Predicate<T>)... boolean
   * this.ormap(Predicate<T>)... boolean
   * this.length()... int
   * this.contains(T)... boolean
   * Methods of fields:
   * this.rest.filter(Predicate<T>)... IList<T>
   * this.rest.map(Function<T, U>)... IList<U>
   * this.rest.folder(BiFunction<T, U, U>, <U>)... U
   * this.rest.andmap(Predicate<T>)... boolean
   * this.rest.ormap(Predicate<T>)... boolean
   * this.rest.length()... int
   * this.rest.contains(T)... boolean
   */

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  // filter this list using the given predicate
  public IList<T> filter(Predicate<T> pred) {
    /*
     * Parameters:
     * pred... Predicate<T>
     * Methods of paramters:
     */
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    } else {
      return this.rest.filter(pred);
    }
  }

  // map a function onto every member of this list
  public <U> IList<U> map(Function<T, U> converter) {
    /*
     * Parameters:
     * converter... Function<T, U>
     * Methods of Parameters:
     */
    return new ConsList<U>(converter.apply(this.first), this.rest.map(converter));
  }

  // combine the items in this list from right to left
  public <U> U foldr(BiFunction<T, U, U> converter, U initial) {
    /*
     * Parameters:
     * converter... BiFunction<T, U, U>
     * initial... U
     * Methods of parameters:
     */
    return converter.apply(this.first, this.rest.foldr(converter, initial));
  }


  // checks if a condition holds true for all items in this list
  public boolean andmap(Predicate<T> condition) {
    /*
     * Parameters:
     * condition... Predicate<T>
     * Methods on parameters:
     */
    if ((condition.apply(this.first)) && (this.rest.andmap(condition))) {
      return true;
    }
    else {
      return condition.apply(this.first) && (this.rest.length() == 1);
    }
  }

  // checks if a condition holds true for at least one item in this list
  public boolean ormap(Predicate<T> condition) {
    /*
     * Parameters:
     * condition... Predicate<T>
     * Methods on parameters:
     */
    return (condition.apply(this.first)) || (this.rest.ormap(condition));
  }

  // compute the length of this list
  public int length() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return 1 + this.rest.length();
  }

  // checks if a given item is contained in this list
  public boolean contains(T t) {
    /*
     * Parameters:
     * t... T
     * Methods of Parameters:
     */
    return this.first.equals(t) || this.rest.contains(t);
  }
}

//draws a GamePiece onto a worldScene
class Drawer implements BiFunction<IGamePiece, WorldScene, WorldScene> {
  /*
   * Fields:
   * Methods:
   * this.apply(IGamePiece, WorldScene)... WorldScene
   * Methods of fields:
   */
  // applies draw method to current gamepiece and places onto WorldScene
  public WorldScene apply(IGamePiece piece, WorldScene worldScene) {
    /*
     * Parameters:
     * piece... IGamePiece
     * worldScene... WorldScene
     * Methods of Parameters:
     * piece.draw()... WorldScene
     * piece.isValid()... boolean
     * piece.shoot()... IGamePiece
     * piece.move(WorldScene)... IGamePiece
     * peice.moveReg()... IGamePiece
     */
    return piece.draw(worldScene);
  }
}

//function object for moving spaceship, ibullets, and sbullets normally at every tick
class MoveRegFunc implements Function<IGamePiece, IGamePiece> {
  /*
   * Fields:
   * Methods:
   * this.apply(IGamePiece)... IGamePiece
   * Methods of fields:
   */
  
  // apples function moveReg() to a Gamepiece
  public IGamePiece apply(IGamePiece piece) {
    /*
     * Parameters:
     * piece... IGamePiece
     * Methods of parameters:
     */
    return piece.moveReg();
  }
}

//removing bullets that are out of the frame of the game from the list
class RemoveBullets implements Predicate<IGamePiece> {
  /*
   * Fields:
   * Methods:
   * this.apply(IGamePiece)... boolean
   * Methods of fields:
   */
  
  // applies is valid onto the given Gamepiece
  public boolean apply(IGamePiece inp) {
    /*
     * Parameters:
     * inp... IGamePiece
     * Methods of Parameters:
     */
    return inp.isValid();
  }
}

//checks whether the spaceship has been hit by an ibullet
class CheckSpaceship implements Predicate<IGamePiece> {
  CartPt shipPt;
  
  /*
   * Fields:
   * this.shipPt... CartPt
   * Methods:
   * this.apply(IGamePiece)... boolean
   * Methods of fields:
   */

  CheckSpaceship(CartPt shipPt) {
    this.shipPt = shipPt;
  }

  // appleis loc() to get spaceship location and check it's place on the WorldScene
  public boolean apply(IGamePiece piece) {
    /*
     * Parameters:
     * piece... IGamePiece
     * Methods of parameters:
     */
    return this.shipPt.x - 30 < piece.loc().x && this.shipPt.x + 30 > piece.loc().x
        && piece.loc().y == this.shipPt.y - 10;
  }
}

// returns the CartPt of an IGamePiece
class Points implements Function<IGamePiece, CartPt> {
  /*
   * Fields:
   * Methods:
   * this.apply(IGamePiece)... CartPt
   * Methods of fields:
   */
  
  // applies loc() to a gamepiece and gets it's location
  public CartPt apply(IGamePiece piece) {
    /*
     * Parameters:
     * piece... IGamePiece
     * Methods of Parameters:
     */
    return piece.loc();
  }
}

// returns whether an invader has been hit by a bullet or if a bullet hit an invader
class Hit implements Predicate<IGamePiece> {
  IList<CartPt> sbulletLocs;
  /*
   * Fields:
   * this.sbulletLocs... IList<CartPt>
   * Methods:
   * this.apply(IGamePiece)... boolean
   * Methods of fields:
   * this.sbulletLocs.filter(Predicate<T>)... IList<T>
   * this.sbulletLocs.map(Function<T, U>)... IList<U>
   * this.sbulletLocs.folder(BiFunction<T, U, U>, <U>)... U
   * this.sbulletLocs.andmap(Predicate<T>)... boolean
   * this.sbulletLocs.ormap(Predicate<T>)... boolean
   * this.sbulletLocs.length()... int
   * this.sbulletLocs.contains(T)... boolean
   */

  
  Hit(IList<CartPt> sbulletLocs) {
    this.sbulletLocs = sbulletLocs;
  }

  // checks if an invader has been hit by a bullet
  public boolean apply(IGamePiece piece) {
    /*
     * Parameters:
     * piece... IGamePiece
     * Methods of parameters:
     */
    return !(this.sbulletLocs.ormap(new ContainsPoint(piece.loc())));
  }
}

//Utilities class for utility methods
class Utils {
  // builds a 2d list according to given columns and rows
  public <U, R, T> IList<T> buildList2D(Integer numCol, Integer numRow,
      BiFunction<Integer, Integer, T> func2D) {
    /*
     * Parameters:
     * numCol... Integer
     * numRow... Integer
     * Func2D... BiFunction<Integer, Integer>
     * Methods of parameters:
     */
    if (numRow < 1) {
      return new MtList<T>();
    }
    else if (numCol < 1) {
      return new ConsList<T>(func2D.apply(numCol, numRow),
          this.buildList2D(numCol + 8, numRow - 1, func2D));
    }
    else {
      return new ConsList<T>(func2D.apply(numCol, numRow),
          this.buildList2D(numCol - 1, numRow, func2D));
    }
  }
}

// creates a single new invader
class MakeInvader implements Function<Integer, IGamePiece> { 
  /*
   * Fields:
   * Methods:
   * this.apply(Integer)... IGamePiece
   * Methods of fields:
   */
  
  // creates an invader at new location by applying num to the CartPt
  public IGamePiece apply(Integer num) {
    /*
     * Parameters:
     * num... Integer
     * Methods of parameters:
     */
    return new Invader(new CartPt(50 + 40 * num, 50), Color.RED);
  }
}

//function for creating an invader for the grid
class Func2D implements BiFunction<Integer, Integer, IGamePiece> {
  /*
   * Fields:
   * Methods:
   * this.apply(Integer, Integer)... IGamePiece
   * Methods of fields:
   */
  
  // creates new invaders at different Points according to the given row and col numbers
  public IGamePiece apply(Integer col, Integer row) {
    /*
     * Parameters:
     * col... Integer
     * row... Integer
     * Methods of parameters:
     */
    return new Invader(new CartPt(50 + 40 * col, 50 + 40 * row), Color.RED);
  }
}

//whether a point is contained within a list of Points
class ContainsPoint implements Predicate<CartPt> {
  CartPt point; // invader loc
  /*
   * Fields:
   * this.point... CartPt
   * Methods:
   * this.apply(CartPt)... boolean
   * Methods of fields:
   * this.point.moveLoc(int, int)... CartPt
   */

  ContainsPoint(CartPt point) {
    this.point = point;
  }

  // checks whether a point is between a certain range
  public boolean apply(CartPt pt) {
    /*
     * Parameters:
     * pt... CartPt:
     * Methods of parameters:
     * pt.moveLoc(int, int)... CartPt
     */
    return (Math.sqrt(((point.x - pt.x) * (point.x - pt.x))
        + ((point.y - pt.y) * (point.y - pt.y))) < Math.sqrt(200));

  }
}

// checks whether a given integer is bigger than another
class BiggerThan implements Predicate<Integer> {
  Integer limit;
  /*
   * Fields:
   * this.limit... Integer
   * Methods:
   * this.apply(Integer)... boolean
   * Methods of fields:
   */

  BiggerThan(Integer lim) {
    this.limit = lim;
  }

  // compares the size of one integer to another
  public boolean apply(Integer inp) {
    /*
     * Parameters:
     * inp... Integer
     * Methods of parameters:
     */   
    return inp > limit;
  }
}

// calculates the length of a string
class StringLengthSum implements BiFunction<String, Integer, Integer> {
  /*
   * Fields:
   * Methods:
   * this.apply(String, Integer)... Integer
   * Methods of fields:
   */
  
  // adds the length of strings
  public Integer apply(String t, Integer u) {
    /*
     * Parameters:
     * t... String
     * u... Integer
     * Methods of parameters:
     */
    return u + t.length();
  }
}


//Represents a worldscene of SpaceInvaders
class SpaceInvaders extends World {
  IList<IGamePiece> invaders;
  IGamePiece spaceship;
  IList<IGamePiece> sbullets;
  IList<IGamePiece> ibullets;
  int time;

  /*
   * Fields:
   * this.invaders... IList<IGamePiece>
   * this.spaceship... IGamePiece
   * this.sbullets... IList<IGamePiece>
   * this.ibullets... IList<IGamePiece>
   * this.rand... Random
   * Methods:
   * this.makeScene()... WorldScene
   * this.Random()... Interger
   * Methods of fields:
   * this.invaders.filter(Predicate<T>)... IList<T>
   * this.invaders.map(Function<T, U>)... IList<U>
   * this.invaders.folder(BiFunction<T, U, U>, <U>)... U
   * this.invaders.andmap(Predicate<T>)... boolean
   * this.invaders.ormap(Predicate<T>)... boolean
   * this.invaders.length()... int
   * this.invaders.contains(T)... boolean
   * this.sbullets.filter(Predicate<T>)... IList<T>
   * this.sbullets.map(Function<T, U>)... IList<U>
   * this.sbullets.folder(BiFunction<T, U, U>, <U>)... U
   * this.sbullets.andmap(Predicate<T>)... boolean
   * this.sbullets.ormap(Predicate<T>)... boolean
   * this.sbullets.length()... int
   * this.sbullets.contains(T)... boolean
   * this.ibullets.filter(Predicate<T>)... IList<T>
   * this.ibullets.map(Function<T, U>)... IList<U>
   * this.ibullets.folder(BiFunction<T, U, U>, <U>)... U
   * this.ibullets.andmap(Predicate<T>)... boolean
   * this.ibullets.ormap(Predicate<T>)... boolean
   * this.ibullets.length()... int
   * this.ibullets.contains(T)... boolean
   * this.spaceship.draw()... WorldScene
   * this.spaceship.isValid()... boolean
   * this.spaceship.shoot()... IGamePiece
   * this.spaceship.move(WorldScene)... IGamePiece
   * this.spaceship.moveReg()... IGamePiece
   */


  SpaceInvaders(IList<IGamePiece> invaders, IGamePiece spaceship, IList<IGamePiece> sbullets,
      IList<IGamePiece> ibullets, int time) {
    this.invaders = invaders;
    this.spaceship = spaceship;
    this.sbullets = sbullets.filter(new RemoveBullets());
    this.ibullets = ibullets.filter(new RemoveBullets());
    this.time = time;
  }

  SpaceInvaders() {
    this.invaders = new Utils().buildList2D(8, 4, new Func2D());
    this.spaceship = new Spaceship();
    this.sbullets = new MtList<IGamePiece>();
    this.ibullets = new MtList<IGamePiece>();
    this.time = 0;
  }

  // returns an end world scene if the conditions for Game Over occur
  public WorldEnd worldEnds() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    if (this.ibullets.ormap(new CheckSpaceship(this.spaceship.loc()))
        || this.invaders.length() == 0) {
      return new WorldEnd(true, new WorldScene(420, 500)
          .placeImageXY(new TextImage("Game Over", 30, Color.BLUE), 210, 250));
    }
    else {
      return new WorldEnd(false, this.makeScene());
    }
  }
  
  // Draws this WorldScene
  public WorldScene makeScene() {
    /*
     * Parameters:
     * Methods of Parameters:
     */
    return this.spaceship.draw(this.ibullets.foldr(new Drawer(), this.sbullets.foldr(new Drawer(),
        this.invaders.foldr(new Drawer(), new WorldScene(420, 500)))));
  }

  // produces a random integer
  public Integer random(int maxVal) {
    /*
     * Parameters:
     * maxVal... int
     * Methods of parameters:
     */
    return new Random().nextInt(maxVal);
  }

  // creates the game after every interval of time
  public SpaceInvaders onTick() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.moveSpaceship().killInvaders(this.invaders).invaderShoot();
  }

  // remove invaders and corresponding sbullets if they have collided
  public SpaceInvaders killInvaders(IList<IGamePiece> i) {
    /*
     * Parameters:
     * i... IList<IGamePiece>
     * Methods of Parameters:
     * i.filter(Predicate<T>)... IList<T>
     * i.map(Function<T, U>)... IList<U>
     * i.folder(BiFunction<T, U, U>, <U>)... U
     * i.andmap(Predicate<T>)... boolean
     * i.ormap(Predicate<T>)... boolean
     * i.length()... int
     * i.contains(T)... boolean
     */
    return new SpaceInvaders(this.invaders.filter(new Hit(this.sbullets.map(new Points()))),
        this.spaceship, this.sbullets.filter(new Hit(this.invaders.map(new Points()))),
        this.ibullets, this.time);
  }
  
  // moves the spaceship, ibullets, and sbullets normally (left/right, down, up)
  public SpaceInvaders moveSpaceship() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new SpaceInvaders(this.invaders, this.spaceship.moveReg(),
        this.sbullets.map(new MoveRegFunc()), this.ibullets.map(new MoveRegFunc()), this.time + 1);
  }

  // switched the direction of the spaceship
  public SpaceInvaders shiftSpaceship() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new SpaceInvaders(this.invaders, this.spaceship.move(), this.sbullets, this.ibullets,
        this.time);
  }

  // shoots bullets from a Spaceship
  public SpaceInvaders shipShoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    if (this.sbullets.length() < 3) {
      return new SpaceInvaders(this.invaders, this.spaceship,
          new ConsList<IGamePiece>(this.spaceship.shoot(), this.sbullets), this.ibullets,
          this.time);
    }
    return this;
  }
  
  // shoots a bullet from a random invader
  public SpaceInvaders invaderShoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    if (ibullets.length() < 10 && this.time % 5 == 0) {
      return new SpaceInvaders(this.invaders, this.spaceship, this.sbullets,
          new ConsList<IGamePiece>(newInvaderBullet(), this.ibullets), this.time);
    }
    return this;
  }

  // creates a bullet from a random invader CartPt
  public IBullet newInvaderBullet() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    CartPt rand1 = new CartPt(random(9) * 40 + 50, random(4) * 40 + 100);
    if (this.invaders.map(new Points()).ormap(new ContainsPoint(rand1))) {
      return new IBullet(rand1, 5, Color.RED);
    }
    return new IBullet(new CartPt(-1, -1), 5, Color.RED);
  }

  // processes the three valid key presses for the game
  public SpaceInvaders onKeyReleased(String key) {
    /*
     * Parameters:
     * key... String
     * Methods of parameters:
     */
    if (key.equals("left")) {
      return this.leftKey();
    }
    if (key.equals("right")) {
      return this.rightKey();
    }
    if (key.equals(" ")) {
      return this.spaceKey();
    }
    else {
      return this;
    }
  }

  // processes left key press to switch spaceship direction
  SpaceInvaders leftKey() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.shiftSpaceship();
  }

  // processes right key press to switch spaceship direction
  SpaceInvaders rightKey() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.shiftSpaceship();
  }

  // processes space key press to shoot from spaceship
  SpaceInvaders spaceKey() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.shipShoot();
  }
}

//Represents a Gamepiece
interface IGamePiece {
  // draws a WorldScene
  WorldScene draw(WorldScene worldscene);

  // checks whether an IGamePiece is within the frame of the game
  boolean isValid();

  // shoots a bullet from the IGamePiece
  IGamePiece shoot();

  // moves this IGamePiece
  IGamePiece move();

  // moves this IGamePiece in its normal way
  IGamePiece moveReg();

  // returns the location of the IGamePiece
  CartPt loc();
}

//Represents the location and color of a GamePiece
abstract class AGamePiece implements IGamePiece {
  CartPt loc;
  Color color;

  /*
   * Fields:
   * this.loc... CartPt
   * this.color... Color
   * Methods:
   * this.loc()... CartPt
   * Methods of fields:
   * this.loc.moveLoc()... CartPt
   */

  AGamePiece(CartPt loc, Color color) {
    this.loc = loc;
    this.color = color;
  }

  // gets location of a Gamepiece
  public CartPt loc() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.loc;
  }
}

//Represents a Spaceship 
class Spaceship extends AGamePiece {
  int speed;

  /*
   * Fields:
   * super.loc... CartPt
   * super.color... Color
   * this.speed... int
   * Methods:
   * this.draw()... WorldScene
   * this.isValid()... boolean
   * this.shoot()... IGamePiece
   * this.move(WorldScene)... IGamePiece
   * this.moveReg()... IGamePiece
   * Methods of fields:
   * super.loc.moveLoc()... CartPt
   */

  Spaceship() {
    super(new CartPt(250, 480), Color.BLACK);
    this.speed = 10;
  }

  Spaceship(CartPt loc, int speed, Color color) {
    super(loc, color);
    this.speed = speed;
  }

  // draws a WorldScene with this Spaceship
  public WorldScene draw(WorldScene acc) {
    /*
     * Parameters:
     * acc... WorldScene
     * Methods of Parameters:
     */
    return acc.placeImageXY(
        new OverlayImage(new EquilateralTriangleImage(40, OutlineMode.SOLID, this.color),
            new EllipseImage(60, 20, OutlineMode.SOLID, this.color)),
        this.loc.x, this.loc.y);
  }

  // moves this Spaceship
  public IGamePiece move() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new Spaceship(this.loc.moveLoc((-1 * this.speed), 0), this.speed * (-1), this.color);
  }

  // moves this Spaceship in its normal way
  public IGamePiece moveReg() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    if (this.loc.x >= 420 || this.loc.x <= 0) {
      return new Spaceship(this.loc.moveLoc(-1 * this.speed, 0), (-1) * this.speed, this.color);
    }
    else {
      return new Spaceship(this.loc.moveLoc(this.speed, 0), this.speed, this.color);
    }
  }

  // shoots a bullet from the Spaceship
  public IGamePiece shoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new SBullet(this.loc.moveLoc(0, -20), 10, this.color);
  }

  // chekcs if this Spaceship is within the frame of the game
  public boolean isValid() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return true;
  }
}

//Represents an Invader
class Invader extends AGamePiece {

  /*
   * Fields:
   * super.loc... CartPt
   * super.color... Color
   * Methods:
   * this.draw()... WorldScene
   * this.isValid()... boolean
   * this.shoot()... IGamePiece
   * this.move(WorldScene)... IGamePiece
   * this.moveReg()... IGamePiece
   * Methods of fields:
   * super.loc.moveLoc()... CartPt
   */

  Invader() {
    super(new CartPt(100, 100), Color.RED);
  }

  Invader(CartPt loc, Color color) {
    super(loc, color);
    this.loc = loc;
  }

  // draws a WorldScene with this Invader
  public WorldScene draw(WorldScene acc) {
    /*
     * Parameters:
     * acc... WorldScene
     * Methods of Parameters:
     */
    return acc.placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, this.color), this.loc.x,
        this.loc.y);
  }

  // moves this Invader
  public IGamePiece move() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }
  
  // moves this Invader in its regular way
  public IGamePiece moveReg() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }

  // shoots a bullet from an Invader
  public IGamePiece shoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }

  // checks whether this invader is within the frame of the game
  public boolean isValid() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.color.equals(Color.RED);
  }
}

//Represents a Spaceship bullet
class SBullet extends AGamePiece {
  int speed;

  /*
   * Fields:
   * super.loc... CartPt
   * super.color... Color
   * this.speed... int
   * Methods:
   * this.draw()... WorldScene
   * this.isValid()... boolean
   * this.shoot()... IGamePiece
   * this.move(WorldScene)... IGamePiece
   * this.moveReg()... IGamePiece
   * Methods of fields:
   * super.loc.moveLoc(int, int)... CartPt
   */

  SBullet() {
    super(new CartPt(0, 0), Color.BLACK);
    this.speed = 5;
  }

  SBullet(CartPt loc, int speed, Color color) {
    super(loc, color);
    this.speed = speed;
  }


  // draws a WorldScene with this Spaceship Bullet
  public WorldScene draw(WorldScene acc) {
    /*
     * Parameters:
     * acc... WorldScene
     * Methods of Parameters:
     */
    if (loc.x < 420 && loc.y < 500) {
      return acc.placeImageXY(new EllipseImage(5, 5, OutlineMode.SOLID, this.color), this.loc.x,
          this.loc.y);
    }
    return acc;
  }

  // moves this Spaceship bullet
  public IGamePiece move() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }
  
  // moves this Spaceship Bullet in its regular way
  public IGamePiece moveReg() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new SBullet(this.loc.moveLoc(0, -speed), this.speed, this.color);
  }

  // shoots this Spaceship Bullet
  public IGamePiece shoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }

  // checks if this Spaceship Bullet is within the frame of the game
  public boolean isValid() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this.loc.y < 500 && this.loc.y > 0;
  }
}

//Represents an Invader bullet
class IBullet extends AGamePiece {
  int speed;

  /*
   * Fields:
   * super.loc... CartPt
   * super.color... Color
   * this.speed... int
   * Methods:
   * this.draw()... WorldScene
   * this.isValid()... boolean
   * this.shoot()... IGamePiece
   * this.move(WorldScene)... IGamePiece
   * this.moveReg()... IGamePiec
   * Methods of fields:
   * super.loc.moveLoc()... CartPt
   */

  IBullet(CartPt loc, int speed, Color color) {
    super(loc, color);
    this.speed = speed;
  }

  // draws a WorldScene with this Invader bullet
  public WorldScene draw(WorldScene acc) {
    /*
     * Parameters:
     * acc... WorldScene
     * Methods of Parameters:
     */
    if (loc.x < 420 && loc.y < 500) {
      return acc.placeImageXY(new EllipseImage(5, 5, OutlineMode.SOLID, this.color), this.loc.x,
          this.loc.y);
    }
    return acc;
  }

  // moves this Invader bullet
  public IGamePiece move() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }

  // moves this this Invader bullet
  public IGamePiece moveReg() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return new IBullet(new CartPt(this.loc.x, this.loc.y + speed), this.speed, this.color);
  }

  // shoots this Invader bullet
  public IGamePiece shoot() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return this;
  }

  // returns whether the invader bullet is in the frame
  public boolean isValid() {
    /*
     * Parameters:
     * Methods of parameters:
     */
    return (this.loc.y < 500 && this.loc.y > 0);
  }
}

//Represents a Cartesian point
class CartPt {
  int x;
  int y;
  /*
   * Fields:
   * this.x... int
   * this.y... int
   * Methods:
   * this.moveLoc(int, int)... CartPt
   * Methods of fields:
   */

  CartPt(int x, int y) {
    this.x = x;
    this.y = y;
  }

  // moves the CartPt according to a given value
  CartPt moveLoc(int x, int y) {
    /*
     * Parameters:
     * x... int
     * y... int
     * Methods of parameters:
     */
    return new CartPt(this.x + x, this.y + y);
  }
}

class ExamplesGame {

  CartPt pt1 = new CartPt(100, 100);
  CartPt pt2 = new CartPt(30, 40);
  CartPt pt3 = new CartPt(20, 20);
  CartPt pt0 = new CartPt(0, 0);

  IGamePiece invader1 = new Invader(this.pt1, Color.red);
  IGamePiece invader2 = new Invader(this.pt2, Color.RED);

  IList<Integer> list1 = new ConsList<Integer>(3, new ConsList<Integer>(2,
      new ConsList<Integer>(1, new ConsList<Integer>(1, new MtList<Integer>()))));
  IList<Integer> list2 = new ConsList<Integer>(3,
      new ConsList<Integer>(2, new ConsList<Integer>(4, new MtList<Integer>())));
  IList<String> stringsTest = new ConsList<String>("Hello",
      new ConsList<String>("goodbye", new MtList<String>()));
  IList<CartPt> lop = new ConsList<CartPt>(this.pt2,
      new ConsList<CartPt>(this.pt3, new MtList<CartPt>()));
  IList<CartPt> Points2 = new ConsList<CartPt>(this.pt1, this.lop);
  IList<String> mt = new MtList<String>();

  CartPt moved = new CartPt(105, 100);
  IGamePiece movedInvader = new Invader(this.moved, Color.RED);

  SpaceInvaders game = new SpaceInvaders();
  IList<IGamePiece> sbullets = new ConsList<IGamePiece>(
      new SBullet(new CartPt(130, 350), 10, Color.BLACK), new ConsList<IGamePiece>(
          new SBullet(new CartPt(90, 300), 10, Color.BLACK), new MtList<IGamePiece>()));
  IList<IGamePiece> ibullets = new ConsList<IGamePiece>(
      new IBullet(new CartPt(290, 400), 10, Color.RED), new MtList<IGamePiece>());
  SpaceInvaders game2 = new SpaceInvaders(new Utils().buildList2D(9, 4, new Func2D()),
      new Spaceship(new CartPt(400, 480), 10, Color.BLACK), sbullets, ibullets, 0);

  IList<IGamePiece> sbullets2 = new ConsList<IGamePiece>(
      new SBullet(new CartPt(130, 550), 10, Color.BLACK), new ConsList<IGamePiece>(
          new SBullet(new CartPt(90, 300), 10, Color.BLACK), new MtList<IGamePiece>()));

  IGamePiece spaceship1 = new Spaceship(this.pt1, 1, Color.BLACK);

  IList<IGamePiece> spaceshipList = new ConsList<IGamePiece>(this.spaceship1,
      new MtList<IGamePiece>());

  IList<CartPt> emptyPT = new MtList<CartPt>();

  boolean testRemoveBullets(Tester t) {
    return t.checkExpect(sbullets2.filter(new RemoveBullets()),
        new ConsList<IGamePiece>(new SBullet(new CartPt(90, 300), 10, Color.BLACK),
            new MtList<IGamePiece>()))
        && t.checkExpect(sbullets.filter(new RemoveBullets()), this.sbullets);
  }

  boolean testCheckSpaceship(Tester t) {
    return t.checkExpect(this.spaceshipList.filter(new CheckSpaceship(this.pt2)),
        new MtList<IGamePiece>())
        && t.checkExpect(this.spaceshipList.filter(new CheckSpaceship(this.pt0)),
            new MtList<IGamePiece>());
  }

  boolean testBigBang(Tester t) {
    return game.bigBang(420, 490, 0.25);
    // return game2.bigBang(500, 500, 0.25);
  }

  boolean testFilter(Tester t) {
    return t.checkExpect(list1.filter(new BiggerThan(1)),
        new ConsList<Integer>(3, new ConsList<Integer>(2, new MtList<Integer>())))
        && t.checkExpect((new MtList<Integer>()).filter(new BiggerThan(1)), new MtList<Integer>());
  }

  boolean testFold(Tester t) {
    return t.checkExpect(stringsTest.foldr(new StringLengthSum(), 0), 12)
        && t.checkExpect(this.mt.foldr(new StringLengthSum(), 0), 0);
  }

  boolean testOrMap(Tester t) {
    return t.checkExpect(list2.ormap(new BiggerThan(1)), true)
        && t.checkExpect(list1.ormap(new BiggerThan(1)), true)
        && t.checkExpect((new MtList<Integer>()).ormap(new BiggerThan(1)), false)
        && t.checkExpect(list2.ormap(new BiggerThan(5)), false);
  }

  boolean testAndMap(Tester t) {
    return t.checkExpect(list1.andmap(new BiggerThan(1)), false)
        && t.checkExpect((new MtList<Integer>()).andmap(new BiggerThan(1)), false)
        && t.checkExpect((new ConsList<Integer>(3, new ConsList<Integer>(4, new MtList<Integer>())))
            .andmap(new BiggerThan(1)), true);
  }

  boolean testLength(Tester t) {
    return t.checkExpect(list2.length(), 3) && t.checkExpect(list1.length(), 4)
        && t.checkExpect(stringsTest.length(), 2);
  }

  boolean testDraw(Tester t) {
    return t.checkExpect(this.invader1.draw(new WorldScene(600, 400)),
        new WorldScene(600, 400)
            .placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 100, 100))
        && t.checkExpect(this.invader2.draw(new WorldScene(600, 400)), new WorldScene(600, 400)
            .placeImageXY(new RectangleImage(20, 20, OutlineMode.SOLID, Color.RED), 30, 40));
  }

  boolean testMoveReg(Tester t) {
    return t.checkExpect(new SBullet(new CartPt(20, 50), 9, Color.BLACK).moveReg(),
        new SBullet(new CartPt(20, 41), 9, Color.BLACK))
        && t.checkExpect(new Spaceship(new CartPt(210, 250), -5, Color.BLACK).moveReg(),
            new Spaceship(new CartPt(205, 250), -5, Color.BLACK))
        && t.checkExpect(new Spaceship(new CartPt(420, 0), 5, Color.BLACK).moveReg(),
            new Spaceship(new CartPt(415, 0), -5, Color.BLACK));
  }

  boolean testMoveRegFunc(Tester t) {
    IList<IGamePiece> sbullets1 = sbullets.map(new MoveRegFunc());
    IList<IGamePiece> sbullets2 = new ConsList<IGamePiece>(
        new SBullet(new CartPt(130, 340), 10, Color.BLACK), new ConsList<IGamePiece>(
            new SBullet(new CartPt(90, 290), 10, Color.BLACK), new MtList<IGamePiece>()));
    IList<IGamePiece> ibullets1 = ibullets.map(new MoveRegFunc());
    IList<IGamePiece> ibullets2 = new ConsList<IGamePiece>(
        new IBullet(new CartPt(290, 410), 10, Color.RED), new MtList<IGamePiece>());

    return t.checkExpect(sbullets1, sbullets2) && t.checkExpect(ibullets1, ibullets2)
        && t.checkExpect(new MtList<IGamePiece>().map(new MoveRegFunc()), new MtList<IGamePiece>());
  }

  boolean testLeftKey(Tester t) {
    SpaceInvaders game3 = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK), new MtList<IGamePiece>(),
        new MtList<IGamePiece>(), 0);
    SpaceInvaders game4 = game3.leftKey();
    return t.checkExpect(game4.spaceship, new Spaceship(new CartPt(240, 480), -10, Color.BLACK))
        && t.checkExpect(game4.sbullets, new MtList<IGamePiece>())
        && t.checkExpect(game4.ibullets, new MtList<IGamePiece>());
  }

  boolean testRightKey(Tester t) {
    SpaceInvaders game3 = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), -10, Color.BLACK), new MtList<IGamePiece>(),
        new MtList<IGamePiece>(), 0);
    SpaceInvaders game4 = game3.rightKey();
    return t.checkExpect(game4.spaceship, new Spaceship(new CartPt(260, 480), 10, Color.BLACK))
        && t.checkExpect(game4.sbullets, new MtList<IGamePiece>())
        && t.checkExpect(game4.ibullets, game3.ibullets);
  }

  boolean testOnKeyReleased(Tester t) {
    SpaceInvaders game3 = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK), new MtList<IGamePiece>(),
        new MtList<IGamePiece>(), 0);
    SpaceInvaders left = game3.onKeyReleased("left");
    SpaceInvaders right = game3.onKeyReleased("right");
    SpaceInvaders space = game3.onKeyReleased(" ");
    return t.checkExpect(left.spaceship, new Spaceship(new CartPt(240, 480), -10, Color.BLACK))
        && t.checkExpect(right.spaceship, new Spaceship(new CartPt(240, 480), -10, Color.BLACK))
        && t.checkExpect(space.sbullets, new ConsList<IGamePiece>(
            new SBullet(new CartPt(250, 460), 10, Color.BLACK), new MtList<IGamePiece>()));
  }

  boolean testSpaceKey(Tester t) {
    SpaceInvaders game3 = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK), new MtList<IGamePiece>(),
        new MtList<IGamePiece>(), 0);
    SpaceInvaders game4 = game3.spaceKey();
    return t.checkExpect(game4.spaceship, new Spaceship(new CartPt(250, 480), 10, Color.BLACK))
        && t.checkExpect(game4.sbullets,
            new ConsList<IGamePiece>(new SBullet(new CartPt(250, 460), 10, Color.BLACK),
                new MtList<IGamePiece>()))
        && t.checkExpect(game4.ibullets, new MtList<IGamePiece>());
  }

  boolean testShiftSpaceship(Tester t) {
    SpaceInvaders game = new SpaceInvaders();
    SpaceInvaders shifted = game.shiftSpaceship();
    return t.checkExpect(game.ibullets, shifted.ibullets)
        && t.checkExpect(shifted.spaceship, new Spaceship(new CartPt(240, 480), -10, Color.BLACK))
        && t.checkExpect(game.time, shifted.time);
  }

  boolean testMoveSpaceship(Tester t) {
    SpaceInvaders game = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK),
        new ConsList<IGamePiece>(new SBullet(new CartPt(100, 50), 5, Color.BLACK),
            new MtList<IGamePiece>()),
        new ConsList<IGamePiece>(new IBullet(new CartPt(200, 70), 5, Color.RED),
            new MtList<IGamePiece>()),
        0);
    SpaceInvaders moved = game.moveSpaceship();
    return t.checkExpect(moved.ibullets,
        new ConsList<IGamePiece>(new IBullet(new CartPt(200, 75), 5, Color.RED),
            new MtList<IGamePiece>()))
        && t.checkExpect(moved.sbullets,
            new ConsList<IGamePiece>(new SBullet(new CartPt(100, 45), 5, Color.BLACK),
                new MtList<IGamePiece>()))
        && t.checkExpect(moved.spaceship, new Spaceship(new CartPt(260, 480), 10, Color.BLACK));
  }

  boolean testOnTick(Tester t) {
    SpaceInvaders game = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK),
        new ConsList<IGamePiece>(new SBullet(new CartPt(100, 50), 5, Color.BLACK),
            new MtList<IGamePiece>()),
        new ConsList<IGamePiece>(new IBullet(new CartPt(200, 70), 5, Color.RED),
            new MtList<IGamePiece>()),
        0);
    SpaceInvaders moved = game.onTick();
    return t.checkExpect(moved.ibullets,
        new ConsList<IGamePiece>(new IBullet(new CartPt(200, 75), 5, Color.RED),
            new MtList<IGamePiece>()))
        && t.checkExpect(moved.sbullets,
            new ConsList<IGamePiece>(new SBullet(new CartPt(100, 45), 5, Color.BLACK),
                new MtList<IGamePiece>()))
        && t.checkExpect(moved.spaceship, new Spaceship(new CartPt(260, 480), 10, Color.BLACK));
  }

  boolean testPoints(Tester t) {
    return t.checkExpect(sbullets.map(new Points()),
        new ConsList<CartPt>(new CartPt(130, 350),
            new ConsList<CartPt>(new CartPt(90, 300), new MtList<CartPt>())))
        && t.checkExpect((new MtList<IGamePiece>()).map(new Points()), new MtList<CartPt>())
        && t.checkExpect(ibullets.map(new Points()),
            new ConsList<CartPt>(new CartPt(290, 400), new MtList<CartPt>()));
  }

  boolean testIsValid(Tester t) {
    return t.checkExpect(new IBullet(new CartPt(100, 560), 10, Color.RED).isValid(), false)
        && t.checkExpect(new SBullet(new CartPt(100, -50), 10, Color.BLACK).isValid(), false)
        && t.checkExpect(new IBullet(new CartPt(250, 250), 10, Color.RED).isValid(), true);
  }

  boolean testLoc(Tester t) {
    return t.checkExpect(invader1.loc(), new CartPt(100, 100))
        && t.checkExpect((new SBullet(new CartPt(130, 350), 10, Color.BLACK)).loc(),
            new CartPt(130, 350))
        && t.checkExpect(new Spaceship().loc(), new CartPt(250, 480));
  }

  boolean testMove(Tester t) {
    return t.checkExpect(invader1.move(), invader1)
        && t.checkExpect((new SBullet(new CartPt(130, 350), 10, Color.BLACK)).move(),
            (new SBullet(new CartPt(130, 350), 10, Color.BLACK)))
        && t.checkExpect(new Spaceship().move(),
            new Spaceship(new CartPt(240, 480), -10, Color.BLACK));
  }

  boolean testShipShoot(Tester t) {
    SpaceInvaders game = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK),
        new ConsList<IGamePiece>(new SBullet(new CartPt(100, 50), 5, Color.BLACK),
            new MtList<IGamePiece>()),
        new ConsList<IGamePiece>(new IBullet(new CartPt(200, 70), 5, Color.RED),
            new MtList<IGamePiece>()),
        0);
    SpaceInvaders shooted = game.shipShoot();
    return t.checkExpect(shooted.spaceship, game.spaceship)
        && t.checkExpect(shooted.ibullets, game.ibullets)
        && t.checkExpect(shooted.sbullets,
            new ConsList<IGamePiece>(new SBullet(new CartPt(250, 460), 10, Color.BLACK),
                new ConsList<IGamePiece>(new SBullet(new CartPt(100, 50), 5, Color.BLACK),
                    new MtList<IGamePiece>())));
  }

  boolean testContainsPoint(Tester t) {
    return t.checkExpect(this.lop.ormap(new ContainsPoint(this.invader1.loc())), false)
        && t.checkExpect(this.emptyPT.ormap(new ContainsPoint(this.invader1.loc())), false)
        && t.checkExpect(this.Points2.ormap(new ContainsPoint(this.invader1.loc())), true);
  }

  boolean testBiggerThan(Tester t) {
    return t.checkExpect(list1.filter(new BiggerThan(1)),
        new ConsList<Integer>(3, new ConsList<Integer>(2, new MtList<Integer>())))
        && t.checkExpect(list2.ormap(new BiggerThan(1)), true)
        && t.checkExpect(list1.ormap(new BiggerThan(1)), true);
  }

  boolean testStringLengthSum(Tester t) {
    return t.checkExpect(stringsTest.foldr(new StringLengthSum(), 0), 12)
        && t.checkExpect(this.mt.foldr(new StringLengthSum(), 0), 0);
  }

  boolean testShoot(Tester t) {
    return t.checkExpect(new IBullet(new CartPt(0, 0), 5, Color.RED).shoot(),
        new IBullet(new CartPt(0, 0), 5, Color.RED))
        && t.checkExpect(new Spaceship().shoot(),
            new SBullet(new CartPt(250, 460), 10, Color.BLACK))
        && t.checkExpect(new Invader().shoot(), new Invader());
  }

  boolean testInvaderShoot(Tester t) {
    SpaceInvaders game = new SpaceInvaders();
    SpaceInvaders game1 = game.invaderShoot();
    SpaceInvaders game2 = new SpaceInvaders(new Utils().buildList2D(8, 4, new Func2D()),
        new Spaceship(new CartPt(250, 480), 10, Color.BLACK),
        new ConsList<IGamePiece>(new SBullet(new CartPt(100, 50), 5, Color.BLACK),
            new MtList<IGamePiece>()),
        new ConsList<IGamePiece>(new IBullet(new CartPt(100, 150), 5, Color.RED),
            new ConsList<IGamePiece>(new IBullet(new CartPt(100, 150), 5, Color.RED),
                new ConsList<IGamePiece>(new IBullet(new CartPt(100, 150), 5, Color.RED),
                    new ConsList<IGamePiece>(new IBullet(new CartPt(100, 150), 5, Color.RED),
                        new ConsList<IGamePiece>(new IBullet(new CartPt(100, 150), 5, Color.RED),
                            new ConsList<IGamePiece>(
                                new IBullet(new CartPt(100, 150), 5, Color.RED),
                                new ConsList<IGamePiece>(
                                    new IBullet(new CartPt(100, 150), 5, Color.RED),
                                    new ConsList<IGamePiece>(
                                        new IBullet(new CartPt(100, 150), 5, Color.RED),
                                        new ConsList<IGamePiece>(
                                            new IBullet(new CartPt(100, 150), 5, Color.RED),
                                            new ConsList<IGamePiece>(
                                                new IBullet(new CartPt(200, 70), 5, Color.RED),
                                                new MtList<IGamePiece>())))))))))),
        0);
    SpaceInvaders game3 = game2.invaderShoot();
    return t.checkExpect(game1.ibullets.length(), game.ibullets.length() + 1)
        && t.checkExpect(game3.ibullets.length(), game2.ibullets.length());
  }
}