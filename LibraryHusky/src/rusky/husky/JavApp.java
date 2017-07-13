package rusky.husky;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

public interface JavApp {

	void tick();

	void init();

	void paint(Graphics g);

	String getName();

	default void btnPress(int x, int y, int button) {
	}

	default void btnRelease(int x, int y, int button) {
	}

	default Dimension getScreenSize() {
		return new Dimension(500, 500);
	}

	default boolean close() {
		return true;
	}

	default void keyPress(int keycode) {
	}

	default void keyRelease(int keycode) {
	}
	
	default void keyTyped(char character){
	}

	default boolean needsRepaint() {
		return true;
	}

	default void mouseMove(int x, int y) {
	}

	default void mouseDrag(int x, int y) {
	}

	default void mouseWheel(double rotation) {
	}

	void setFrame(Rectangle frame);

	default boolean resizeable() {
		return false;
	}

	void setParent(JavLoader parent);

	default void windowStateChanged(WindowState state) {
	}
	
	default WindowState getDefaultWindowState(){
		return WindowState.Normal;
	}
}
