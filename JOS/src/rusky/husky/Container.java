package rusky.husky;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import rusky.husky.InputState.KeyboardState;
import rusky.husky.InputState.MouseState;
import rusky.husky.math.Vector2;
import rusky.husky.math.polygons.Rectangle;

public class Container<T extends Drawable> extends Drawable implements IAcceptsInput{

	private final List<T> children = new CopyOnWriteArrayList<T>();
	private BufferedImage buffer;

	private EnumSet<Axes> autoSizeAxes = EnumSet.noneOf(Axes.class);

	public boolean masked;
	public float cornerRadius;

	private MarginPadding padding = new MarginPadding();

	public EnumSet<Axes> getAutoSizeAxes() {
		return autoSizeAxes;
	}

	public void setAutoSizeAxes(EnumSet<Axes> autoSizeAxes) {
		if(autoSizeAxes.stream().anyMatch(getRelativeSizeAxes()::contains))
			throw new InvalidParameterException("An axes can't be relative sized and auto sized at the same time ("+toString()+")");
		else{
			this.autoSizeAxes = autoSizeAxes;
			invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
		}
	}

	@Override
	public void setRelativeSizeAxes(EnumSet<Axes> relativeSizeAxes) {
		if(relativeSizeAxes.stream().anyMatch(autoSizeAxes::contains))
			throw new InvalidParameterException("An axes can't be relative sized and auto sized at the same time ("+toString()+")");
		else
			super.setRelativeSizeAxes(relativeSizeAxes);
	}

	@Override
	public void paint(Graphics2D graphics) {
		Graphics2D gr = masked ? buffer.createGraphics() : graphics;

		if(masked){	
			gr.setComposite(AlphaComposite.Clear); 
			gr.fill(new Rectangle(0, 0, buffer.getWidth(), buffer.getHeight()).getNative());
			gr.setComposite(AlphaComposite.SrcOver);
			gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}

		gr.translate(getChildOffset().getXInt(), getChildOffset().getYInt());
		getPresentChildren().forEach(drawable -> paintChild(drawable, gr));

		if(masked){
			gr.dispose();
			graphics.setPaint(new TexturePaint(buffer, new Rectangle2D.Float(0, 0, buffer.getWidth(), buffer.getHeight())));

			graphics.fill(getClip());
		}else{
			gr.translate(-getChildOffset().getXInt(), -getChildOffset().getYInt());
		}
	}

	public Vector2 getChildSize(){
		return getDrawSize().substract(padding.getSize());
	}

	public Vector2 getChildOffset(){
		return padding.getOffset();
	}

