package rusky.husky.containers;

import java.util.EnumSet;

import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Easing;
import rusky.husky.Direction;
import rusky.husky.Drawable;
import rusky.husky.Invalidation;
import rusky.husky.math.Vector2;

public class FlowContainer<T extends Drawable> extends Container<T>{

	private EnumSet<Direction> direction = EnumSet.of(Direction.Horizontal);

	public float flowDuration = 0;
	public Easing flowCurve = Easing.Linear;

	private float currentX, currentY, nextY;
	private Vector2 spacing = Vector2.ZERO.clone();
	
	@Override
	protected void onInvalidation(EnumSet<Invalidation> invalidation) {
		super.onInvalidation(invalidation);
		if(invalidation.contains(Invalidation.SIZE_IN_PARENT_SPACE)){
			Vector2 drawSize = getDrawSize();
			Vector2 maxSize = new Vector2(getAutoSizeAxes().contains(Axes.X) ? Float.MAX_VALUE : drawSize.getX(), getAutoSizeAxes().contains(Axes.Y) ? Float.MAX_VALUE : drawSize.getY());
			if(direction.containsAll(Direction.both())){
				currentX = 0;
				currentY = 0;
				nextY = 0;
				getChildren().stream().filter(child -> child.isPresent()).sorted().forEach(child ->{
					if(currentX + child.getLayoutSize().getX() + spacing.getX() > maxSize.getX()){
						currentX = 0;
						currentY += nextY + spacing.getY();
						nextY = 0;
					}
					child.moveTo(new Vector2(currentX, currentY), flowDuration, flowCurve);
					currentX += child.getLayoutSize().getX() + spacing.getX();
					nextY = Math.max(nextY, child.getLayoutSize().getY());
				});
			}else if(direction.contains(Direction.Horizontal)){
				currentX = 0;
				getChildren().stream().filter(child -> child.isPresent()).sorted().forEach(child ->{
					child.moveTo(new Vector2(currentX, 0), flowDuration, flowCurve);
					currentX += child.getLayoutSize().getX() + spacing.getX();
				});
			}else if(direction.contains(Direction.Vertical)){
				currentY = 0;
				getChildren().stream().filter(child -> child.isPresent()).sorted().forEach(child ->{
					child.moveTo(new Vector2(0, currentY), flowDuration, flowCurve);
					currentY += child.getLayoutSize().getY() + spacing.getY();
				});
			}
		}
	}

	public EnumSet<Direction> getDirection() {
		return direction;
	}

	public void setDirection(EnumSet<Direction> direction) {
		if(direction.isEmpty()) throw new IllegalArgumentException("direction cannot be empty");
		this.direction = direction;
	}

	public Vector2 getSpacing() {
		return spacing;
	}

	public void setSpacing(Vector2 spacing) {
		if(spacing.equals(this.spacing)) return;
		this.spacing = spacing;
		invalidate(EnumSet.of(Invalidation.POSITION));
	}
	
	
}
