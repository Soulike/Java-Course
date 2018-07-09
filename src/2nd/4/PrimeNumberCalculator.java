import java.util.ArrayList;
import java.util.List;

/*编写程序求出1万以内的所有素数，然后再判断这些素数中哪些是由素数拼接而成的。
    例如素数23就符合条件，23本身是素数，其由素数2，和素数3拼接（连接）组成。
    素数29就不满足条件，2是素数，而9不是素数。素数307不满足条件，不能忽略0.
	7907这个素数符合条件，7是素数，907是素数。
	需要把符合条件的拼接素数全部输出，并统计个数。*/
public class PrimeNumberCalculator
{
    private List<Integer> primeNumberList;
    private final int LIMIT = 10000;

    public PrimeNumberCalculator()
    {
        primeNumberList = new ArrayList<>();
        getPrimeNumbers();
    }

    private void getPrimeNumbers()
    {
        boolean isPrimeNumber;
        for (int i = 2; i < LIMIT; i++)
        {
            isPrimeNumber = true;
            for (int j = 2; j <= Math.sqrt(i); j++)
            {
                if (i % j == 0)
                {
                    isPrimeNumber = false;
                    break;
                }
            }
            if (isPrimeNumber)
            {
                primeNumberList.add(i);
            }
        }
    }

    public void getValidPrimeNumbers()
    {
        List<Integer> validPrimeNumberList = new ArrayList<>();
        for (int num : primeNumberList)
        {
            if (isValidPrimeNumber(num))
            {
                validPrimeNumberList.add(num);
            }
        }
        System.out.printf("符合条件的素数有%d个\n", validPrimeNumberList.size());
        for (int num : validPrimeNumberList)
        {
            System.out.printf("%d ", num);
        }
    }

    private boolean isValidPrimeNumber(int number)
    {
        // 小于 10 的数不考虑
        if (number < 10)
        {
            return false;
        }
        else
        {
            StringBuilder numberStr = new StringBuilder(String.valueOf(number));
            StringBuilder numberFront = new StringBuilder();//数字的前几位
            while (true)
            {
                // 后字符串第一位不能是0，直接全部丢到前字符串
                do
                {
                    numberFront.append(numberStr.substring(0, 1));//拿出第一位数字放入前字符串
                    numberStr.delete(0, 1);//后字符串删除第一位
                } while (numberStr.charAt(0) == '0');

                // 如果前后字符串都是素数或前后字符串都是由素数组成的，则符合条件
                if (isConnectedByPrimeNumbers(Integer.valueOf(numberFront.toString())) && isConnectedByPrimeNumbers(Integer.valueOf(numberStr.toString())))
                {
                    return true;
                }
                // 只要有一个不是素数
                // 后字符串只剩下一位了，那这个数字不可能是素数组合而成
                // 如果不只剩下一位，重复以上过程
                else if (numberStr.length() == 1)
                {
                    return false;
                }
            }
        }
    }

    private boolean isPrimeNumber(int number)
    {
        return primeNumberList.contains(number);
    }

    // 判断数字是不是由素数拼接而成，如果这个数是素数就直接返回true
    private boolean isConnectedByPrimeNumbers(int number)
    {
        if (isPrimeNumber(number))
        {
            return true;
        }
        else
        {
            String numberStr = String.valueOf(number);
            return isPrimeNumber(Integer.parseInt(numberStr.substring(0, 1))) && isConnectedByPrimeNumbers(Integer.parseInt(numberStr.substring(1)));
        }
    }

    public static void main(String[] args)
    {
        PrimeNumberCalculator calculator = new PrimeNumberCalculator();
        calculator.getValidPrimeNumbers();
    }
}
