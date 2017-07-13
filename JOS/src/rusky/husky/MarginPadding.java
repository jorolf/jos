package rusky.husky;

import rusky.husky.math.Vector2;

public class MarginPadding {
	public float top, left, bottom, right;
	
	public MarginPadding(){ }
	
	public MarginPadding(float all){
		top = all;
		left = all;
		bottom = all;
		right = all;
	}

	public MarginPadding(float top, float left, float bottom, float right) {
		this.top = top;
		this.left = left;
		this.bottom = bottom;
		this.right = right;
	}
	
	public MarginPadding setTop(float value){
		top = value;
		return this;
	}
	
	public MarginPadding setLeft(float value){
		left = value;
		return this;
	}
	
	public MarginPadding setBottom(float value){
		bottom = value;
		return this;
	}
	
	public MarginPadding setRight(float value){
		right = value;
		return this;
	}
	
	public Vector2 getSize(){
		return new Vector2(left + right, top + bottom);
	}
	public Vector2 getOffset(){
		return new Vector2(left, top);
	}

	@Override
	public String toString() {
		return "MarginPadding [top=" + top + ", left=" + left + ", bottom=" + bottom + ", right=" + right + "]";
	}
}
