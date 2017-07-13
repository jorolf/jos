package rusky.husky.standalone;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import rusky.husky.Container;
import rusky.husky.Drawable;
import rusky.husky.InputState;
import rusky.husky.JavApp;
import rusky.husky.JavLoader;
import rusky.husky.clocks.GameClock;
import rusky.husky.math.Vector2;

public abstract class Window extends Container<Drawable> implements JavApp {

	private InputState state = new InputState();

	public Window() {
		masked = true;
		hovering = true;
		setClock(new GameClock());
	}

	@Override
	public synchronized final void paint(Graphics g) {
		g.setColor(Color.WHITE);
		paint((Graphics2D) g);
	}

	@Override
	public synchronized final void keyPress(int keycode) {
		state = state.createNew();
		switch (keycode) {
		case KeyEvent.VK_CONTROL:
			state.keyboard.controlPressed = true;
			break;
		case KeyEvent.VK_ALT:
			state.keyboard.altPressed = true;
			break;
		case KeyEvent.VK_SHIFT:
			state.keyboard.shiftPressed = true;
			break;
		case KeyEvent.VK_META:
		case KeyEvent.VK_WINDOWS:
			state.keyboard.superPressed = true;
			break;
		}
		state.keyboard.pressedKeys.add(keycode);
		state.keyboard.pressedKey = keycode;
		triggerKeyRelease(state);
	}

	@Override
	public synchronized final void keyRelease(int keycode) {
		state = state.createNew();
		switch (keycode) {
		case KeyEvent.VK_CONTROL:
			state.keyboard.controlPressed = false;
			break;
		case KeyEvent.VK_ALT:
			state.keyboard.altPressed = false;
			break;
		case KeyEvent.VK_SHIFT:
			state.keyboard.shiftPressed = false;
			break;
		case KeyEvent.VK_META:
		case KeyEvent.VK_WINDOWS:
			state.keyboard.superPressed = false;
			break;
		}
		state.keyboard.pressedKeys.removeIf(code -> code == keycode);
		state.keyboard.releasedKey = keycode;
		triggerKeyPress(state);
	}

	@Override
	public synchronized final void btnPress(int x, int y, int button) {
		state = state.createNew();
		state.mouse.pressedButtons.add(button);
		triggerMouseDown(state);
		triggerFocus(state);
	}

	@Override
	public synchronized final void btnRelease(int x, int y, int button) {
		state = state.createNew();
		state.mouse.pressedButtons.remove((Integer) button);
		if (new Vector2(x, y).equals(state.mouse.position))
			triggerClick(state);
		triggerMouseUp(state);
	}

	@Override
	public synchronized final void mouseMove(int x, int y) {
		state = state.createNew();
		state.mouse = state.mouse.createNew();
		state.mouse.position = new Vector2(x, y);
		triggerMouseMove(state);
	}

	@Override
	public synchronized final void mouseWheel(double rotation) {
		state = state.createNew();
		state.mouse.lastWheel = state.mouse.wheel;
		state.mouse.wheel += rotation;
		triggerWheel(state);
	}

	@Override
	public synchronized final void tick() {
		triggerUpdate(state);
	}

	private Rectangle frame;

	@Override
	public synchronized void setFrame(Rectangle frame) {
		this.frame = frame;
		setSize(new Vector2(frame.width, frame.height));
	}

	public Rectangle getFrame() {
		return frame;
	}

	@Override
	public synchronized boolean needsRepaint() {
		return true;
	}

	private JavLoader parent;

	@Override
	public synchronized void setParent(JavLoader parent) {
		this.parent = parent;
	}

	public JavLoader getParent() {
		return parent;
	}

	@Override
	public synchronized final void mouseDrag(int x, int y) {
		state = state.createNew();
		state.mouse = state.mouse.createNew();
		state.mouse.position = new Vector2(x, y);
		triggerMouseDrag(state);
	}
	
	@Override
	public synchronized final void keyTyped(char character) {
		onKeyTyped(character);
	}
}
