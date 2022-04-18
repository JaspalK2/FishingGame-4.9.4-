/*
 * Activity 4.9.3
 */
public class Fish extends LakeObject
{
     @Override
public String say()
{
  return "You got a fish!";
}
@Override
 public int getCost(){
   //System.out.println("Cost: "+super.getCost()+"Weight: "+super.getWeight());
   return super.getCost() * super.getWeight();
 }
 
  


}