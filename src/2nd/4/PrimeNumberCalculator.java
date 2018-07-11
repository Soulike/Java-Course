import java.util.*;

/*编写程序求出1万以内的所有素数，然后再判断这些素数中哪些是由素数拼接而成的。
    例如素数23就符合条件，23本身是素数，其由素数2，和素数3拼接（连接）组成。
    素数29就不满足条件，2是素数，而9不是素数。素数307不满足条件，不能忽略0.
	7907这个素数符合条件，7是素数，907是素数。
	需要把符合条件的拼接素数全部输出，并统计个数。*/
public class PrimeNumberCalculator
{
    private List<Integer> primeNumberList;
    private final static int LIMIT = 10000;

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

    public List<Integer> getValidPrimeNumbers()
    {
        List<Integer> validPrimeNumberList = new ArrayList<>();
        for (int num : primeNumberList)
        {
            if (isValidPrimeNumber(num))
            {
                validPrimeNumberList.add(num);
            }
        }
        return validPrimeNumberList;
    }

    public void printValidPrimeNumbers()
    {
        List<Integer> validPrimeNumberList = getValidPrimeNumbers();
        System.out.printf("符合条件的素数共有%d个\n", validPrimeNumberList.size());
        for (int i = 0; i < validPrimeNumberList.size(); i++)
        {
            if (i % 10 == 0)
            {
                System.out.println();
            }
            System.out.printf("%8d", validPrimeNumberList.get(i));
        }
    }

    private boolean isValidPrimeNumber(int number)
    {
        String numberStr = String.valueOf(number);
        // 将数字字符串所有的两两分割情况递归
        for (int i = 1; i < numberStr.length(); i++)
        {
            if (isValidPrimeNumberRecursive(numberStr.substring(0, i)) && isValidPrimeNumberRecursive(numberStr.substring(i)))
            {
                return true;
            }
        }
        return false;
    }

    private boolean isValidPrimeNumberRecursive(String numberPart)
    {
        // 如果第一位是0，那么直接认为不是素数
        if (numberPart.charAt(0) == '0')
        {
            return false;
        }
        // 如果这个数已经是素数，那就不用继续分割了
        else if (isPrimeNumber(Integer.parseInt(numberPart)))
        {
            return true;
        }
        // 不是素数且第一位不是0，继续两两分割递归。只要有一种分割成立就立即返回
        else
        {
            for (int i = 1; i < numberPart.length(); i++)
            {
                if (isValidPrimeNumberRecursive(numberPart.substring(0, i)) && isValidPrimeNumberRecursive(numberPart.substring(i)))
                {
                    return true;
                }
            }
            return false;
        }
    }

    private boolean isPrimeNumber(int number)
    {
        return primeNumberList.contains(number);
    }

    public static void main(String[] args)
    {
        PrimeNumberCalculator calculator = new PrimeNumberCalculator();
        calculator.printValidPrimeNumbers();
    }
}
