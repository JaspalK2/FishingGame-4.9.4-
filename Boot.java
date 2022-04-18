/*
 * Activity 4.9.3
 */
public class Boot extends LakeObject
{
  private int cost = 0;
  public Boot(){
  super();
  super.setCost(cost);
  }
     @Override
public String say()
{
  return "You now have a boot!";
}
     @Override
    public boolean wasCaught(Hook h){
        return true;
    }
    
}
