/*
 * RadialRangeSlider
 * 
 * (c) 2016 Till Nagel, tillnagel.com (see license.txt)
 */
import processing.core.PApplet;

public class RadialRangeSlider extends AbstractRadialRangeSlider {

  public RadialRangeSlider(PApplet p, float centerX, float centerY, float radius, float angle1, 
    float angle2) {
    super(p, centerX, centerY, radius, angle1, angle2);
  }

  protected void drawRadialBackground() {
    p.stroke(0);
    p.fill(250);
    p.ellipse(0, 0, radius * 2, radius * 2);
  }

  protected void drawHandle(boolean from, boolean highlight) {
    if (highlight) {
      float x = radius * 0.7f;
      p.stroke(255, 0, 0, 150);
      p.fill(255);
      p.triangle(x, 5, x + 5, 13, x + 10, 5);
      p.triangle(x, -5, x + 5, -13, x + 10, -5);
      p.stroke(255, 0, 0);
    } else {
      p.stroke(0);
    }
    p.line(0, 0, radius, 0);
  }

  protected void drawSelectedSegment(float fromAngle, float toAngle, boolean highlight) {
    if (highlight) {
      p.fill(255, 0, 0, 150);
    } else {
      p.fill(0, 255, 0, 100);
    }
    p.arc(0, 0, radius * 2, radius * 2, fromAngle, toAngle);
  }

  protected void drawTic() {
    p.stroke(0);
    p.line(radius - 2, 0, radius + 2, 0);
  }
}