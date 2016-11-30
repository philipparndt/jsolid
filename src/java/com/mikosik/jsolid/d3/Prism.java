package com.mikosik.jsolid.d3;

import static com.mikosik.jsolid.JSolid.v;

import java.util.ArrayList;
import java.util.List;

import com.mikosik.jsolid.d1.Range;
import com.mikosik.jsolid.d2.Geometry;
import com.mikosik.jsolid.d2.Polygon;
import com.mikosik.jsolid.d2.Vector2;

import eu.mihosoft.vrl.v3d.CSG;
import eu.mihosoft.vrl.v3d.Vector3d;

public final class Prism extends AbstractSolid {
  private final Polygon base;
  private final Range zRange;

  public Prism(Polygon base, Range zRange) {
    this.base = base;
    this.zRange = zRange;
  }

  public CSG toCsg() {
    if (!Geometry.isConvexCounterClockwisePolygon(base.vertexes())) {
      throw new IllegalStateException("base is not convex, counter clockwise polygon.");
    }
    double bottom = zRange.low;
    double top = zRange.high;
    List<eu.mihosoft.vrl.v3d.Polygon> polygons = new ArrayList<>();
    List<Vector2> vertexes = base.vertexes();
    Vector2 last = vertexes.get(vertexes.size() - 1);
    for (int i = 0; i < vertexes.size() - 2; i++) {
      polygons.add(poly(
          v(vertexes.get(i), bottom),
          v(last, bottom),
          v(vertexes.get(i + 1), bottom)));
      polygons.add(poly(
          v(vertexes.get(i + 1), top),
          v(last, top),
          v(vertexes.get(i), top)));
    }

    for (int i = 0; i < vertexes.size(); i++) {
      int next = (i + 1) % vertexes.size();
      polygons.add(poly(
          v(vertexes.get(i), bottom),
          v(vertexes.get(next), bottom),
          v(vertexes.get(i), top)));
      polygons.add(poly(
          v(vertexes.get(next), bottom),
          v(vertexes.get(next), top),
          v(vertexes.get(i), top)));
    }
    return CSG.fromPolygons(polygons);
  }

  private eu.mihosoft.vrl.v3d.Polygon poly(Vector3d p1, Vector3d p2, Vector3d p3) {
    return eu.mihosoft.vrl.v3d.Polygon.fromPoints(p1, p2, p3);
  }
}
