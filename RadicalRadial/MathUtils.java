import processing.core.PApplet;
import processing.core.PVector;

public class MathUtils {


  /**
   * Calculates angle between two points, i.e. between the sum vector and the x-axis.
   * 
   * @param p1
   *            The first point.
   * @param p2
   *            The second point.
   * @return The angle between both points.
   */
  public static float getAngleBetween(PVector p1, PVector p2) {
    // float angle = (float) Math.atan2(p2.y - p1.y, p2.x - p1.x);

    // Note: Raw values between point 1 and point 2 not valid, as they are are origin-based.
    PVector sub = PVector.sub(p2, p1);
    PVector xaxis = new PVector(1, 0);
    float angle = PVector.angleBetween(xaxis, sub);

    if (p2.y < p1.y) {
      angle = PApplet.TWO_PI - angle;
    }
    return angle;
  }

  /**
   * Calculates distance between two points, i.e. between the sum vector and the x-axis.
   * 
   * @param p1
   *            The first point.
   * @param p2
   *            The second point.
   * @return The distance between both points.
   */
  public static float getDistanceBetween(PVector p1, PVector p2) {
    // Note: Raw values between point 1 and point 2 not valid, as they are are origin-based.
    PVector sub = PVector.sub(p1, p2);
    PVector xaxis = new PVector(1, 0);
    float dist = PVector.dist(sub, xaxis);
    return dist;
  }

}