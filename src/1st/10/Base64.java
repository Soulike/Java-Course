import java.util.ArrayList;

public class Base64
{

    private final static String INDEX_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    // 编码时，每次只需要取出高6位
    private final static int ENCODE_MASK = 0b11111100_00000000_00000000_00000000;
    // 解码时，每次只需要取出高8位
    private final static int DECODE_MASK = 0b11111111_00000000_00000000_00000000;
    // 去除byte提升的时候填充的符号位用
    private final static int UNSIGNED_MASK = 0b00000000_00000000_00000000_11111111;

    public static String encode(byte[] binaryData)
    {
        final int BYTES_LEFT = binaryData.length % 3;//最后剩余几个字节不成组
        int suffixNum = 0;// 最后需要补几个'='
        if (BYTES_LEFT == 1)
        {
            suffixNum = 2;
        }
        else if (BYTES_LEFT == 2)
        {
            suffixNum = 1;
        }
        // 当最后有剩余不足3字节的字符时，增加一组以补全
        int groupNum = BYTES_LEFT == 0 ? binaryData.length / 3 : binaryData.length / 3 + 1;
        // 构建Base64串字符串
        StringBuilder encodedStr = new StringBuilder();
        // 存放一组当前处理的24位
        int buffer24Bits = 0;
        // 存放一个当前处理的6位
        int buffer6Bits = 0;

        // 逐个24字节处理，循环完成后有效的24字节将在int的高位
        for (int i = 0; i < groupNum; i++)
        {
            // 从字节数组中取出3个字节放入 buffer24Bits
            for (int j = 0; j < 3; j++)
            {
                // 只有数组中还有数据的时候去取。如果取完了没凑够一组，就直接移位（相当于补0）
                if (i * 3 + j < binaryData.length)
                {
                    //提升到 int 并删除所有符号位，仅保留最后八位
                    buffer24Bits += (int) binaryData[i * 3 + j] & UNSIGNED_MASK;
                }
                // 每次加完后移动到高位
                buffer24Bits <<= 8;
            }

            // 分4组取出24位
            for (int j = 0; j < 4; j++)
            {
                // 取出高位6位
                buffer6Bits = buffer24Bits & ENCODE_MASK;
                // 将高6位删除
                buffer24Bits <<= 6;
                // 把这6位移动到低位，查表找对应字符
                encodedStr.append(INDEX_TABLE.charAt(buffer6Bits >>> 26));
            }
        }
        // 后缀处理。如果最后有补全的0那么会出现A，根据规则应当补充=，所以删除末尾的A换成=
        encodedStr.delete(encodedStr.length() - suffixNum, encodedStr.length());
        for (int i = 0; i < suffixNum; i++)
        {
            encodedStr.append('=');
        }
        return encodedStr.toString();
    }

    public static byte[] decode(String s)
    {
        ArrayList<Byte> arrayList = new ArrayList<>();
        // 每个字符事实上代表6位。看有多少组24位
        final int GROUP_SIZE = s.length() / 4;
        // 放置每个字符
        char charBuffer;
        // 字符代表的数字
        int decodeNum = 0;
        int buffer24Bits = 0;
        // 成24位的取出并解码
        for (int i = 0; i < GROUP_SIZE; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                charBuffer = s.charAt(i * 4 + j);
                if (charBuffer == '=')
                {
                    decodeNum = 0;
                }
                else
                {
                    decodeNum = INDEX_TABLE.indexOf(charBuffer);
                }
                buffer24Bits += decodeNum;
                buffer24Bits <<= 6;
            }
            // 需要把24位移动到int的高位，因此需要补移2位
            buffer24Bits <<= 2;

            // 每个字节取出查看数据。如果出现了8位全是0，那么必然是补上去的，忽略
            for (int j = 0; j < 3 && buffer24Bits != 0; j++)
            {
                // 取出高8位数据，移动到低位，并截断成byte。
                arrayList.add((byte) ((buffer24Bits & DECODE_MASK) >>> 24));
                buffer24Bits <<= 8;

            }
        }
        // 把arrayList中的数据移动到byte数组中
        byte[] arr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            arr[i] = arrayList.get(i);
        }
        return arr;
    }

    public static void main(String[] args)
    {
        byte[] a = {1, 2, 3, -7, -9, 110};
        //byte[] a = "dawdawdfwefewf".getBytes();

        for (int i = 0; i < a.length; i++)
        {
            System.out.print(a[i] + " ");
        }

        String s = encode(a);
        System.out.println(s);
        byte[] b = decode(s);
        for (int i = 0; i < b.length; i++)
        {
            System.out.print(b[i] + " ");
        }
        System.out.println();
    }
}