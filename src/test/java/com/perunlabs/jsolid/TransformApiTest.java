package com.perunlabs.jsolid;

import static com.perunlabs.jsolid.JSolid.cuboid;
import static com.perunlabs.jsolid.JSolid.degrees;
import static com.perunlabs.jsolid.JSolid.x;
import static com.perunlabs.jsolid.JSolid.y;
import static org.testory.Testory.any;
import static org.testory.Testory.given;
import static org.testory.Testory.spy;
import static org.testory.Testory.thenCalledNever;
import static org.testory.Testory.when;

import org.junit.Test;

import com.perunlabs.jsolid.d3.Matrix4;
import com.perunlabs.jsolid.d3.Vector3;

public class TransformApiTest {
  private Matrix4 matrix;
  private Matrix4 matrix2;

  @Test
  public void matrix_transforms_are_multiplied_and_result_matrix_is_applied() throws Exception {
    given(matrix = spy(x().rotateMatrix(degrees(90))));
    given(matrix2 = spy(y().rotateMatrix(degrees(90))));
    when(cuboid(1, 2, 3).apply(matrix).apply(matrix).vertexes());
    thenCalledNever(matrix).mul(any(Vector3.class));
    thenCalledNever(matrix2).mul(any(Vector3.class));
  }
}
