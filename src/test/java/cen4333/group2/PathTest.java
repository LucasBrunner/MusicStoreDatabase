package cen4333.group2;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cen4333.group2.journeycliengine.Junction;
import cen4333.group2.journeycliengine.Path;

public class PathTest {

  private Path getPathWithName(String name) {
    return new Path() {
      @Override
      public String name() {
        return name;
      }
      @Override
      public Junction junction() {
        return null;
      }
      @Override
      public void run() {}      
    };
  }
  
  @Test
  public void testPathName() {
    Path p = getPathWithName(null);
    assertEquals(p.getName(), "Unnamed Path");

    p = getPathWithName("");
    assertEquals(p.getName(), "Unnamed Path");

    p = getPathWithName("   ");
    assertEquals(p.getName(), "Unnamed Path");

    p = getPathWithName("Named Path");
    assertEquals(p.getName(), "Named Path");
  }
}
