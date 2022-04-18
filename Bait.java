/*
 * Activity 4.9.3
 */
public class Bait extends LakeObject
{
  private int cost =10;
  public Bait(){
    super();
    super.setCost(cost);
  }
     @Override
public String say()
{
  return "you got some bait!";
}
}