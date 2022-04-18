/*
* Activity 4.9.3 and 4.9.4 
* A text-based Fishing Game 
* 
* V1.0
* 6/20/2020
* Copyright(c) 2019 PLTW to present. All rights reserved
*/
import java.util.Scanner;
import java.util.ArrayList;

public class GameUI
{
  // Utilities
  Scanner console;

  private Player player;

  /**
   * Constructor for the GameTextBased class.
   */
  public GameUI(Player player)
  {
    this.player = player;
    console = new Scanner(System.in);
  }

  /*
   * Append to the output area and prompt for input
   * 
   * @param s a String to append to the output
   */
  public void setOutput(String s)
  {
    System.out.print(s);
    getInput();
  }

  /*
   * Show inventory items
   */
  public void printInventory()
  {
    System.out.println("\nYour current inventory is: ");
    ArrayList<LakeObject> inventoryItems = player.getInventory();

    for (LakeObject item : inventoryItems)
    {
      if(!item.equals("empty"))
      {
        System.out.println("\t" + item.getObjectName() + ": $"+ item.getCost());    
      }
    }
  }

  /*
   * Add a player to the game and set the inventory and wallet values.
   * 
   * @param p player to be added
   */
  public void playGame()
  {
    System.out.print("\nWelcome to the Fishing Game");
    goToForest();
  }

  /*---------- public methods not to be called directly ----------*/

  /*
   * get the user's command
   */
  public void getInput()
  {
    String input = console.nextLine();
    execCommand(input);
  }

  /*---------- private/convenience methods ----------*/
 
  /**
   * Execute the command entered by the player
   * 
   * @param input String command entered by the player
   */
  private void execCommand(String input)
  {
    if (input.equals("quit") || input.equals("q") || input.equals("Q"))
    {
      System.out.println("Thank you, bye!");
    }
    else if (input.equals("shop") || input.equals("S"))
    {
      goToShop();
    }
    else if (input.equals("lake") || input.equals("L"))
    {
      goToLake();
    }
    else if ((input.equals("yes") || input.equals("y")) && player.getCurrentLocation().equals("lake"))
    {
      goFish();
      goToLake();
    }
    else if ((input.equals("no") || input.equals("n")) && player.getCurrentLocation().equals("lake"))
    {
      goToForest();     
    }
    else if ((input.equals("bait") || input.equals("b")) && player.getCurrentLocation().equals("shop"))
    {
      System.out.println("Buy Bait");
      buyBait();
      goToShop();
    }
    else if ((input.equals("hook") || input.equals("h")) && player.getCurrentLocation().equals("shop"))
    {
      System.out.println("Buy Hook");
      buyHook();
      goToShop();
    }
    else if ((input.equals("sell") || input.equals("s")) && player.getCurrentLocation().equals("shop"))
    {
      System.out.println("Sell Fish");
      sellFish();
      goToShop();
    }
    else if ((input.equals("leave") || input.equals("l") || input.equals("Leave")))
    {
      System.out.println("Thank you, return anytime!");
      goToForest();  
    }
    else
    {
      System.out.println("Not a valid command.");
      // reset output and input
      if (player.getCurrentLocation().equals("forest"))
        goToForest();
      else if (player.getCurrentLocation().equals("lake"))
        goToLake();
      else if (player.getCurrentLocation().equals("shop"))
        goToShop();
    }
  }

  /*---------- Private Game Area methods ----------*/

  /* Game Forest Area */
  /**
   * Take the player to the forest.
   */
  private void goToForest()
  {
    System.out.println("");
    player.setCurrentLocation("forest");
    printInventory();
    setOutput("\nYou are in a forest. Where would you like to go?\n---- Enter command Lake(L), Shop(S), or Quit(Q): ");
  }

  /* Game Shop Area */
  private void goToShop()
  {
    player.setCurrentLocation("shop");
    printInventory();
    setOutput("\nYou are at the shop. What would you like to buy?\n---- Enter command bait(b), hook(h), sell fish(s), or leave (l): ");
  }

  private void buyBait()
  {
    Bait b = new Bait();

    if (player.roomInInventory())
    {
      if (player.getWallet() >= b.getCost())
      {
        player.updateWallet(-1 * b.getCost());
        player.updateInventory(b, true);
        System.out.println(b.say());
      }
      else
        System.out.println("You cannot afford a bait.");
    }
    else
      System.out.println("You have no room in your inventory.");
  }

  private void buyHook()
  {
    Hook h = new Hook();

    if (player.roomInInventory())
    {
      if (player.getWallet() >= h.getCost())
      {
        player.updateWallet(-1 * h.getCost());
        player.updateInventory(h, true);
        System.out.println(h.say());
      }
      else
        System.out.println("You cannot afford a hook.");
    }
    else
      System.out.println("You have no room in your inventory.");
  }

  private void sellFish()
  {
    System.out.print("What is the location of the fish you want to sell? ");
    String input = console.nextLine();
    try
    {
      int fishNum = Integer.parseInt(input);
      boolean sold = player.loseItem("Fish", fishNum, true);
      if (sold)
        System.out.println("Thank you. Your wallet has been updated.");
      else
        System.out.println("You did not have a fish at that location. Don't try to trick me!");
    }
    catch (IndexOutOfBoundsException iob)
    {
      System.out.println("You do not have that many items in you inventory."); 
      sellFish();
    }
    catch (NumberFormatException nf)
    {
      System.out.println("Please enter a number for the location of your fish."); 
      sellFish();
    }
  }

  /* Game Lake Area */
  public void goToLake()
  {
    player.setCurrentLocation("lake");
    printInventory();
    setOutput("\nYou are at the lake. Would you like to fish?\n---- Enter command yes(y), no(n), or leave(l): ");
  }

  public void goFish()
  {
    // generate a Lake full of new LakeObjects
    LakeObject[] lakeObjects = new LakeObject[4];
    lakeObjects[0] = new Fish();
    lakeObjects[1] = new Wallet();
    lakeObjects[2] = new Fish();
    lakeObjects[3] = new Boot();

    if (player.hasHook() && player.hasBait())
    {
      int i = (int) (Math.random() * lakeObjects.length) + 1;
      Hook strongestHook = player.getStrongestHook();
      System.out.println("\n");

      if (i >= lakeObjects.length)
      {
        System.out.println("\nYou lost your hook  :(");
        player.updateInventory(new Hook(), false);
      } 
      else if (lakeObjects[i].wasCaught(strongestHook)) 
      {
        if (lakeObjects[i].getObjectName().equals("Wallet"))
        {
          player.updateWallet(lakeObjects[i].getCost());
          System.out.println("You caught a Wallet!");
          System.out.println(lakeObjects[i].say());
          System.out.println("Money from the wallet was added to your inventory");
        }
        else
        {
          if(player.roomInInventory())
          {
            player.updateInventory(lakeObjects[i], true);
            System.out.println(lakeObjects[i].say());
          }
          else
            System.out.println("You have no room in your inventory.");
        }
      }
      else
      {
        System.out.println("\nYour hook had a strength of " + strongestHook.getStrength());
        System.out.println("You needed at least a strength of " + lakeObjects[i].getWeight());
      }
    }
    else
      System.out.println("You need a hook and bait to go fishing. You can buy them at the shop.");
  }
}