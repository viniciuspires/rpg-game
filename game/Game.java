package game;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Game {
	static Character[][] _map;
	static Character[][] map;

	static int MAP_HEIGHT = 20;
	static int MAP_WIDTH = 20;

	static int NPC_COUNT = 2;

	static int FRAME_RATE = 30;

	public static void main(String[] args) throws IOException, InterruptedException {
		populateMap();
		while ( true ) { // Game loop
			update();
			Thread.sleep( (long) (1000 / FRAME_RATE) );
		}
	}

	private static void update() {
		for (Character[] characters : map) {
			for (Character character : characters) {
				if ( character != null ) {
					System.out.println("Updating character "+character);
					character.update();
				}
			}
		}
		
		_map = map.clone();
		redraw();
	}

	static void populateMap() {
		map = new Character[MAP_HEIGHT][MAP_WIDTH];
		
		for (int i = 0; i < NPC_COUNT; i++) {
			Point blank = findRandomBlankSpace();
			map[blank.getY()][blank.getX()] = makeRandomNpc();
		}
		
		Point playerPoint = findRandomBlankSpace();
		map[playerPoint.getY()][playerPoint.getX()] = new Player();
		
		_map = map.clone();
		
		redraw();
	}
	
	private static Point findRandomBlankSpace() {
		Point blankSpace = null;
		do {
			int x = getRandomX();
			int y = getRandomY();
			if (map[y][x] == null) {
				blankSpace = new Point(x, y);
			}
		} while (blankSpace == null);
		
		return blankSpace;
	}

	static void redraw() {
		System.out.println("Display: "+new SimpleDateFormat("HH:mm:ss").format(new Date()));
		List<Character> activeCharacters = new ArrayList<Character>();
		
		System.out.print("   ");
		for (int x=0; x<MAP_HEIGHT; x++) {
			System.out.print("| ");
			System.out.printf("%2d",x);
		}
		System.out.println("|");
		
		int count = 0;
		for (Character[] row : _map) {
			System.out.printf("%2d ",count);
			count++;
			for (Character column : row) {
				String representation = "_";
				if (column != null) {
					representation = column.toString();
					activeCharacters.add(column);
				}
				System.out.print("| " + representation + " ");
			}
			System.out.println("|");
		}
		
		for (Character c : activeCharacters) {
			System.out.print("HEALTH: "+c.getHealth()+" STRENGTH: "+c.getStrength()+" RESISTENCE: "+c.getResistence()+" - ");
			System.out.println(c.toString()+" ("+c.getClass().getSimpleName()+")");
		}
	}

	static Character makeRandomNpc() {
		return new Enemy();
	}

	static int getRandomY() {
		return (int) (Math.random() * MAP_HEIGHT);
	}

	static int getRandomX() {
		return (int) (Math.random() * MAP_WIDTH);
	}

	public static Point findPlayerSpot() {
		Point spot = null;
		
		for (int y=0; y<MAP_HEIGHT;y++) {
			for (int x=0; x<MAP_WIDTH;x++) {
				if ( map[y][x] instanceof Player ) {
					spot = new Point(x, y);
				}
			}
		}
		
		return spot;
	}

	public static Point findSpot(Character thing) {
		Point spot = null;
		
		for (int y=0; y<MAP_HEIGHT;y++) {
			for (int x=0; x<MAP_WIDTH;x++) {
				if ( thing.equals(map[y][x]) ) {
					spot = new Point(x, y);
				}
			}
		}
		
		return spot;
	}

	public static boolean isCloseTo(Point point1, Point point2) {
		return (point1.getX()+1 == point2.getX()
			|| point1.getX()-1 == point2.getX())
			&& ( point1.getY()+1 == point2.getY()
			|| point1.getY()-1 == point2.getY() );
	}

	public static Player getPlayer() {
		Point p = findPlayerSpot();
		return (Player) map[p.getY()][p.getX()];
	}

	public static void removeFromMap(Character enemy) {
		Point spot = findSpot(enemy);
		map[spot.getY()][spot.getX()] = null;
	}

	public static void over() {
		System.out.println("GAME OVER");
		System.exit(0);
	}

	public static void moveCloserTo(Point from, Point to, Character c) {
		int walkX = 0;
		int walkY = 0;
		
		if ( to.getX() > from.getX() ) {
			walkX = 1;
		} else if ( to.getX() < from.getX() ) {
			walkX = -1;
		}
		
		if ( to.getY() > from.getY() ) {
			walkY = 1;
		} else if ( to.getY() < from.getY() ) {
			walkY = -1;
		}
		
		if ( map[ from.getY()+walkY ][ from.getX()+walkX ] == null ) {
			map[ from.getY() ][ from.getX() ] = null;
			map[ from.getY()+walkY ][ from.getX()+walkX ] = c;
		}
	}
}
