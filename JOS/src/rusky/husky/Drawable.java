package rusky.husky;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import rusky.husky.math.Polygon;
import rusky.husky.math.Vector2;
import rusky.husky.math.polygons.Rectangle;

public class Drawable implements Comparable<Drawable>, IDrawable {

	private Vector2 size = Vector2.ZERO.clone();
	private EnumSet<Axes> relativeSizeAxes = Axes.none();
	private EnumSet<Axes> bypassAutoSizeAxes = Axes.none();
	private MarginPadding margin = new MarginPadding();

	private Vector2 position = Vector2.ZERO.clone();
	private EnumSet<Axes> relativePositionAxes = Axes.none();
	public Vector2 scale = Vector2.ONE.clone();

	public Anchor anchor = Anchor.TopLeft;
	public Anchor origin = Anchor.TopLeft;

	private Paint color = Color.WHITE;
	private float alpha = 1;

	public Container<?> parent;
	public int depth;
	public boolean hovering;
	public boolean focus;
	public boolean alwaysPresent;
	private Clock clock;
	private EnumSet<Invalidation> invalidation = EnumSet.noneOf(Invalidation.class);

	public void paint(Graphics2D graphics) {
	}

	public Vector2 getDrawSize() {
		Vector2 size = this.size.clone();
		if (relativeSizeAxes.contains(Axes.X) && parent != null)
			size = size.multiply(new Vector2(parent.getChildSize().getX(), 1));
		if (relativeSizeAxes.contains(Axes.Y) && parent != null)
			size = size.multiply(new Vector2(1, parent.getChildSize().getY()));

		return size;
	}

	public Vector2 getLayoutSize() {
		return getDrawSize().add(margin.getSize());
	}
	
	public Vector2 getLayoutPosition(){
		Vector2 position = this.position.add(anchor.apply(parent != null ? parent.getDrawSize() : Vector2.ZERO)
				.substract(origin.apply(getDrawSize())));
		if (relativePositionAxes.contains(Axes.X) && parent != null)
			position = position.multiply(new Vector2(parent.getDrawSize().getX(), 1));
		if (relativePositionAxes.contains(Axes.Y) && parent != null)
			position = position.multiply(new Vector2(1, parent.getDrawSize().getY()));

		return position;
	}

	public Vector2 getDrawPosition() {
		return getLayoutPosition().add(margin.getOffset());
	}

	public EnumSet<Axes> getRelativeSizeAxes() {
		return relativeSizeAxes;
	}

