public class RectangleShape extends Shape {
    public RectangleShape() {
        super();
    }

    public RectangleShape(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getArea() {
        return this.height * this.width;
    }
}