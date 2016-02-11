/*
 * AbstractRadialRangeSlider
 * 
 * (c) 2016 Till Nagel, tillnagel.com (see license.txt)
 */
import processing.core.PApplet;
import processing.core.PVector;

public abstract class AbstractRadialRangeSlider {

  public static final float TWO_PI = PApplet.TWO_PI;

  protected PApplet p;

  protected float centerX = 300;
  protected float centerY = 300;
  protected float radius = 200;

  // Edge 1
  protected float angle1 = 0.1f;
  protected boolean highlight1 = false;

  // Edge 1
  protected float angle2 = 0.3f;
  protected boolean highlight2 = false;

  protected boolean highlightArc = false;

  protected float angleDistToEdges = 0.1f;

  // Snapping behaviour. Used to draw tics, and to snap mouse to one of them.
  protected boolean snapToTics = true;
  protected int snapTicNumber = 12;
  protected float snapAngleStep = TWO_PI / snapTicNumber;

  public AbstractRadialRangeSlider(PApplet p, float centerX, float centerY, float radius, 
    float angle1, float angle2) {
    super();
    this.p = p;
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius = radius;
    this.angle1 = angle1;
    this.angle2 = angle2;
  }

  public void draw() {
    p.pushMatrix();
    p.translate(centerX, centerY);

    drawRadialBackground();

    // Angle1 switch (see ArcTest2App)
    float angleToUse1 = angle1;
    if (angleToUse1 >= angle2) {
      angleToUse1 = -(TWO_PI - angleToUse1);
    }

    // Arc
    drawSelectedSegment(angleToUse1, angle2, highlightArc);

    // Edge 1
    p.pushMatrix();
    p.rotate(angleToUse1);
    drawHandle(true, highlight1);
    p.popMatrix();

    // Edge 2
    p.pushMatrix();
    p.rotate(angle2);
    drawHandle(false, highlight2);
    p.popMatrix();

    drawTics();

    p.popMatrix();
  }

  protected abstract void drawRadialBackground();

  protected abstract void drawHandle(boolean from, boolean highlight);

  protected abstract void drawSelectedSegment(float fromAngle, float toAngle, boolean highlight);

  protected void drawTics() {
    for (float angle = 0; angle <= TWO_PI; angle += snapAngleStep) {
      p.pushMatrix();
      p.rotate(angle);
      drawTic();
      p.popMatrix();
    }
  }

  protected abstract void drawTic();

  public void onMoved(float x, float y) {
    // All interactions only if inside circle
    boolean insideCircle = PApplet.dist(centerX, centerY, x, y) < radius;

    // Angle between given pos and center
    PVector center = new PVector(centerX, centerY);
    PVector check = new PVector(x, y);
    float checkAngle = MathUtils.getAngleBetween(center, check);

    // Highlight if pos is close to edge 1
    highlight1 = insideCircle && Math.abs(angle1 - checkAngle) < angleDistToEdges;

    // Highlight if pos is close to edge 2
    highlight2 = insideCircle && Math.abs(angle2 - checkAngle) < angleDistToEdges;

    if (highlight1 && highlight2) {
      // If in proximity of both, only highlight the nearer one
      highlight1 = Math.abs(angle1 - checkAngle) < Math.abs(angle2 - checkAngle);
      highlight2 = !highlight1;
    }

    // If between both edges
    boolean inAngle1 = checkAngle > angle1 + angleDistToEdges;
    boolean inAngle2 = checkAngle < angle2 - angleDistToEdges;
    boolean inBetween = inAngle1 && inAngle2 && insideCircle;
    if (angle1 > angle2) {
      // Update condition if angle1 switch
      float angleToUse1 = -(TWO_PI - angle1);
      float mouseAngleToUse = -(TWO_PI - checkAngle);
      inAngle1 = Math.abs(mouseAngleToUse) < Math.abs(angleToUse1) - angleDistToEdges;
      inBetween = (inAngle1 || inAngle2) && insideCircle;
    }
    if (inBetween) {
      highlightArc = true;
    } else {
      highlightArc = false;
    }
  }

  protected float getSnappedAngle(float angle) {
    if (snapToTics) {
      return PApplet.round(angle / snapAngleStep) * snapAngleStep;
    } else {
      return angle;
    }
  }

  public void onDragged(float x, float y, float oldX, float oldY) {
    PVector center = new PVector(centerX, centerY);
    PVector check = new PVector(x, y);
    float checkAngle = MathUtils.getAngleBetween(center, check);
    checkAngle = getSnappedAngle(checkAngle);

    if (highlight1) {
      angle1 = checkAngle;
    }
    if (highlight2) {
      angle2 = checkAngle;
    }

    if (highlightArc) {
      // Update both angles to move whole arc

      PVector oldCheck = new PVector(oldX, oldY);
      float oldAngle = MathUtils.getAngleBetween(center, oldCheck);
      oldAngle = getSnappedAngle(oldAngle);

      float diffAngle = oldAngle - checkAngle;
      diffAngle = getRangedAngle(diffAngle);
      moveArc(diffAngle);
    }
  }

  public void moveArc(float diffAngle) {
    angle1 -= diffAngle;
    angle2 -= diffAngle;

    // NB: checkAngle only can be between 0 and TWO_PI, so it's fine,
    // but by subtracting a diffAngle the angles can be moved out of range
    angle1 = getRangedAngle(angle1);
    angle2 = getRangedAngle(angle2);
  }

  protected float getRangedAngle(float angle) {
    float rangedAngle = angle;
    if (rangedAngle > TWO_PI) {
      rangedAngle = TWO_PI - rangedAngle;
    } else {
      if (rangedAngle < 0) {
        rangedAngle = TWO_PI + rangedAngle;
      }
    }
    return rangedAngle;
  }
}