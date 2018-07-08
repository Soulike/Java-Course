/*编写直线类（Line类），需要提供两点确定一条直线的函数功能。
	如果两点重合，可以返回异常或者返回null引用来解决这个问题。
	直线类的数据成员和函数成员请自行设计。*/
public class Line
{
    private Point point1, point2;

    public Line(Point point1, Point point2) throws SamePointException
    {
        if (point1.equals(point2))
        {
            throw new SamePointException();
        }
        else
        {
            this.point1 = point1;
            this.point2 = point2;
        }
    }

    public Point getPoint1()
    {
        return point1.clone();
    }

    public Point getPoint2()
    {
        return point2.clone();
    }

    public void setPoint1(Point point1) throws SamePointException
    {
        if (point1.equals(point2))
        {
            throw new SamePointException();
        }
        else
        {
            this.point1 = point1;
        }
    }

    public void setPoint2(Point point2) throws SamePointException
    {
        if (point2.equals(point1))
        {
            throw new SamePointException();
        }
        else
        {
            this.point2 = point2;
        }
    }

    public String getEquation()
    {
        final double x1 = point1.getX();
        final double x2 = point2.getX();
        final double y1 = point1.getY();
        final double y2 = point2.getY();
        if (Double.compare(x1, x2) != 0)
        {
            double k = (y1 - y2) / (x1 - x2);
            double b = y1 - k * x1;
            if (Double.compare(k, 1) == 0)
            {
                return String.format("y=x+%f", b);
            }
            else if (Double.compare(k, 0) == 0)
            {
                return String.format("y=%f", b);
            }
            else
            {
                return String.format("y=%fx+%f", k, b);
            }
        }
        else
        {
            return String.format("x=%f", x1);
        }
    }

    public static void main(String[] args)
    {
        try
        {
            Line line = new Line(new Point(32453, 343), new Point(31235, 483));
            System.out.println(line.getEquation());
        }
        catch (SamePointException e)
        {
            e.printStackTrace();
        }
    }
}
