import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;


public class KnightAdventure
{


	public static void main(String[] args)
	{
		String name = null;
		String attack = null;
		String armor = null;
		int lvl = 0;
		int numEnemy = 0;
		int curEnemy = 0;
		int selection;
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		boolean status = true; //Determines if the adventure is restarted
		
		while(status)
		{
			System.out.println("Welcome to the JavaBean Forest.");
			System.out.print("Enter your name: ");
			name = input.next();
			
			
			do
			{
				try
				{
					System.out.print("\nPlease select your combat style. (Choose number)" +
							"\n1) Physical\n2) Ranged\n3) Magic\nYour choice: ");
					selection = input.nextInt();
					switch(selection)
					{
					case 1:
						attack = "Physical";
						status = false;
						break;
					case 2:
						attack = "Ranged";
						status = false;
						break;
					case 3:
						attack = "Magic";
						status = false;
						break;
					default:
						System.out.println("That was an invalid selection please try again.");
					}
				}
				catch(InputMismatchException inputMismatchException)
				{
					System.err.printf("\nException: %s", inputMismatchException);
					input.nextLine(); // Discard input so used can try again
					System.out.println("That was an invalid selection please try again.");
				}
			} while(status);
			
			status = true;
			do
			{
				try
				{
					System.out.print("\nPlease select your armor. (Choose number)" +
							"\n1) Heavy\n2) Medium \n3) Light\nYour choice: ");
					selection = input.nextInt();
					switch(selection)
					{
					case 1:
						armor = "Heavy";
						status = false;
						break;
					case 2:
						armor = "Medium";
						status = false;
						break;
					case 3:
						armor = "Light";
						status = false;
						break;
					default:
						System.out.println("That was an invalid selection please try again.");
					}
				}
				catch(InputMismatchException inputMismatchException)
				{
					System.err.printf("\nException: %s", inputMismatchException);
					input.nextLine(); // Discard input so user can try again
					System.out.println("That was an invalid selection please try again.");
				}
			} while(status);
			
			status = true;
			do
			{
				try
				{
					System.out.print("What level are you? ");
					selection = input.nextInt();
					if(selection > 0)
					{
						lvl = selection;
						status = false;
					}
					else
						System.out.println("That was an invalid selection please try again.");
					
				}
				catch(InputMismatchException inputMismatchException)
				{
					System.err.printf("\nException: %s", inputMismatchException);
					input.nextLine();
					System.out.println("That was an invalid selection please try again.");
				}
			} while(status);
			
			Hero hero = new Hero(name, armor, attack, lvl);
			
			status = true;
			do
			{
				try
				{
					System.out.print("What level of enemies would you like to encounter? ");
					selection = input.nextInt();
					if(selection > 0)
					{
						lvl = selection;
						status = false;
					}
					else
						System.out.println("That was an invalid selection please try again.");
				}
				catch(InputMismatchException inputMismatchException)
				{
					System.err.printf("\nException: %s", inputMismatchException);
					input.nextLine();
					System.out.println("That was an invalid selection please try again.");
				}
			} while(status);
			
			status = true;
			do
			{
				try
				{
					System.out.print("How many enemies would you like to encounter? ");
					selection = input.nextInt();
					if(selection > 0)
					{
						numEnemy = curEnemy = selection;
						status = false;
					}
					else
						System.out.println("That was an invalid selection please try again.");
					
				}
				catch(InputMismatchException inputMismatchException)
				{
					System.err.printf("\nException: %s", inputMismatchException);
					input.nextLine();
					System.out.println("That was an invalid selection please try again.");
				}
			} while(status);
			
			Fighter[] enemy = new Fighter[numEnemy];
			for(int i = 0; i < numEnemy; ++i)
			{
				enemy[i] = Fighter.getRandomEnemy(lvl);
			}
			
			System.out.println(hero.toString()); //display hero and enemy stats
			for(int i = 0; i < numEnemy; ++i)
			{
				System.out.println(enemy[i].toString());
			}
			
			System.out.println("Press any key to begin combat.");
			input.next();
			status = true;
			while (hero.getHealth() > 0 && curEnemy > 0) //status refers to all enemies dead
			{
				int curTar = 0;
				Random random = new Random();
				curTar = random.nextInt(numEnemy + 1); //Fight a random enemy
				
					if(curTar != numEnemy && enemy[curTar] == null) //If enemy is dead(null) fight first available
					{
						curTar = 0;
						while(curTar < numEnemy - 1 && enemy[curTar] == null)
							++curTar;
						
					}
					
					if (curTar == numEnemy)
					{
						System.out.println("Area Attack!");
						for(int i = 0; i < numEnemy; ++i)
						{
							if(enemy[i] != null)
							{
								try
								{
									enemy[i].takeDamage(enemy[i].fight(hero, enemy[i]));
								}
								catch (negativeDmgException e)
								{
									System.out.println("Damage was less than zero");
								}
								if(enemy[i].getHealth() <= 0)
								{
									enemy[i] = null;
									--curEnemy;
								}
							}
						}
					}
					else
					{
						try
						{
							enemy[curTar].takeDamage(enemy[curTar].fight(hero, enemy[curTar]));
						}
						catch (negativeDmgException e)
						{
							System.out.println("Damage was less than zero");
						}
						if(enemy[curTar].getHealth() <= 0)
						{
							enemy[curTar] = null;
							--curEnemy;
						}
					}
				
				
				for(int i = 0; i < numEnemy; ++i) //Enemies attack hero
				{
					if(enemy[i] != null) try
					{
						hero.takeDamage(hero.fight(hero, enemy[i]));
					}
					catch (negativeDmgException e)
					{
						System.out.println("Damage was less than zero");
					}
				}
			} //End while
			
			
			if(hero.getHealth() <= 0)
				System.out.print("\nThe hero was defeated. Would you like to try again (y/n)?");
							
			else
				System.out.print("You are victorious! Would you like to try again (y/n)?");
		
			switch(input.next())
			{
			default:
			case "n":
			case "N":
				System.out.println("Thank you for visiting JavaBean Forest.");
				status = false;
				break;
			case "y":
			case "Y":
				break;
			}
		} //end while
	} //end public

} //end class
