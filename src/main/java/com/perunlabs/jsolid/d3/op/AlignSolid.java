package com.perunlabs.jsolid.d3.op;

import static java.util.Arrays.asList;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import com.perunlabs.jsolid.d3.Alignment;
import com.perunlabs.jsolid.d3.Solid;

import eu.mihosoft.vrl.v3d.Polygon;

public class AlignSolid extends AbstractSolid {
  private final Solid solid;
  private final Solid aligning;
  private final List<Alignment> alignments;

  public AlignSolid(Solid solid, Solid aligning, List<Alignment> alignments) {
    this.solid = requireNonNull(solid);
    this.aligning = requireNonNull(aligning);
    this.alignments = new ArrayList<>(alignments);
  }

  protected List<Polygon> calculateSides() {
    if (solid.sides().isEmpty()) {
      return asList();
    }
    Solid result = solid;
    for (Alignment alignment : alignments) {
      result = alignment.align(aligning, result);
    }
    return result.sides();
  }
}
