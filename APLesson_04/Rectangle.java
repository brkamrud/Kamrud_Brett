import java.util.Scanner;
import java.text.DecimalFormat;

public class Rectangle
{
	public static void main(String[]args)
	{
		Rectangle method = new Rectangle();
		
		Scanner kb = new Scanner(System.in);
		DecimalFormat df = new DecimalFormat(".#####");
		
		System.out.println("Enter the width of your rectangle:");
		double width = kb.nextDouble();
		
		System.out.println("Enter the length:");
		double length = kb.nextDouble();
		
		double perimeter = method.calcPerim(width, length);
		
		System.out.println("Your rectangle is " + df.format(perimeter) + " sq ft around.");
		
	}
	
	public double calcPerim(double w, double l)
	{
		return (w * 2) + (l * 2);
		
	}
	
}