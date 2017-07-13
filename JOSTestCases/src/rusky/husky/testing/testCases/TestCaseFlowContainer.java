package rusky.husky.testing.testCases;

import java.awt.Color;
import java.util.Arrays;
import java.util.EnumSet;

import rusky.husky.Axes;
import rusky.husky.Direction;
import rusky.husky.Drawable;
import rusky.husky.containers.FlowContainer;
import rusky.husky.sprites.SpriteNumber;
import rusky.husky.testing.TestCase;

public class TestCaseFlowContainer extends TestCase {

	@Override
	public String getDescription() {
		return "Feel the flow";
	}
	
	@Override
	public void internalReset() {
		FlowContainer<Drawable> horizontal = new FlowContainer<>();
		FlowContainer<Drawable> vertical = new FlowContainer<>();
		FlowContainer<Drawable> both = new FlowContainer<>();
		add(Arrays.asList(horizontal, vertical, both));
		
		horizontal.setDirection(EnumSet.of(Direction.Horizontal));
		vertical.setDirection(EnumSet.of(Direction.Vertical));
		both.setDirection(Direction.both());
		
		horizontal.masked = true;
		vertical.masked = true;
		both.masked = true;
		
		horizontal.setRelativeSizeAxes(Axes.both());
		vertical.setRelativeSizeAxes(Axes.both());
		both.setRelativeSizeAxes(Axes.both());

		horizontal.setRelativePositionAxes(Axes.both());
		vertical.setRelativePositionAxes(Axes.both());
		both.setRelativePositionAxes(Axes.both());
		
		horizontal.setWidth(1/3f);
		vertical.setWidth(1/3f);
		both.setWidth(1/3f);
		
		horizontal.setX(0);
		vertical.setX(1/3f);
		both.setX(1/1.5f);
		
		for(int i = 0; i < 10; i++){
			SpriteNumber htext = new SpriteNumber(i);
			SpriteNumber vtext = new SpriteNumber(i);
			SpriteNumber btext = new SpriteNumber(i);
			
			htext.setHeight(20);
			vtext.setHeight(20);
			btext.setHeight(20);
			
			htext.setColor(Color.BLACK);
			vtext.setColor(Color.BLACK);
			btext.setColor(Color.BLACK);
			
			horizontal.add(htext);
			vertical.add(vtext);
			both.add(btext);
		}
	}
}
