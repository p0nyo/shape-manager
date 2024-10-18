import java.util.*;

public class NestedShape extends RectangleShape {
    private ArrayList<Shape> innerShapes = new ArrayList<>();

    public NestedShape(int x, int y, int width, int height) {
        super(x, y, width, height);
        createInnerShape(ShapeType.RECTANGLE, 0, 0, width/2, height/2);
    }

    public NestedShape(int width, int height) {
        super(0, 0, width, height);
    }

    public Shape createInnerShape(ShapeType st, int x, int y, int w, int h) {
        if ((this.x + x >= this.x && x+w+this.x <= this.x + this.width) && (this.y-y <= this.y && this.y-y-h >= this.y - this.height)){}
        else {
            x = 0;
            y = 0;
            w = this.width / 2;
            h = this.height / 2;
        }
        switch (st) {
            case RECTANGLE:
                Shape rectangle = new RectangleShape(x, y, w, h);
                rectangle.setParent(this);
                innerShapes.add(rectangle);
                return rectangle;
            case TRIANGLE:
                Shape triangle = new TriangleShape(x, y, w, h);
                triangle.setParent(this);
                innerShapes.add(triangle);
                return triangle;
            case NESTED:
                Shape nested = new NestedShape(x, y, w, h);
                nested.setParent(this);
                innerShapes.add(nested);
                return nested;
            default:
                throw new IllegalArgumentException("Unknown ShapeType");
        }
    }

    public void addInnerShape(Shape s) {
        innerShapes.add(s);
    }

    public void removeInnerShape(Shape s) {
        s.setParent(null);
        innerShapes.remove(s);
    }

    public void removeInnerShapeAt(int index) {
        Shape s = getInnerShapeAt(index);
        s.setParent(null);
        removeInnerShape(s);
    }

    public int indexOf(Shape s) {
        return innerShapes.indexOf(s);
    }

    public ArrayList<Shape> getAllInnerShapes() {
        return innerShapes;
    }

    public int getArea() {
        int totalArea = this.width * this.height;
        for (Shape shape : innerShapes) {
            totalArea += (shape.width * shape.height);
        }
        return totalArea;
    }

    public Shape getInnerShapeAt(int index) {
        return innerShapes.get(index);
    }

    public int getSize() {
        return innerShapes.size();
    }

    public void showDetails() {
        System.out.printf("(%d,%d),%dx%d\n", this.x, this.y, this.width, this.height);
        for (Shape shape : innerShapes) {
            System.out.printf("(%d,%d),%dx%d\n", shape.x, shape.y, shape.width, shape.height);
        }
    }

    @Override
    public String toString() {
        return String.format("%s: %s, (children: %d)", getClass().getName(), this.label, this.getSize());
    }

