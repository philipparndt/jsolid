package com.perunlabs.jsolid.d3;

import static com.perunlabs.jsolid.JSolid.matrix;
import static com.perunlabs.jsolid.JSolid.max;
import static com.perunlabs.jsolid.JSolid.min;
import static com.perunlabs.jsolid.JSolid.range;

import com.perunlabs.jsolid.JSolid;
import com.perunlabs.jsolid.d1.Angle;
import com.perunlabs.jsolid.d1.Range;

public abstract class Axis<A extends Axis<A>> extends Vector3 {
  public static final XAxis X = new XAxis();
  public static final YAxis Y = new YAxis();
  public static final ZAxis Z = new ZAxis();

  private Axis(double x, double y, double z) {
    super(x, y, z);
  }

  public abstract double coordinate(Vector3 v);

  public abstract Vector3 v(double coordinate);

  public Range rangeOf(Solid solid) {
    return range(min().on(this).valueIn(solid), max().on(this).valueIn(solid));
  }

  public double sizeOf(Solid solid) {
    return rangeOf(solid).size();
  }

  public abstract Matrix4 rotateMatrix(Angle angle);

  public abstract Matrix4 mirrorMatrix();

  public abstract Matrix4 scaleMatrix(double factor);

  public static class XAxis extends Axis<XAxis> {
    public XAxis() {
      super(1, 0, 0);
    }

    public double coordinate(Vector3 v) {
      return v.x;
    }

    public Vector3 v(double coordinate) {
      return JSolid.v(coordinate, 0, 0);
    }

    public Matrix4 rotateMatrix(Angle angle) {
      double sin = Math.sin(angle.radians());
      double cos = Math.cos(angle.radians());
      return matrix(
          1, 0, 0, 0,
          0, cos, -sin, 0,
          0, sin, cos, 0,
          0, 0, 0, 1);
    }

    public Matrix4 mirrorMatrix() {
      return new Matrix4(
          -1, 0, 0, 0,
          0, 1, 0, 0,
          0, 0, 1, 0,
          0, 0, 0, 1);
    }

    public Matrix4 scaleMatrix(double factor) {
      return matrix(
          factor, 0, 0, 0,
          0, 1, 0, 0,
          0, 0, 1, 0,
          0, 0, 0, 1);
    }
  }

  public static class YAxis extends Axis<YAxis> {
    public YAxis() {
      super(0, 1, 0);
    }

    public double coordinate(Vector3 v) {
      return v.y;
    }

    public Vector3 v(double coordinate) {
      return JSolid.v(0, coordinate, 0);
    }

    public Matrix4 rotateMatrix(Angle angle) {
      double sin = Math.sin(angle.radians());
      double cos = Math.cos(angle.radians());
      return matrix(
          cos, 0, sin, 0,
          0, 1, 0, 0,
          -sin, 0, cos, 0,
          0, 0, 0, 1);
    }

    public Matrix4 mirrorMatrix() {
      return new Matrix4(
          1, 0, 0, 0,
          0, -1, 0, 0,
          0, 0, 1, 0,
          0, 0, 0, 1);
    }

    public Matrix4 scaleMatrix(double factor) {
      return matrix(
          1, 0, 0, 0,
          0, factor, 0, 0,
          0, 0, 1, 0,
          0, 0, 0, 1);
    }
  }

  public static class ZAxis extends Axis<ZAxis> {
    public ZAxis() {
      super(0, 0, 1);
    }

    public double coordinate(Vector3 v) {
      return v.z;
    }

    public Vector3 v(double coordinate) {
      return JSolid.v(0, 0, coordinate);
    }

    public Matrix4 rotateMatrix(Angle angle) {
      double sin = Math.sin(angle.radians());
      double cos = Math.cos(angle.radians());
      return matrix(
          cos, -sin, 0, 0,
          sin, cos, 0, 0,
          0, 0, 1, 0,
          0, 0, 0, 1);
    }

    public Matrix4 mirrorMatrix() {
      return new Matrix4(
          1, 0, 0, 0,
          0, 1, 0, 0,
          0, 0, -1, 0,
          0, 0, 0, 1);
    }

    public Matrix4 scaleMatrix(double factor) {
      return matrix(
          1, 0, 0, 0,
          0, 1, 0, 0,
          0, 0, factor, 0,
          0, 0, 0, 1);
    }

  }
}
