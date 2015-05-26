package org.test.utils;

public class Clone<T> {
	private T clone;
	
	public Clone(T clone){
		this.clone = clone;
	}
	
	public T get(){
		return clone;
	}
}
