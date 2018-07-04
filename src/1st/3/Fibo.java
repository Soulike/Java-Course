public class Fibo
{
    public static void main(String[] args)
    {
        Fibo f = new Fibo();
        System.out.println(f.fibo1(9)); // 这两种方法哪种效率更高？
        System.out.println(f.fibo2(9));
    }

    public int fibo1(int n)
    { // 使用方法（函数）递归来实现
        if (n == 0 || n == 1)
        {
            return n;
        }
        else
        {
            return fibo1(n - 1) + fibo1(n - 2);
        }
    }

    public int fibo2(int n)
    { // 使用循环来实现
        if (n == 0 || n == 1)
        {
            return n;
        }
        else
        {
            int f0 = 0;
            int f1 = 1;
            int temp = 0;
            for (int i = 2; i <= n; i++)
            {
                temp = f0 + f1;
                f0 = f1;
                f1 = temp;
            }
            return f1;
        }
    }
}
