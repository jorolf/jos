package rusky.husky.ui;

import java.util.EnumSet;

import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.containers.FlowContainer;
import rusky.husky.math.Vector2;

public class MenuBar extends Container<Drawable>{
	
	private final FlowContainer<MenuItem> items;
	private final Container<Menu> menus;
	private Menu openMenu;

	public MenuBar() {
		setHeight(15);
		setRelativeSizeAxes(EnumSet.of(Axes.X));
		
		items = new FlowContainer<>();
		items.setRelativeSizeAxes(Axes.both());
		add(items);
		
		menus = new Container<>();
		add(menus);
	}

	
	public void addMenu(String name, Menu menu){
		MenuItem item = new MenuItem(name, null);
		item.action = () -> {
			menus.getChildren().forEach(_menu -> _menu.hide());
			if(openMenu != menu){
				menu.setPosition(item.getPosition().add(new Vector2(0, 15)));
				menu.show();
				openMenu = menu;
			}else{
				openMenu = null;
			}
		};
		item.setRelativeSizeAxes(Axes.none());
		item.setAutoSizeAxes(EnumSet.of(Axes.X));
		items.add(item);
		menu.hide();
		menus.add(menu);
	}
}
