package application;

public class Tile {
	// Tile is a 3x3 grid of spaces, not including the first two X-coords and last two X-coords.
	
	static int tileNumber;
	static int sciSpawnNumber;
	static int sciReinforceNumber;
	
	public static int assignTileNumber(int spaceX, int spaceY) {
		if ((spaceX > 1 && spaceX < 5) && (spaceY >= 0 && spaceY < 3)) {
			tileNumber = 1;
		} else if ((spaceX > 1 && spaceX < 5) && (spaceY > 2/* && spaceY < 6*/)) {
			tileNumber = 2;
		} else if ((spaceX > 4 && spaceX < 7) && (spaceY >= 0 && spaceY < 3)) {
			tileNumber = 3;
		} else if ((spaceX > 4 && spaceX < 7) && (spaceY > 2/* && spaceY < 6*/)){
			tileNumber = 4;
		} else if ((spaceX > 7 && spaceX < 11) && (spaceY >= 0 && spaceY < 3)){
			tileNumber = 5;
		} else if ((spaceX > 7 && spaceX < 11) && (spaceY > 2/* && spaceY < 6*/)) {
			tileNumber = 6;
		}
		return tileNumber;
	}

	
	public static int assignScientistSpawnTile(int spaceX, int spaceY) {
		if(spaceX == 1 && spaceY < 3) {
			sciSpawnNumber = 1;
		} else if (spaceX == 1 && spaceY > 2) {
			sciSpawnNumber = 2;
		} else if (spaceX == 11 && spaceY < 3) {
			sciSpawnNumber = 3;
		} else if (spaceX == 11 && spaceY > 2) {
			sciSpawnNumber = 4;
		}
		return sciSpawnNumber;
	}
}
