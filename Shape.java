public abstract class Shape {
    public static final ShapeType DEFAULT_SHAPETYPE = ShapeType.RECTANGLE;
    public static final int DEFAULT_PANEL_WIDTH = 800;
    public static final int DEFAULT_PANEL_HEIGHT = 400;
    
    protected int x = 0;
    protected int y = 0;
    protected int width = 10;
    protected int height = 10;

    protected NestedShape parent;

    public static int numberOfShapes = 0;

    protected String label = "";

    public Shape() {
        label = "" + numberOfShapes++;
    }

    public Shape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        label = "" + numberOfShapes++;
    }

    public NestedShape getParent() {
        return this.parent;
    }

    public void setParent(NestedShape s) {
        this.parent = s;
    }

    public Shape[] getPath() {
        return getPathToRoot(this, 0);
    }

    public Shape[] getPathToRoot(Shape aShape, int depth) {
        Shape[] returnShapes;
        if (aShape == null) {
          if(depth == 0) return null;
          else returnShapes = new Shape[depth];
        }
        else {
          depth++;
          returnShapes = getPathToRoot(aShape.getParent(), depth);
          returnShapes[returnShapes.length - depth] = aShape;
        }
        return returnShapes;
      }

    public abstract int getArea();

    @Override
    public String toString() {
        return String.format("%s: %s", getClass().getName(), label);
    }
}

