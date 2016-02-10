AbstractRadialRangeSlider raSlider;

void setup() {
  size(500, 500);
  smooth();

  PFont font = createFont("Sans-Serif", 14);
  textFont(font);

  raSlider = new StyledRadialRangeSlider(this, 250, 250, 100, radians(10), radians(30));
  raSlider.snapToTics = false;
}

void draw() {
  background(255);

  raSlider.draw();
}

void mouseMoved() {
  raSlider.onMoved(mouseX, mouseY);
}

void mouseDragged() {
  raSlider.onDragged(mouseX, mouseY, pmouseX, pmouseY);
}

void mousePressed() {
  raSlider.onMoved(mouseX, mouseY);
}

void keyPressed() {
  if (key == '+') {
    raSlider.moveArc(0.1f);
  }
  if (key == '-') {
    raSlider.moveArc(-0.1f);
  }
}