    public static void main(String[] args) {

        // NestedShape s1 = new NestedShape(10,20,50,100);
        // System.out.println(s1.getSize());
        // Shape inner = s1.getInnerShapeAt(0);
        // System.out.println(inner);
        // System.out.println(inner.getParent());
        // NestedShape s2 = new NestedShape(400, 500);
        // System.out.println(s2);
        // System.out.println(s2.getSize());
        // NestedShape s3 = new NestedShape (10,20,50,100);
        // System.out.println(s3);
        // System.out.println(s3.getSize());
        // System.out.printf("s3:(%d,%d),%dx%d\n", s3.x, s3.y, s3.width, s3.height);
        // Shape inner2 = s3.getInnerShapeAt(0);
        // System.out.println(inner2);
        // NestedShape s4 = new NestedShape(800, 600);
        // System.out.println(s4.getSize());
        // s3.createInnerShape(ShapeType.RECTANGLE, 1, 2, 20, 50);
        // System.out.println(s3.getSize());
        // Shape t1 = s3.getInnerShapeAt(1);
        // System.out.printf("t1:(%d,%d),%dx%d\n", t1.x, t1.y, t1.width, t1.height);
        // s3.createInnerShape(ShapeType.RECTANGLE, 11, 12, 20, 30);
        // System.out.println(s3.getSize());
        // Shape t2 = s3.getInnerShapeAt(2);
        // System.out.printf("t2:(%d,%d),%dx%d\n", t2.x, t2.y, t2.width, t2.height);
        // Shape t3 = s3.createInnerShape(ShapeType.RECTANGLE, 11, 12, 20, 50);
        // System.out.printf("t3:(%d,%d),%dx%d\n", t3.x, t3.y, t3.width, t3.height);
        // Shape t4 = s3.createInnerShape(ShapeType.RECTANGLE, 11, 12, 60, 100);
        // System.out.printf("t4:(%d,%d),%dx%d\n", t4.x, t4.y, t4.width, t4.height);
        // Shape t5 = s3.createInnerShape(ShapeType.RECTANGLE, 11, 12, 45, 95);
        // System.out.printf("t5:(%d,%d),%dx%d\n", t5.x, t5.y, t5.width, t5.height);
        // Shape t6 = s3.createInnerShape(ShapeType.RECTANGLE, 150, 20, 30, 100);
        // System.out.printf("t6:(%d,%d),%dx%d\n", t6.x, t6.y, t6.width, t6.height);
        // Shape t7 = s3.createInnerShape(ShapeType.RECTANGLE, 20, 420, 30, 100);
        // System.out.printf("t7:(%d,%d),%dx%d\n", t7.x, t7.y, t7.width, t7.height);
        	
        // NestedShape s1 = new NestedShape(400, 100);
        // System.out.println(s1.createInnerShape(ShapeType.RECTANGLE,10,20,40,10));
        // System.out.println(s1.createInnerShape(ShapeType.NESTED, 10, 20, 50, 80));
        // System.out.println(s1.createInnerShape(ShapeType.NESTED, 10, 20, 100, 200));
        // System.out.println(s1.createInnerShape(ShapeType.NESTED, 500, 80, 50, 80));
        // s1.createInnerShape(ShapeType.NESTED, 10, 20, 30, 50);
        // System.out.println(s1.getSize());
        // Shape inner1 = s1.getInnerShapeAt(0);
        // System.out.printf("(%d,%d),%dx%d\n", inner1.x, inner1.y, inner1.width, inner1.height);
        // Shape inner2 = s1.getInnerShapeAt(1);
        // System.out.printf("(%d,%d),%dx%d\n", inner2.x, inner2.y, inner2.width, inner2.height);
        // Shape inner3 = s1.getInnerShapeAt(2);
        // System.out.printf("(%d,%d),%dx%d\n", inner3.x, inner3.y, inner3.width, inner3.height);
        // Shape inner4 = s1.getInnerShapeAt(3);
        // System.out.printf("(%d,%d),%dx%d\n", inner4.x, inner4.y, inner4.width, inner4.height);

        	
        // NestedShape  n1 = new NestedShape(11,12,60,120);
        // n1.createInnerShape(ShapeType.RECTANGLE, 20, 40, 50, 50);
        // n1.createInnerShape(ShapeType.TRIANGLE, 10, 30, 40, 45);
        // n1.createInnerShape(ShapeType.NESTED, 10, 30, 80, 80);
        // for (int i=0; i<n1.getSize(); i++) {
        // Shape s = n1.getInnerShapeAt(i);
        // System.out.printf("(%d,%d),%dx%d\n", s.x, s.y, s.width, s.height);
        // }

        // NestedShape s1 = new NestedShape(10,20,50,100);
        // NestedShape s2 = new NestedShape(500, 600);
        // NestedShape s3 = new NestedShape (11,22,80,100);
        // s1.showDetails();s2.showDetails();s2.showDetails();
        // System.out.printf("%d, %d, %d", s1.getArea(), s2.getArea(), s3.getArea());
        // Shape r1 = new RectangleShape();
        // s1.addInnerShape(r1);
        // System.out.println(s1.indexOf(r1));
        // System.out.println((s1.getAllInnerShapes()));
        // s1.removeInnerShape(r1);
        // System.out.println(s1);
    }
}
