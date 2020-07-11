package com.perunlabs.jsolid.d2;

import static java.util.Arrays.asList;

import java.util.List;

import com.perunlabs.jsolid.util.Check;

public final class ConvexPolygon implements Polygon {
  private final List<Vector2> vertexes;

  public ConvexPolygon(Vector2... vertexes) {
    List<Vector2> vertexList = asList(Check.noNullElements(vertexes));
    if (!Geometry.isConvexCounterClockwisePolygon(vertexList)) {
      throw new IllegalArgumentException("vertexes should specify convex shape");
    }
    this.vertexes = vertexList;
  }

  public List<Vector2> vertexes() {
    return vertexes;
  }
}
