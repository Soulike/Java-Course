// 13/17小数点后第100位的数字是几？
public class FractionalDigit
{

    public static void main(String[] args)
    {
        int d = 13;
        int q = 17;
        int a = 0;

        /*基本思路：
         * 1. 计算 d * 10 / q，得到一个整数 a
         * 2. 令 d = d * 10 - a * q，重复 1
         * */

        for (int i = 0; i < 100; i++)
        {
            a = d * 10 / q;
            d = d * 10 - a * q;
        }


        System.out.println(a);

    }

}
