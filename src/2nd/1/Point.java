/*编写点类（Point类），属性成员有x,y，都是double数据类型。需要为Point类编写构造函数。*/
public class Point implements Cloneable
{
    private final double x;
    private final double y;

    public Point(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        else if (obj.getClass() != this.getClass())
        {
            return false;
        }
        else
        {
            Point point = (Point) obj;
            return (this.x == point.x && this.y == point.y);
        }
    }

    public Point clone()
    {
        return new Point(x, y);
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}
