package rusky.husky;


import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import rusky.husky.JavApp;

public abstract class Game implements JavApp{

	protected int fps;
	protected int _fps;
	protected int tps;
	protected int _tps;
	public int ticksSinceStart = 0;
	
	private boolean repaint;
	private Rectangle frame;
	private JavLoader parent;

	protected boolean[] keys = new boolean[1000];

	public Game() {
		Timer perSecond = new Timer(1000, this::resetFpsTps);
		perSecond.start();
	}
	
	private void resetFpsTps(ActionEvent e){
		fps = _fps;
		tps = _tps;
		_fps = 0;
		_tps = 0;
	}

	@Override
	public final void tick(){
		repaint(true);
		gTick();
		_tps++;
		ticksSinceStart++;
	}

	@Override
	public final void paint(Graphics g){
		_fps++;
		gPaint((Graphics2D) g);
		repaint(false);
	}

	@Override
	public abstract void init();
	protected abstract void gPaint(Graphics2D g);
	protected abstract void gTick();
	@Override
	public abstract String getName();
	
	
	protected void gKeyPress(int keycode){ }
	
	protected void gKeyRelease(int keycode){ }

	@Override
	public final void keyPress(int keycode) {
		keys[keycode] = true;
		gKeyPress(keycode);
	}

	@Override
	public final void keyRelease(int keycode) {
		keys[keycode] = false;
		gKeyRelease(keycode);
	}

	@Override
	public final boolean needsRepaint() {
		return repaint;
	}
	
	public final void repaint(boolean repaint) {
		this.repaint = repaint;
	}

	public Rectangle getFrame() {
		return frame;
	}

	@Override
	public void setFrame(Rectangle frame) {
		this.frame = frame;
	}
	
	@Override
	public final void setParent(JavLoader parent) {
		this.parent = parent;
	}
	
	protected final JavLoader getParent(){
		return parent;
	}
}
