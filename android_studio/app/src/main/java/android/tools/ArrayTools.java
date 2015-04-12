package android.tools;

public class ArrayTools {

	public static int getMin(int[] array)
	{
		int min = array[0];
		for (int i = 1; i < array.length; i++) {
			if(min > array[i])
				min = array[i];
		}
		return min;
	}
	public static int getMax(int[] array)
	{
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if(max < array[i])
				max = array[i];
		}
		return max;
	}
	
	public static boolean IsInArray(int value, int[] array)
	{
		for (int i = 0; i < array.length; i++) {
			if(value == array[i])
				return true;
		}
		return false;
	}
}
