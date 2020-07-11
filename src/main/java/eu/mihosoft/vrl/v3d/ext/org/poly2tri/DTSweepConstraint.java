/**
 * DTSweepConstraint.java
 *
 * Copyright 2014-2014 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */

package eu.mihosoft.vrl.v3d.ext.org.poly2tri;
/* Poly2Tri
 * Copyright (c) 2009-2010, Poly2Tri Contributors
 * http://code.google.com/p/poly2tri/
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of Poly2Tri nor the names of its contributors may be
 *   used to endorse or promote products derived from this software without specific
 *   prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 *
 * @author Thomas ???, thahlen@gmail.com
 *
 */
class DTSweepConstraint extends TriangulationConstraint {
  public TriangulationPoint pointP;
  public TriangulationPoint pointQ;

  /**
   * Give two points in any order. Will always be ordered so that q.y > p.y and
   * q.x > p.x if same y value
   */
  public DTSweepConstraint(TriangulationPoint p1, TriangulationPoint p2)
  // throws DuplicatePointException
  {
    pointP = p1;
    pointQ = p2;
    if (p1.getY() > p2.getY()) {
      pointQ = p1;
      pointP = p2;
    } else if (p1.getY() == p2.getY()) {
      if (p1.getX() > p2.getX()) {
        pointQ = p1;
        pointP = p2;
      } else if (p1.getX() == p2.getX()) {
        System.out.println("Failed to create constraint " + p1 + "=" + p2);
        // throw new DuplicatePointException( p1 + "=" + p2 );
        // return;
      }
    }
    pointQ.addEdge(this);
  }

  // public TPoint intersect( TPoint a, TPoint b )
  // {
  // double pqx,pqy,bax,bay,t;
  //
  // pqx = p.getX()-q.getX();
  // pqy = p.getY()-q.getY();
  // t = pqy*(a.getX()-q.getX()) - pqx*(a.getY()-q.getY() );
  // t /= pqx*(b.getY()-a.getY()) - pqy*(b.getX()-a.getX());
  // bax = t*(b.getX()-a.getX()) + a.getX();
  // bay = t*(b.getY()-a.getY()) + a.getY();
  // return new TPoint( bax, bay );
  // }

  @Override
  public TriangulationPoint getP() {
    return pointP;
  }

  @Override
  public TriangulationPoint getQ() {
    return pointQ;
  }
}
