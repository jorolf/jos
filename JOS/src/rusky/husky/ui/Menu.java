package rusky.husky.ui;

import java.util.EnumSet;

import rusky.husky.Axes;
import rusky.husky.Direction;
import rusky.husky.containers.FlowContainer;

public class Menu extends FlowContainer<MenuItem>{

	public Menu() {
		setDirection(EnumSet.of(Direction.Vertical));
		setAutoSizeAxes(EnumSet.of(Axes.Y));
	}

}
