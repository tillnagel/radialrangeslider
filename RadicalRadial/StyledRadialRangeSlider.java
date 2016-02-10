import processing.core.PApplet;

public class StyledRadialRangeSlider extends AbstractRadialRangeSlider {

  public StyledRadialRangeSlider(PApplet p, float centerX, float centerY, float radius, 
    float angle1, float angle2) {
    super(p, centerX, centerY, radius, angle1, angle2);
  }

  protected void drawRadialBackground() {
    p.stroke(140);
    p.fill(240);
    p.ellipse(0, 0, radius * 2, radius * 2);
  }

  protected void drawHandle(boolean from, boolean highlight) {
    p.fill(240);
    if (highlight) {
      p.stroke(255, 0, 0, 150);
    } else {
      p.stroke(140);
    }
    p.line(0, 0, radius, 0);

    float x = radius * 0.8f;
    p.rect(x, -4, 16, 8);
    p.line(x + 4, -1, x + 12, -1);
    p.line(x + 4, +1, x + 12, +1);
  }

  protected void drawSelectedSegment(float fromAngle, float toAngle, boolean highlight) {
    if (highlight) {
      p.fill(153, 102, 102, 150);
    } else {
      p.fill(107, 100);
    }
    p.arc(0, 0, radius * 2, radius * 2, fromAngle, toAngle);
  }

  protected void drawTic() {
    p.stroke(140);
    p.line(radius - 2, 0, radius + 2, 0);
  }
}