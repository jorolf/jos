package rusky.husky.testing.testCases;

import java.util.EnumSet;
import java.util.stream.IntStream;

import rusky.husky.Axes;
import rusky.husky.testing.TestCase;
import rusky.husky.ui.Menu;
import rusky.husky.ui.MenuItem;

public class TestCaseMenu extends TestCase {

	@Override
	public String getDescription() {
		return "Sample menu";
	}
	
	@Override
	public void internalReset() {
		Menu menu = new Menu();
		menu.setAutoSizeAxes(EnumSet.of(Axes.Y));
		menu.setWidth(150);
		menu.masked = true;
		add(menu);
		
		IntStream.range(0, 11).mapToObj(i -> new MenuItem(Integer.toString(i), () -> menu.getChildren().get(i).setAlpha(0.5f))).forEach(menu::add);
	}

}
