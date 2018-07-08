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

    // 产生数字所有连续组合
    private List<Integer> splitNumber(int number)
    {
        List<Integer> list = new ArrayList<>();
        String numberStr = String.valueOf(number);
        for (int i = 0; i < numberStr.length(); i++)
        {
            for (int j = i + 1; j <= numberStr.length(); j++)
            {
                list.add(Integer.valueOf(numberStr.substring(i, j)));
            }
        }
        return list;
    }

    public void getValidPrimeNumbers()
    {
        List<Integer> numberSplitList;
        List<Integer> validPrimeNumberList = new ArrayList<>();
        boolean isValid;
        for (int num : primeNumberList)
        {
            numberSplitList = splitNumber(num);
            isValid = true;
            for (int i : numberSplitList)
            {
                if (!primeNumberList.contains(i))
                {
                    isValid = false;
                    break;
                }
            }
            if (isValid)
            {
                validPrimeNumberList.add(num);
            }
        }

        for (int num : validPrimeNumberList)
        {
            System.out.printf("%d ", num);
        }
        System.out.println();
        System.out.printf("符合要求素数总数为 %d", validPrimeNumberList.size());
    }

    public static void main(String[] args)
    {
        PrimeNumberCalculator calculator = new PrimeNumberCalculator();
        calculator.getValidPrimeNumbers();
    }
}
