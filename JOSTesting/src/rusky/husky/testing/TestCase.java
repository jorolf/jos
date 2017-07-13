package rusky.husky.testing;

import java.util.EnumSet;

import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Direction;
import rusky.husky.Drawable;
import rusky.husky.containers.FlowContainer;

public abstract class TestCase extends Container<Drawable> {
	
	private FlowContainer<Button> buttons = new FlowContainer<>();
	
	public abstract String getDescription();
	
	public TestCase(){
		setRelativeSizeAxes(Axes.both());
	}
	
	public void reset(){
		clear();
		buttons = new FlowContainer<>();
		buttons.setWidth(100);
		buttons.setAutoSizeAxes(EnumSet.of(Axes.Y));
		buttons.setDirection(EnumSet.of(Direction.Vertical));
		add(buttons);
		internalReset();
	}
	
	protected abstract void internalReset();
	
	public void addButton(String text, Runnable action){
		Button b = new Button(text, action);
		b.setRelativeSizeAxes(EnumSet.of(Axes.X));
		b.setHeight(15);
		b.masked = true;
		b.cornerRadius = 15;
		buttons.add(b);
	}
}
