package rusky.husky.testing.testCases;

import rusky.husky.testing.TestCase;
import rusky.husky.ui.Menu;
import rusky.husky.ui.MenuBar;
import rusky.husky.ui.MenuItem;

public class TestCaseMenuBar extends TestCase {

	@Override
	public String getDescription() {
		return "1 bar";
	}

	@Override
	public void internalReset() {
		MenuBar bar = new MenuBar();
		
		Menu menu1 = new Menu();
		menu1.setWidth(150);
		menu1.add(new MenuItem("save", null));
		menu1.add(new MenuItem("load", null));
		bar.addMenu("file", menu1);
		
		add(bar);
	}
}
