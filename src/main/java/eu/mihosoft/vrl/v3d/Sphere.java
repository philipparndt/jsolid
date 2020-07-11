/**
 * Sphere.java
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

import static com.perunlabs.jsolid.JSolid.v;
import static com.perunlabs.jsolid.JSolid.v0;

import java.util.ArrayList;
import java.util.List;

import com.perunlabs.jsolid.d3.Vector3;

/**
 * A solid sphere.
 *
 * Tthe tessellation along the longitude and latitude directions can be
 * controlled via the {@link #numSlices} and {@link #numStacks} parameters.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Sphere {

  private Vector3 center;
  private double radius;
  private int numSlices;
  private int numStacks;

  /**
   * Constructor. Creates a sphere with radius 1, 16 slices and 8 stacks and
   * center [0,0,0].
   *
   */
  public Sphere() {
    init();
  }

  /**
   * Constructor. Creates a sphere with the specified radius, 16 slices and 8
   * stacks and center [0,0,0].
   *
   * @param radius
   *          sphare radius
   */
  public Sphere(double radius) {
    init();
    this.radius = radius;
  }

  /**
   * Constructor. Creates a sphere with the specified radius, number of slices
   * and stacks.
   *
   * @param radius
   *          sphare radius
   * @param numSlices
   *          number of slices
   * @param numStacks
   *          number of stacks
   */
  public Sphere(double radius, int numSlices, int numStacks) {
    init();
    this.radius = radius;
    this.numSlices = numSlices;
    this.numStacks = numStacks;
  }

  /**
   * Constructor. Creates a sphere with the specified center, radius, number of
   * slices and stacks.
   *
   * @param center
   *          center of the sphere
   * @param radius
   *          sphere radius
   * @param numSlices
   *          number of slices
   * @param numStacks
   *          number of stacks
   */
  public Sphere(Vector3 center, double radius, int numSlices, int numStacks) {
    this.center = center;
    this.radius = radius;
    this.numSlices = numSlices;
    this.numStacks = numStacks;
  }

  private void init() {
    center = v0();
    radius = 1;
    numSlices = 16;
    numStacks = 8;
  }

  private Vector3 sphereVertex(Vector3 c, double r, double theta, double phi) {
    theta *= Math.PI * 2;
    phi *= Math.PI;
    Vector3 dir = v(
        Math.cos(theta) * Math.sin(phi),
        Math.cos(phi),
        Math.sin(theta) * Math.sin(phi));
    return c.add(dir.mul(r));
  }

  public List<Polygon> toPolygons() {
    List<Polygon> polygons = new ArrayList<>();

    for (int i = 0; i < numSlices; i++) {
      for (int j = 0; j < numStacks; j++) {
        final List<Vector3> vertices = new ArrayList<>();

        vertices.add(
            sphereVertex(center, radius, i / (double) numSlices,
                j / (double) numStacks));
        if (j > 0) {
          vertices.add(
              sphereVertex(center, radius, (i + 1) / (double) numSlices,
                  j / (double) numStacks));
        }
        if (j < numStacks - 1) {
          vertices.add(
              sphereVertex(center, radius, (i + 1) / (double) numSlices,
                  (j + 1) / (double) numStacks));
        }
        vertices.add(
            sphereVertex(center, radius, i / (double) numSlices,
                (j + 1) / (double) numStacks));
        polygons.add(new Polygon(vertices));
      }
    }
    return polygons;
  }

  /**
   * @return the center
   */
  public Vector3 getCenter() {
    return center;
  }

  /**
   * @param center
   *          the center to set
   */
  public void setCenter(Vector3 center) {
    this.center = center;
  }

  /**
   * @return the radius
   */
  public double getRadius() {
    return radius;
  }

  /**
   * @param radius
   *          the radius to set
   */
  public void setRadius(double radius) {
    this.radius = radius;
  }

  /**
   * @return the numSlices
   */
  public int getNumSlices() {
    return numSlices;
  }

  /**
   * @param numSlices
   *          the numSlices to set
   */
  public void setNumSlices(int numSlices) {
    this.numSlices = numSlices;
  }

  /**
   * @return the numStacks
   */
  public int getNumStacks() {
    return numStacks;
  }

  /**
   * @param numStacks
   *          the numStacks to set
   */
  public void setNumStacks(int numStacks) {
    this.numStacks = numStacks;
  }
}
