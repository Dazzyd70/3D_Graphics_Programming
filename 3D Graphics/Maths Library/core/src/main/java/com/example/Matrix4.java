package com.example;

public class Matrix4 {
	double[][] values;

	public Matrix4(double[][] values ) {
		this.values = values;
	}

	// matrix transposition
	public Matrix4 transpose() {
		double[][] result = new double[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				result[i][j] = values[j][i];
			}
		}
		return new Matrix4(result);
	}

	// matrix multiplication by scalar
	public Matrix4 multiply(double scalar) {
		double[][] result = new double[4][4];
		for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = values[i][j] * scalar;
            }
        }
        return new Matrix4(result);
	}

	// matrix multiplication by vector
	public Vector3 multiply(Vector3 vec) {
        double x = values[0][0] * vec.x + values[0][1] * vec.y + values[0][2] * vec.z + values[0][3];
        double y = values[1][0] * vec.x + values[1][1] * vec.y + values[1][2] * vec.z + values[1][3];
        double z = values[2][0] * vec.x + values[2][1] * vec.y + values[2][2] * vec.z + values[2][3];
        return new Vector3(x, y, z);
    }

	// matrix multiplication by matrix
	public Matrix4 multiply(Matrix4 other) {
        double[][] result = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i][j] = 0;
                for (int k = 0; k < 4; k++) {
                    result[i][j] += this.values[i][k] * other.values[k][j];
                }
            }
        }
        return new Matrix4(result);
    }

    // orthagonal projection 
    public static Matrix4 orthogonalProjection(double left, double right, double bottom, double top, double near, double far) {
        double[][] values = {
            {2.0 / (right - left), 0, 0, -(right + left) / (right - left)},
            {0, 2.0 / (top - bottom), 0, -(top + bottom) / (top - bottom)},
            {0, 0, -2.0 / (far - near), -(far + near) / (far - near)},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }

    // perspective projection
    public static Matrix4 perspectiveProjection(double fov, double aspectRatio, double near, double far) {
        double tanHalfFov = Math.tan(Math.toRadians(fov / 2.0));
        double[][] values = {
            {1.0 / (aspectRatio * tanHalfFov), 0, 0, 0},
            {0, 1.0 / tanHalfFov, 0, 0},
            {0, 0, -(far + near) / (far - near), -2 * far * near / (far - near)},
            {0, 0, -1, 0}
        };
        return new Matrix4(values);
    }

	 // scaling matrix
	 public static Matrix4 scalingMatrix(double sx, double sy, double sz) {
        double[][] values = {
            {sx, 0, 0, 0},
            {0, sy, 0, 0},
            {0, 0, sz, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }

	// translation matrix
    public static Matrix4 translationMatrix(double tx, double ty, double tz) {
        double[][] values = {
            {1, 0, 0, tx},
            {0, 1, 0, ty},
            {0, 0, 1, tz},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }

    // rotation matrix around x
    public static Matrix4 rotationX(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double[][] values = {
            {1, 0, 0, 0},
            {0, cos, -sin, 0},
            {0, sin, cos, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }

    // rotation matrix around y
    public static Matrix4 rotationY(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double[][] values = {
            {cos, 0, sin, 0},
            {0, 1, 0, 0},
            {-sin, 0, cos, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }

    // rotation matrix around z
    public static Matrix4 rotationZ(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double[][] values = {
            {cos, -sin, 0, 0},
            {sin, cos, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
        };
        return new Matrix4(values);
    }


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
        for (double[] row : values) {
            sb.append("[");
            for (double val : row) {
                sb.append(String.format("%6.2f ", val));
            }
            sb.append("]\n");
        }
        return sb.toString();
	}
}