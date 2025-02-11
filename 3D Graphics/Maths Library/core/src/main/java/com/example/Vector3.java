package com.example;

public class Vector3 {
	double x, y, z;

	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	// vector addition
	public Vector3 add(Vector3 other) {
		return new Vector3(this.x + other.x, this.y + other.y, this.z + other.z);
	}

	// vector subtraction
	public Vector3 subtract(Vector3 other) {
		return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
	}

	// vector multiplication by scalar
	public Vector3 multiply(double scalar) {
		return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
	}

	// vector magnitude
	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	// vector dot product
	public double dot(Vector3 other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	// vector cross product
	public Vector3 cross(Vector3 other) {
		return new Vector3(
			this.y * other.z - this.z * other.y,
			this.z * other.x - this.x * other.z,
			this.x * other.y - this.y * other.x
		);
	}

	// vector distance
	public double distance(Vector3 other) {
		double dx = this.x - other.x;
		double dy = this.y - other.y;
		double dz = this.z - other.z;

		return Math.sqrt(dx * dx + dy * dy + dz * dz);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}