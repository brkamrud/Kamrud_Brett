import java.util.Scanner;
import java.text.DecimalFormat;

public class Average
{
	static DecimalFormat df = new DecimalFormat(".#####");
	
	
	public static void main(String[]args)
	{
		Scanner kb = new Scanner(System.in);
		Average method = new Average();
		
		System.out.println("Enter a number:");
		double num1 = kb.nextDouble();
		
		System.out.println("Enter another:");
		double num2 = kb.nextDouble();
		
		System.out.println("Enter one more:");
		double num3 = kb.nextDouble();
		
		method.print(method.average(num1, num2, num3));
		
	}
	
	public double average(double x, double y, double z)
	{
		return (x + y +z) / 3;
		
	}
	
	public void print(double avg)
	{
		System.out.println("The average of your numbers is " + df.format(avg) + ".");
		
	}
	
	
}