	public void add(T drawable){
		if(drawable.parent != null)
			throw new IllegalArgumentException("Drawable cannot have two parents");
		children.add(drawable);
		drawable.parent = this;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public void add(Collection<T> drawables){
		drawables.forEach(this::add);
	}

	public void remove(T drawable){
		children.remove(drawable);
		drawable.parent = null;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public void remove(Collection<T> drawables){
		drawables.forEach(this::remove);
	}

	public void clear(){
		remove(children);
	}

	public List<T> getChildren(){
		return children;
	}

	public void setChildren(List<T> children){
		remove(this.children);
		add(children);
	}

	private Stream<T> getMouseQueue(MouseState state){
		if(masked && !hovering)
			return Stream.empty();
		return children.stream().filter(child -> child.contains(state.position)).sorted();
	}

	private Stream<T> getKeyboardQueue(KeyboardState state){
		return children.stream().sorted();
	}

	private Stream<T> getPresentChildren(){
		return children.stream().filter(child -> child.isPresent()).sorted();
	}

	public strictfp void paintChild(T drawable, Graphics2D graphics){
		AlphaComposite before = (AlphaComposite) graphics.getComposite();
		graphics.setComposite(before.derive(before.getAlpha() * drawable.getAlpha()));
		graphics.setPaint(drawable.getColor());
		graphics.scale(drawable.scale.getX(),drawable.scale.getY());
		graphics.translate(drawable.getDrawPosition().getX(),drawable.getDrawPosition().getY());
		drawable.paint(graphics);
		graphics.translate(-drawable.getDrawPosition().getX(),-drawable.getDrawPosition().getY());
		graphics.scale(1 / drawable.scale.getX(),1 / drawable.scale.getY());
		graphics.setComposite(before);
	}

	@Override
	void invalidate(EnumSet<Invalidation> invalidation, Drawable source) {
		super.invalidate(invalidation, source);
		children.stream().filter(drawable -> !drawable.equals(source)).forEach(child -> child.invalidate(invalidation,source));
	}
	
	@Override
	protected void onInvalidation(EnumSet<Invalidation> invalidation) {
		if(invalidation.stream().anyMatch(EnumSet.of(Invalidation.POSITION, Invalidation.SIZE_IN_PARENT_SPACE)::contains)){
			if(getAutoSizeAxes().contains(Axes.X))
				super.setWidth((float)children.stream().filter(drawable -> !drawable.getBypassAutoSizeAxes().contains(Axes.X)).mapToDouble(drawable -> drawable.getLayoutSize().getX() + drawable.getDrawPosition().getX()).max().orElse(0));

			if(getAutoSizeAxes().contains(Axes.Y))
				super.setHeight((float)children.stream().filter(drawable -> !drawable.getBypassAutoSizeAxes().contains(Axes.Y)).mapToDouble(drawable -> drawable.getLayoutSize().getY() + drawable.getDrawPosition().getY()).max().orElse(0));
		}
		if(masked && invalidation.contains(Invalidation.SIZE_IN_PARENT_SPACE)){
			Rectangle bounds = getScreenBounds().getTranslated(getDrawPosition().multiply(-2));
			bounds = new Rectangle(bounds.getPosition(), bounds.getSize().componentMax(Vector2.ONE));
			if(masked && (buffer == null || buffer.getHeight() != (int)bounds.height || buffer.getWidth() != (int)bounds.width))
				buffer = new BufferedImage((int)bounds.width, (int)bounds.height, BufferedImage.TYPE_INT_ARGB);
		}
	}

	@Override
	protected boolean onMouseDown(InputState state) {
		if(state.mouse.pressedButtons.size() == 1)
			dragging = getMouseQueue(state.mouse).filter(child -> child.triggerMouseDragStart(state)).findFirst();
		return getMouseQueue(state.mouse).anyMatch(child -> child.triggerMouseDown(state));
	}

	@Override
	protected boolean onMouseUp(InputState state) {
		if(state.mouse.pressedButtons.isEmpty())
			dragging = Optional.empty();
		return getMouseQueue(state.mouse).anyMatch(child -> child.triggerMouseUp(state));
	}

	private List<T> hoveringChildren = new ArrayList<>();

	@Override
	protected void onMouseMove(InputState state) {
		List<T> hoveringChildren = getMouseQueue(state.mouse).collect(Collectors.toList());

		this.hoveringChildren.stream().filter(child -> !hoveringChildren.contains(child)).forEach(child -> child.triggerHoverLost(state));
		hoveringChildren.stream().filter(child -> !this.hoveringChildren.contains(child)).forEach(child -> child.triggerHover(state));

		this.hoveringChildren = hoveringChildren;

		getMouseQueue(state.mouse).forEach(child -> child.triggerMouseMove(state));
	}

	@Override
	protected boolean onClick(InputState state) {
		return getMouseQueue(state.mouse).anyMatch(child -> child.triggerClick(state));
	}

	@Override
	protected boolean onWheel(InputState state) {
		return getMouseQueue(state.mouse).anyMatch(child -> child.triggerWheel(state));
	}

	@Override
	protected void update(InputState state) {
		children.stream().sorted().forEach(child -> child.triggerUpdate(state));
	}

	@Override
	protected void onHoverLost(InputState state) {
		children.stream().filter(child -> child.hovering).sorted().forEach(child -> child.triggerHoverLost(state));
		children.stream().filter(child -> child.hovering).sorted().forEach(child -> child.hovering = false);
	}

	@Override
	public void setSize(Vector2 size) {
		if(getSize().equals(size)) return;
		if(autoSizeAxes.stream().anyMatch(EnumSet.allOf(Axes.class)::contains))
			throw new IllegalAccessError("You can't set the size of an auto-sized container ("+toString()+")");
		super.setSize(size);
	}

	@Override
	public void setWidth(float x) {
		if(getWidth() == x) return;
		if(autoSizeAxes.contains(Axes.X))
			throw new IllegalAccessError("You can't set the width of a container with auto-size set to the X axis ("+toString()+")");
		super.setWidth(x);
	}

	@Override
	public void setHeight(float y) {
		if(getHeight() == y) return;
		if(autoSizeAxes.contains(Axes.Y))
			throw new IllegalAccessError("You can't set the height of a container with auto-size set to the Y axis ("+toString()+")");
		super.setHeight(y);
	}

	private Shape getClip(){
		return masked ? createClip() : getScreenBounds().getTranslated(getDrawPosition().multiply(-2)).getNative();
	}

	protected Shape createClip(){
		return new RoundRectangle2D.Float(0, 0, getDrawSize().getXInt(), getDrawSize().getYInt(), cornerRadius, cornerRadius);
	}

	@Override
	protected boolean onKeyPress(InputState state) {
		return getKeyboardQueue(state.keyboard).anyMatch(child -> child.triggerKeyPress(state));
	}

	@Override
	protected boolean onKeyRelease(InputState state) {
		return getKeyboardQueue(state.keyboard).anyMatch(child -> child.triggerKeyRelease(state));
	}

	private Optional<T> dragging = Optional.empty();
	@Override
	protected void onMouseDrag(InputState state) {
		dragging.ifPresent(child -> child.triggerMouseDrag(state));
	}
	
	@Override
	protected boolean onMouseDragStart(InputState state) {
		return getMouseQueue(state.mouse).anyMatch(child -> child.triggerMouseDragStart(state));
	}

	public MarginPadding getPadding() {
		return padding;
	}

	public void setPadding(MarginPadding padding) {
		this.padding = padding;
		invalidate(EnumSet.of(Invalidation.POSITION, Invalidation.SIZE_IN_PARENT_SPACE));
	}
	
	private Optional<T> focus = Optional.<T>empty();
	
	@Override
	protected boolean onFocus(InputState state) {
		focus.ifPresent(child -> child.triggerFocusLost(state));
		return (focus = getMouseQueue(state.mouse).filter(child -> child.triggerFocus(state)).findFirst()).isPresent();
	}
	
	@Override
	protected void onFocusLost(InputState state) {
		focus.ifPresent(child -> child.triggerFocusLost(state));
	}

	@Override
	public void onKeyTyped(char character) {
		focus.filter(IAcceptsInput.class::isInstance).map(IAcceptsInput.class::cast).ifPresent(child -> child.onKeyTyped(character));
	}
}
