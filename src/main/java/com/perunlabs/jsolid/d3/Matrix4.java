package com.perunlabs.jsolid.d3;

import static com.perunlabs.jsolid.JSolid.v;

import com.perunlabs.jsolid.util.Check;
import com.perunlabs.jsolid.util.Hash;

public class Matrix4 {
  private final double a11;
  private final double a12;
  private final double a13;
  private final double a14;

  private final double a21;
  private final double a22;
  private final double a23;
  private final double a24;

  private final double a31;
  private final double a32;
  private final double a33;
  private final double a34;

  private final double a41;
  private final double a42;
  private final double a43;
  private final double a44;

  public Matrix4(
      double a11, double a12, double a13, double a14,
      double a21, double a22, double a23, double a24,
      double a31, double a32, double a33, double a34,
      double a41, double a42, double a43, double a44) {
    this.a11 = Check.isFinite(a11);
    this.a12 = Check.isFinite(a12);
    this.a13 = Check.isFinite(a13);
    this.a14 = Check.isFinite(a14);
    this.a21 = Check.isFinite(a21);
    this.a22 = Check.isFinite(a22);
    this.a23 = Check.isFinite(a23);
    this.a24 = Check.isFinite(a24);
    this.a31 = Check.isFinite(a31);
    this.a32 = Check.isFinite(a32);
    this.a33 = Check.isFinite(a33);
    this.a34 = Check.isFinite(a34);
    this.a41 = Check.isFinite(a41);
    this.a42 = Check.isFinite(a42);
    this.a43 = Check.isFinite(a43);
    this.a44 = Check.isFinite(a44);
  }

  public static Matrix4 move(Vector3 shift) {
    return new Matrix4(
        1, 0, 0, shift.x,
        0, 1, 0, shift.y,
        0, 0, 1, shift.z,
        0, 0, 0, 1);
  }

  public double determinant() {
    return a11 * a22 * a33 * a44
        + a12 * a23 * a34 * a41
        + a13 * a24 * a31 * a42
        + a14 * a21 * a32 * a43
        - a11 * a24 * a33 * a42
        - a12 * a21 * a34 * a43
        - a13 * a22 * a31 * a44
        - a14 * a23 * a32 * a41;
  }

  public Vector3 mul(Vector3 v) {
    return v(
        a11 * v.x + a12 * v.y + a13 * v.z + a14,
        a21 * v.x + a22 * v.y + a23 * v.z + a24,
        a31 * v.x + a32 * v.y + a33 * v.z + a34);
  }

  public Matrix4 mul(Matrix4 matrix4) {
    return new Matrix4(
        a11 * matrix4.a11 + a12 * matrix4.a21 + a13 * matrix4.a31 + a14 * matrix4.a41,
        a11 * matrix4.a12 + a12 * matrix4.a22 + a13 * matrix4.a32 + a14 * matrix4.a42,
        a11 * matrix4.a13 + a12 * matrix4.a23 + a13 * matrix4.a33 + a14 * matrix4.a43,
        a11 * matrix4.a14 + a12 * matrix4.a24 + a13 * matrix4.a34 + a14 * matrix4.a44,

        a21 * matrix4.a11 + a22 * matrix4.a21 + a23 * matrix4.a31 + a24 * matrix4.a41,
        a21 * matrix4.a12 + a22 * matrix4.a22 + a23 * matrix4.a32 + a24 * matrix4.a42,
        a21 * matrix4.a13 + a22 * matrix4.a23 + a23 * matrix4.a33 + a24 * matrix4.a43,
        a21 * matrix4.a14 + a22 * matrix4.a24 + a23 * matrix4.a34 + a24 * matrix4.a44,

        a31 * matrix4.a11 + a32 * matrix4.a21 + a33 * matrix4.a31 + a34 * matrix4.a41,
        a31 * matrix4.a12 + a32 * matrix4.a22 + a33 * matrix4.a32 + a34 * matrix4.a42,
        a31 * matrix4.a13 + a32 * matrix4.a23 + a33 * matrix4.a33 + a34 * matrix4.a43,
        a31 * matrix4.a14 + a32 * matrix4.a24 + a33 * matrix4.a34 + a34 * matrix4.a44,

        a41 * matrix4.a11 + a42 * matrix4.a21 + a43 * matrix4.a31 + a44 * matrix4.a41,
        a41 * matrix4.a12 + a42 * matrix4.a22 + a43 * matrix4.a32 + a44 * matrix4.a42,
        a41 * matrix4.a13 + a42 * matrix4.a23 + a43 * matrix4.a33 + a44 * matrix4.a43,
        a41 * matrix4.a14 + a42 * matrix4.a24 + a43 * matrix4.a34 + a44 * matrix4.a44);
  }

  public boolean equals(Object object) {
    return (object instanceof Matrix4) && equals((Matrix4) object);
  }

  private boolean equals(Matrix4 that) {
    return a11 == that.a11
        && a12 == that.a12
        && a13 == that.a13
        && a14 == that.a14
        && a21 == that.a21
        && a22 == that.a22
        && a23 == that.a23
        && a24 == that.a24
        && a31 == that.a31
        && a32 == that.a32
        && a33 == that.a33
        && a34 == that.a34
        && a41 == that.a41
        && a42 == that.a42
        && a43 == that.a43
        && a44 == that.a44;
  }

  public int hashCode() {
    return Hash.hash(
        a11, a12, a13, a14,
        a21, a22, a23, a24,
        a31, a32, a33, a34,
        a41, a42, a43, a44);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(a11);
    sb.append(" ");
    sb.append(a12);
    sb.append(" ");
    sb.append(a13);
    sb.append(" ");
    sb.append(a14);
    sb.append("\n");

    sb.append(a21);
    sb.append(" ");
    sb.append(a22);
    sb.append(" ");
    sb.append(a23);
    sb.append(" ");
    sb.append(a24);
    sb.append("\n");

    sb.append(a31);
    sb.append(" ");
    sb.append(a32);
    sb.append(" ");
    sb.append(a33);
    sb.append(" ");
    sb.append(a34);
    sb.append("\n");

    sb.append(a41);
    sb.append(" ");
    sb.append(a42);
    sb.append(" ");
    sb.append(a43);
    sb.append(" ");
    sb.append(a44);

    return sb.toString();
  }
}
