import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/*给定文本文件，文件名称为a.txt，文件内容为一个8行8列的字符矩阵，内容为1和0字符，
    请编程计算出该矩阵中水平方向或者垂直方向或者斜线方向连续1最多的个数。*/
public class MatrixCalculator
{
    private final int MATRIX_SIZE = 8;
    private final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];

    public MatrixCalculator(String filePath, Charset charset) throws FileNotFoundException, MatrixFormatException
    {
        String buffer;
        try (Scanner scanner = new Scanner(new FileInputStream(filePath), charset))
        {
            int row;
            for (row = 0; scanner.hasNextLine(); row++)
            {
                buffer = scanner.nextLine();
                if (buffer.length() != MATRIX_SIZE)
                {
                    throw new MatrixFormatException("No enough cols");
                }
                for (int col = 0; col < MATRIX_SIZE; col++)
                {
                    matrix[row][col] = buffer.charAt(col) == '0' ? 0 : 1;
                }
            }
            if (row != MATRIX_SIZE)
            {
                throw new MatrixFormatException("No enough rows");
            }
        }
    }

    public void showMatrix()
    {
        printMatrix(matrix);
        System.out.println();
    }

    private void printMatrix(int[][] matrix)
    {
        for (int[] row : matrix)
        {
            for (int num : row)
            {
                System.out.printf("%-3d", num);
            }
            System.out.println();
        }
    }

    // 这三个函数分别计算从特定位置出发的水平、垂直、斜向连续1的个数

    private int horizontalCalculate(int row, int col)
    {
        int num = 0;//连续1的个数
        // 如果他自己都不是1，那就直接返回0
        if (matrix[row][col] == 1)
        {
            int colCopy = col;
            // 先往左找
            while (colCopy >= 0 && matrix[row][colCopy] == 1)
            {
                num++;
                colCopy--;
            }

            colCopy = col + 1;
            // 再向右找
            while (colCopy <= MATRIX_SIZE - 1 && matrix[row][colCopy] == 1)
            {
                num++;
                colCopy++;
            }
        }
        return num;
    }

    private int verticalCalculate(int row, int col)
    {
        int num = 0;//连续1的个数
        // 如果他自己都不是1，那就直接返回0
        if (matrix[row][col] == 1)
        {
            int rowCopy = row;
            // 先向上找
            while (rowCopy >= 0 && matrix[rowCopy][col] == 1)
            {
                num++;
                rowCopy--;
            }

            rowCopy = row + 1;
            while (rowCopy <= MATRIX_SIZE - 1 && matrix[rowCopy][col] == 1)
            {
                num++;
                rowCopy++;
            }
        }
        return num;
    }

    // 从左上到右下
    private int mainSlantedCalculate(int row, int col)
    {
        int num = 0;//连续1的个数
        // 如果他自己都不是1，那就直接返回0
        if (matrix[row][col] == 1)
        {
            int rowCopy = row;
            int colCopy = col;
            // 先向左上找
            while (rowCopy >= 0 && colCopy >= 0 && matrix[rowCopy][colCopy] == 1)
            {
                num++;
                rowCopy--;
                colCopy--;
            }

            // 再向右下找
            rowCopy = row + 1;
            colCopy = col + 1;
            while (rowCopy <= MATRIX_SIZE - 1 && colCopy <= MATRIX_SIZE - 1 && matrix[rowCopy][colCopy] == 1)
            {
                num++;
                rowCopy++;
                colCopy++;
            }
        }
        return num;
    }

    // 从右上到左下
    private int viceSlantedCalculate(int row, int col)
    {
        int num = 0;//连续1的个数
        // 如果他自己都不是1，那就直接返回0
        if (matrix[row][col] == 1)
        {
            int rowCopy = row;
            int colCopy = col;
            // 先向右上找
            while (rowCopy >= 0 && colCopy <= MATRIX_SIZE - 1 && matrix[rowCopy][colCopy] == 1)
            {
                num++;
                rowCopy--;
                colCopy++;
            }

            // 再向左下找
            rowCopy = row + 1;
            colCopy = col - 1;
            while (rowCopy <= MATRIX_SIZE - 1 && colCopy >= 0 && matrix[rowCopy][colCopy] == 1)
            {
                num++;
                rowCopy++;
                colCopy--;
            }
        }
        return num;
    }

    private int max(int... arr)
    {
        int max = arr[0];
        for (int i : arr)
        {
            if (i > max)
            {
                max = i;
            }
        }
        return max;
    }

    // 新建一个矩阵，对所有位置进行以上三个函数的运算，取最大值。最后再取这个矩阵的最大值
    public int calculate()
    {
        int[][] maxMatrix = new int[8][8];
        for (int row = 0; row < MATRIX_SIZE; row++)
        {
            for (int col = 0; col < MATRIX_SIZE; col++)
            {
                maxMatrix[row][col] = max(horizontalCalculate(col, row), verticalCalculate(col, row), mainSlantedCalculate(row, col), viceSlantedCalculate(row, col));
            }
        }
        int maxVal = 0;
        for (int[] row : maxMatrix)
        {
            for (int i : row)
            {
                if (i > maxVal)
                {
                    maxVal = i;
                }
            }
        }
        printMatrix(maxMatrix);
        return maxVal;
    }

    public static void main(String[] args)
    {
        try
        {
            MatrixCalculator calculator = new MatrixCalculator("src/2nd/2/a.txt", StandardCharsets.UTF_8);
            calculator.showMatrix();
            System.out.println(calculator.calculate());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
