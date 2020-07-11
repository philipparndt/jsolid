package com.perunlabs.jsolid;

import static com.perunlabs.jsolid.JSolid.cuboid;
import static com.perunlabs.jsolid.JSolid.x;
import static org.testory.Testory.thenThrown;
import static org.testory.Testory.when;

import org.junit.Test;

public class CuboidApiTest {
  @Test
  public void negative_corner_throws_exception() throws Exception {
    when(() -> cuboid(1, 2, 3).cornerR(x(), -1));
    thenThrown(IllegalArgumentException.class);
  }
}
