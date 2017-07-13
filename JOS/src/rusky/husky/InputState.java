package rusky.husky;

import java.util.ArrayList;
import java.util.List;

import rusky.husky.math.Vector2;

public class InputState implements Cloneable {
	public MouseState mouse = new MouseState();
	public KeyboardState keyboard = new KeyboardState();
	public InputState last;

	@Override
	public InputState clone() {
		InputState state = new InputState();
		state.mouse = mouse.clone();
		state.keyboard = keyboard.clone();
		state.last = last;
		return state;
	}

	public InputState createNew() {
		InputState newState = this.clone();
		newState.last = this;
		return newState;
	}

	public class MouseState implements Cloneable {
		public Vector2 position = Vector2.ZERO;
		public Vector2 lastPosition = Vector2.ZERO;

		public Vector2 getDelta() {
			return position.substract(lastPosition);
		}

		public double wheel;
		public double lastWheel;

		public double getWheelDelta() {
			return wheel - lastWheel;
		}

		public List<Integer> pressedButtons = new ArrayList<>();

		@Override
		public MouseState clone() {
			MouseState state = new MouseState();
			state.wheel = wheel;
			state.lastWheel = lastWheel;
			state.position = position.clone();
			state.lastPosition = lastPosition.clone();
			state.pressedButtons = new ArrayList<>(pressedButtons);
			return state;
		}

		public MouseState createNew() {
			MouseState newState = this.clone();
			newState.lastPosition = position;
			return newState;
		}
	}

	public class KeyboardState implements Cloneable {
		public boolean altPressed;
		public boolean controlPressed;
		public boolean shiftPressed;
		/**
		 * windows key on windows, command key on mac
		 */
		public boolean superPressed;

		public List<Integer> pressedKeys = new ArrayList<>();
		public int releasedKey, pressedKey;

		@Override
		public KeyboardState clone() {
			KeyboardState state = new KeyboardState();
			state.altPressed = altPressed;
			state.controlPressed = controlPressed;
			state.shiftPressed = shiftPressed;
			state.superPressed = superPressed;
			state.pressedKeys = new ArrayList<>(pressedKeys);
			state.pressedKey = pressedKey;
			state.releasedKey = releasedKey;
			return state;
		}
	}
}
