package rusky.husky;

import java.awt.Paint;

import rusky.husky.math.Vector2;

public interface IDrawable{
	Vector2 getPosition();
	void setPosition(Vector2 position);
	Vector2 getSize();
	void setSize(Vector2 size);
	Paint getColor();
	void setColor(Paint color);
	
	void transformTo(Transform<?, ? extends IDrawable> transform);
	Clock getClock();
}
