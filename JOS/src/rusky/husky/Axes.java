package rusky.husky;

import java.util.EnumSet;

public enum Axes {
	X,
	Y;
	
	public static EnumSet<Axes> both(){
		return EnumSet.allOf(Axes.class);
	}
	
	public static EnumSet<Axes> none(){
		return EnumSet.noneOf(Axes.class);
	}
}
