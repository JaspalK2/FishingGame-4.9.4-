/*
 * Activity 4.9.3 and 4.9.4
 */
public class Main
{
  public static void main(String[] args)
  {
    Player player = new Player();
    GameUI game = new GameUI(player);
    // start the game
    game.playGame();
  }
}