	public void setRelativeSizeAxes(EnumSet<Axes> relativeSizeAxes) {
		if (relativeSizeAxes.contains(Axes.X) && size.getX() == 0)
			size.setX(1);
		if (relativeSizeAxes.contains(Axes.Y) && size.getY() == 0)
			size.setY(1);

		this.relativeSizeAxes = relativeSizeAxes;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public EnumSet<Axes> getBypassAutoSizeAxes() {
		EnumSet<Axes> set = EnumSet.copyOf(bypassAutoSizeAxes);
		set.addAll(relativeSizeAxes);
		return set;
	}

	public void setBypassAutoSizeAxes(EnumSet<Axes> bypassAutoSizeAxes) {
		this.bypassAutoSizeAxes = bypassAutoSizeAxes;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public boolean isPresent() {
		return alwaysPresent || alpha != 0;
	}

	protected Polygon<?> getBoundsInScreenSpace() {
		if (parent == null)
			return getLocalBounds();
		else
			return new Rectangle(parent.getBoundsInScreenSpace().getTranslated(getLayoutPosition()).getPoints().get(0), getDrawSize());
	}

	protected Rectangle getScreenBounds() {
		if (parent == null)
			return getLocalBounds();
		else
			return parent.getScreenBounds().getTranslated(getLayoutPosition());
	}

	public final void invalidate(EnumSet<Invalidation> invalidation) {
		if (parent != null)
			parent.invalidate(invalidation, this);
		invalidate(invalidation, this);
	}

	void invalidate(EnumSet<Invalidation> invalidation, Drawable source) {
		this.invalidation.addAll(invalidation);
	}
	
	protected void onInvalidation(EnumSet<Invalidation> invalidation){
	}

	@Override
	public Vector2 getSize() {
		return size.clone();
	}

	@Override
	public void setSize(Vector2 size) {
		if (size.equals(this.size))
			return;
		this.size = size;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	@Override
	public Vector2 getPosition() {
		return position.clone();
	}

	@Override
	public void setPosition(Vector2 position) {
		if (position.equals(this.position))
			return;
		this.position = position;
		invalidate(EnumSet.of(Invalidation.POSITION));
	}

	public float getX() {
		return position.getX();
	}

	public float getY() {
		return position.getY();
	}

	public void setX(float x) {
		if (position.getX() == x)
			return;
		position.setX(x);
		invalidate(EnumSet.of(Invalidation.POSITION));
	}

	public void setY(float y) {
		if (position.getY() == y)
			return;
		position.setY(y);
		invalidate(EnumSet.of(Invalidation.POSITION));
	}

	public float getWidth() {
		return size.getX();
	}

	public float getHeight() {
		return size.getY();
	}

	public void setWidth(float x) {
		if (size.getX() == x)
			return;
		size.setX(x);
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public void setHeight(float y) {
		if (size.getY() == y)
			return;
		size.setY(y);
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}

	public final boolean triggerMouseDown(InputState state) {
		return onMouseDown(createCloneInParentSpace(state));
	}

	protected boolean onMouseDown(InputState state) {
		return false;
	}

	public final boolean triggerMouseUp(InputState state) {
		return onMouseUp(createCloneInParentSpace(state));
	}

	protected boolean onMouseUp(InputState state) {
		return false;
	}

	public final void triggerMouseMove(InputState state) {
		onMouseMove(createCloneInParentSpace(state));
	}

	protected void onMouseMove(InputState state) {
	}

	public final boolean triggerClick(InputState state) {
		return onClick(createCloneInParentSpace(state));
	}

	protected boolean onClick(InputState state) {
		return false;
	}

	public final boolean triggerWheel(InputState state) {
		return onWheel(createCloneInParentSpace(state));
	}

	protected boolean onWheel(InputState state) {
		return false;
	}

	public final void triggerUpdate(InputState state) {
		if (clock != null)
			clock.tick(parent != null ? parent.getClock().getTime() : 0);
		updateTransforms();
		update(createCloneInParentSpace(state));
		onInvalidation(invalidation);
	}

	protected void update(InputState state) {
	}

	public final boolean triggerHover(InputState state) {
		hovering = true;
		return onHover(createCloneInParentSpace(state));
	}

	protected boolean onHover(InputState state) {
		return false;
	}

	public final void triggerHoverLost(InputState state) {
		hovering = false;
		onHoverLost(createCloneInParentSpace(state));
	}

	protected void onHoverLost(InputState state) {
	}

	public final boolean triggerKeyPress(InputState state) {
		return onKeyPress(createCloneInParentSpace(state));
	}

	protected boolean onKeyPress(InputState state) {
		return false;
	}

	public final boolean triggerKeyRelease(InputState state) {
		return onKeyRelease(createCloneInParentSpace(state));
	}

	protected boolean onKeyRelease(InputState state) {
		return false;
	}

	public final void triggerMouseDrag(InputState state) {
		onMouseDrag(createCloneInParentSpace(state));
	}

	protected void onMouseDrag(InputState state) {
	}

	public final boolean triggerFocus(InputState state) {
		focus = true;
		return onFocus(createCloneInParentSpace(state));
	}

	protected boolean onFocus(InputState state) {
		return false;
	}

	public final void triggerFocusLost(InputState state) {
		focus = false;
		onFocusLost(createCloneInParentSpace(state));
	}

	protected void onFocusLost(InputState state) {
	}

	public final boolean triggerMouseDragStart(InputState state) {
		return onMouseDragStart(createCloneInParentSpace(state));
	}

	protected boolean onMouseDragStart(InputState state) {
		return false;
	}

	InputState createCloneInParentSpace(InputState state) {
		InputState newState = state.clone();
		if (parent != null) {
			newState.mouse.position = newState.mouse.position.substract(parent.getLayoutPosition().add(parent.getChildOffset()));
			newState.mouse.lastPosition = newState.mouse.lastPosition.substract(parent.getLayoutPosition().add(parent.getChildOffset()));
		}
		return newState;
	}

	public boolean contains(Vector2 pos) {
		return getBounds().contains(pos);
	}

	public Polygon<?> getBounds() {
		return parent != null ? new Rectangle(getLayoutPosition().add(parent.getLayoutPosition()), getDrawSize()) : new Rectangle(getLayoutPosition(), getDrawSize());
	}

	public Rectangle getLocalBounds() {
		return new Rectangle(getLayoutPosition(), getDrawSize());
	}

	@Override
	public int compareTo(Drawable other) {
		return Integer.compare(depth, other.depth);
	}

	private List<Transform<?, ? extends IDrawable>> transforms = new ArrayList<>();

	public void transformTo(Transform<?, ? extends IDrawable> transform, double delay) {
		transform.startTime = getClock().getTime() + delay;
		if(transform.endTime > getClock().getTime())
			transforms.add(transform);
		else
			transform.isFinished(getClock().getTime());
	}
	
	public void transformTo(Transform<?, ? extends IDrawable> transform) {
		transformTo(transform, 0);
	}

	private void updateTransforms() {
		transforms.removeIf(transform -> transform.isFinished(getClock().getTime()));
		transforms.forEach(transform -> transform.update(getClock()));
	}
	
	public TransformSequence transform() {
		return new TransformSequence(this);
	}

	@Override
	public String toString() {
		Class<?> clazz = getClass();
		while (clazz.isAnonymousClass())
			return clazz.getName() + " extends " + clazz.getSuperclass().getName();
		return clazz.getName();
	}

	public Clock getClock() {
		return clock != null ? clock : parent.getClock();
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}

	@Override
	public Paint getColor() {
		return color;
	}

	@Override
	public void setColor(Paint color) {
		this.color = color;
	}

	public MarginPadding getMargin() {
		return margin;
	}

	public void setMargin(MarginPadding margin) {
		this.margin = margin;
	}

	public EnumSet<Axes> getRelativePositionAxes() {
		return relativePositionAxes;
	}

	public void setRelativePositionAxes(EnumSet<Axes> relativePositionAxes) {
		this.relativePositionAxes = relativePositionAxes;
		invalidate(EnumSet.of(Invalidation.POSITION));
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		if(this.alpha == alpha) return;
		this.alpha = alpha;
		invalidate(EnumSet.of(Invalidation.SIZE_IN_PARENT_SPACE));
	}
	
	public void show(){
		setAlpha(1);
	}
	
	public void hide(){
		setAlpha(0);
	}
}
