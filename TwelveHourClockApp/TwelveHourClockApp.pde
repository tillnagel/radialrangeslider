/*
 * Styled RadialRangeSlider example, and showing how to read values.
 * 
 * Drag start or end handle to adapt the range, or drag the whole segment to move.  
 * Press + or - to set the whole range (i.e. the circle segment).
 * Press SPACE to toggle snapping to tics.
 *
 * (c) 2016 Till Nagel, tillnagel.com (see license.txt)
 */

AbstractRadialRangeSlider raSlider;

public void setup() {
  size(500, 500);
  smooth();

  PFont font = createFont("Sans-Serif", 14);
  textFont(font);

  raSlider = new StyledRadialRangeSlider(this, 250, 250, 100, radians(10), radians(30));
  raSlider.snapToTics = false;
  //raSlider = new RadialRangeSlider(this, 250, 250, 100, radians(10), radians(30));
}

public void draw() {
  background(255);

  raSlider.draw();


  fill(0);
  text(getFullHour(raSlider.angle1) + ":00 - " + getFullHour(raSlider.angle2) + ":00", 213, 
    380);
  text(getHourAndMinutes(raSlider.angle1) + " - " + getHourAndMinutes(raSlider.angle2), 213, 
    400);
}

public int getFullHour(float angle) {
  float hour = ((12 / TWO_PI) * (angle + PI / 2));
  if (hour > 12)
    hour = hour - 12;
  int fullHour = floor(hour);
  return fullHour;
}

public String getHourAndMinutes(float angle) {
  float hour = ((12 / TWO_PI) * (angle + PI / 2));
  if (hour > 12)
    hour = hour - 12;
  int fullHour = floor(hour);
  float minutesPart = hour - fullHour;
  String fullMinutes = nf(round(minutesPart * 60), 2);
  return fullHour + ":" + fullMinutes;
}

public void mouseMoved() {
  raSlider.onMoved(mouseX, mouseY);
}

public void mouseDragged() {
  raSlider.onDragged(mouseX, mouseY, pmouseX, pmouseY);
}

public void mousePressed() {
  raSlider.onMoved(mouseX, mouseY);
}

public void keyPressed() {
  if (key == '+') {
    raSlider.moveArc(0.1f);
  }
  if (key == '-') {
    raSlider.moveArc(-0.1f);
  }

  if (key == ' ') {
    raSlider.snapToTics = !raSlider.snapToTics;
  }
}