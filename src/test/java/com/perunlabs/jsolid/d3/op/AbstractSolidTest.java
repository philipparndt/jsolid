package com.perunlabs.jsolid.d3.op;

import static com.perunlabs.jsolid.JSolid.x;
import static com.perunlabs.jsolid.JSolid.y;
import static com.perunlabs.jsolid.JSolid.z;
import static com.perunlabs.jsolid.util.Lists.immutable;
import static java.util.Arrays.asList;
import static org.testory.Testory.given;
import static org.testory.Testory.thenReturned;
import static org.testory.Testory.when;

import java.util.List;

import org.junit.Test;

import eu.mihosoft.vrl.v3d.Polygon;

public class AbstractSolidTest {
  private List<Polygon> sides;
  private AbstractSolid solid;

  @Test
  public void sides_returns_value_from_calculate_sides() throws Exception {
    given(sides = polygonList());
    given(solid = abstractSolid(sides));
    when(solid.sides());
    thenReturned(sides);
  }

  @Test
  public void sides_returns_value_from_calculate_sides_on_second_call() throws Exception {
    given(sides = polygonList());
    given(solid = abstractSolid(sides));
    given(solid).sides();
    when(solid.sides());
    thenReturned(sides);
  }

  @Test
  public void calculate_sides_is_called_only_once() throws Exception {
    given(sides = polygonList());
    given(solid = new AbstractSolid() {
      int count = 0;

      public List<Polygon> calculateSides() {
        if (count == 0) {
          count++;
          return sides;
        } else {
          return null;
        }
      }
    });
    given(solid).sides();
    when(solid).sides();
    thenReturned(sides);
  }

  private static List<Polygon> polygonList() {
    return immutable(asList(new Polygon(x(), y(), z())));
  }

  private static AbstractSolid abstractSolid(List<Polygon> polygons) {
    return new AbstractSolid() {
      public List<Polygon> calculateSides() {
        return polygons;
      }
    };
  }
}
