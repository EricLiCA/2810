package ca.polymtl.inf2810.lab1;

public class Tuple<K,A,B> {
	private A first;
	private B second;
	private K key;
	
	public Tuple(K key, A first, B second) {
		this.key = key;
		this.first = first;
		this.second = second;
	}
	
	public K getKey() {
		return key;
	}
	
	public A getFirst() {
		return first;
	}
	
	public B getSecond() {
		return second;
	}
}
