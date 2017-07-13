package rusky.husky;

import java.util.EnumSet;

public enum Direction {
	Horizontal,
	Vertical;
	
	public static EnumSet<Direction> both(){
		return EnumSet.allOf(Direction.class);
	}
}
