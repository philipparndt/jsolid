/**
 * Polygon.java
 *
 * Copyright 2014-2014 Michael Hoffer <info@michaelhoffer.de>. All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are
 * those of the authors and should not be interpreted as representing official
 * policies, either expressed or implied, of Michael Hoffer
 * <info@michaelhoffer.de>.
 */
package eu.mihosoft.vrl.v3d;

import static com.perunlabs.jsolid.JSolid.edge;
import static com.perunlabs.jsolid.util.Lists.immutable;
import static com.perunlabs.jsolid.util.Lists.reverse;
import static java.lang.String.join;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.perunlabs.jsolid.d3.Edge;
import com.perunlabs.jsolid.d3.Matrix4;
import com.perunlabs.jsolid.d3.Vector3;

/**
 * Represents a convex polygon.
 */
public final class Polygon {
  public final List<Vector3> vertices;
  /**
   * Plane defined by this polygon.
   *
   * <b>Note:</b> uses first three vertices to define the plane.
   */
  public final Plane plane;

  /**
   * Constructor. Creates a new polygon that consists of the specified vertices.
   *
   * <b>Note:</b> the vertices used to initialize a polygon must be coplanar and
   * form a convex loop.
   *
   * @param vertices
   *          polygon vertices
   */
  public Polygon(List<Vector3> vertices) {
    this.vertices = immutable(vertices);
    this.plane = Plane.createFromPoints(
        vertices.get(0),
        vertices.get(1),
        vertices.get(2));
  }

  private Polygon(List<Vector3> vertices, Plane plane) {
    this.vertices = vertices;
    this.plane = plane;
  }

  /**
   * Constructor. Creates a new polygon that consists of the specified vertices.
   *
   * <b>Note:</b> the vertices used to initialize a polygon must be coplanar and
   * form a convex loop.
   *
   * @param vertices
   *          polygon vertices
   *
   */
  public Polygon(Vector3... vertices) {
    this(Arrays.asList(vertices));
  }

  public List<Edge> edges() {
    return IntStream.range(0, vertices.size())
        .mapToObj(i -> edge(vertices.get(i), vertices.get((i + 1) % vertices.size())))
        .collect(toList());
  }

  @Override
  public Polygon clone() {
    return new Polygon(new ArrayList<>(vertices));
  }

  /**
   * Flips this polygon.
   *
   * @return this polygon
   */
  public Polygon flip() {
    return new Polygon(reverse(vertices), plane.flip());
  }

  /**
   * Translates this polygon.
   *
   * @param v
   *          the vector that defines the translation
   * @return this polygon
   */
  public Polygon translateNew(Vector3 v) {
    return new Polygon(vertices.stream()
        .map((vertex) -> vertex.add(v))
        .collect(Collectors.toList()));
  }

  /**
   * Applies the specified transformation to this polygon.
   *
   * <b>Note:</b> if the applied transformation performs a mirror operation the
   * vertex order of this polygon is reversed.
   *
   * @param matrix
   *          the transformation to apply
   *
   * @return this polygon
   */
  public Polygon transform(Matrix4 matrix) {
    List<Vector3> newvertices = vertices.stream()
        .map((vertex) -> matrix.mul(vertex))
        .collect(Collectors.toList());

    if (matrix.determinant() < 0) {
      newvertices = reverse(newvertices);
    }

    return new Polygon(newvertices);
  }

  /**
   * Returns a transformed copy of this polygon.
   *
   * <b>Note:</b> if the applied transformation performs a mirror operation the
   * vertex order of this polygon is reversed.
   *
   * <b>Note:</b> this polygon is not modified
   *
   * @param matrix
   *          the transformation to apply
   * @return a transformed copy of this polygon
   */
  public Polygon mul(Matrix4 matrix) {
    return transform(matrix);
  }

  /**
   * Creates a polygon from the specified points.
   *
   * @param points
   *          the points that define the polygon
   * @return a polygon defined by the specified point list
   */
  public static Polygon fromPoints(Vector3... points) {
    return fromPoints(Arrays.asList(points));
  }

  /**
   * Creates a polygon from the specified point list.
   *
   * @param points
   *          the points that define the polygon
   * @return a polygon defined by the specified point list
   */
  public static Polygon fromPoints(
      List<Vector3> points) {
    return new Polygon(new ArrayList<>(points));
  }

  public boolean equals(Object object) {
    return object instanceof Polygon && equals((Polygon) object);
  }

  private boolean equals(Polygon that) {
    return plane.equals(that.plane)
        && vertices.equals(that.vertices);
  }

  public int hashCode() {
    return 31 * vertices.hashCode() + plane.hashCode();
  }

  public String toString() {
    List<String> points = vertices.stream()
        .map(Vector3::toString)
        .collect(toList());
    return "side(" + join(", ", points) + ")";
  }
}
