import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*编写程序求出1万以内的所有素数，并将这些素数输出到一个文本文件中，每行文本只包含一个素数数据。
    该文本文件内容要求可以用记事本程序来查看。*/
public class PrimeNumberSaver
{
    private List<Integer> primeNumberList;
    private final int LIMIT;

    public PrimeNumberSaver(int limit)
    {
        LIMIT = limit;
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

    public void savePrimeNumbers(String filePath, Charset charset) throws IOException
    {
        final File file = new File(filePath);
        if (!file.exists()) // 如果文件不存在，创建文件
        {
            File dir = new File(file.getParent());
            dir.mkdirs();
            file.createNewFile();
        }
        try (PrintWriter writer = new PrintWriter(filePath, charset))
        {
            for (int num : primeNumberList)
            {
                writer.println(num);
            }
        }
    }

    public static void main(String[] args)
    {
        try
        {
            PrimeNumberSaver primeNumberSaver = new PrimeNumberSaver(10000);
            primeNumberSaver.savePrimeNumbers("src/2nd/3/primeNumbers.txt", StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
