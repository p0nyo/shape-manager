public class TriangleShape extends Shape {
    public TriangleShape() {
        super();
    }

    public TriangleShape(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public int getArea() {
        return this.width * this.height/2;
    }
}