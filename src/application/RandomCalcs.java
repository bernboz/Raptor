package application;

import java.util.Random;

public class RandomCalcs {
	
	static Random generator = new Random();
	
	public static int randomXLoc(int minX, int maxX) {
		int randomX = generator.nextInt((maxX - minX) + 1) + minX;		
		return randomX;
	}

	public static int randomYLoc(int minY, int maxY) {
		int randomY = generator.nextInt((maxY - minY) + 1) + minY;
		return randomY;
	}
	
	public static int randomTile() {
		int randomTileNumber = generator.nextInt((6 - 1) + 1) + 1;
		return randomTileNumber;
	}
	
}
