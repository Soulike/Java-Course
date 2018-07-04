import java.util.ArrayList;

public class Base64
{

    private final static String INDEX_TABLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private final static int ENCODE_MASK = 0b11111100_00000000_00000000_00000000;
    private final static int DECODE_MASK = 0b11111111_00000000_00000000_00000000;

    public static String encode(byte[] binaryData)
    {
        final int BYTES_LEFT = binaryData.length % 3;//最后剩余几个字节
        int suffixNum = 0;// 最后需要补几个'='
        if (BYTES_LEFT == 1)
        {
            suffixNum = 2;
        }
        else if (BYTES_LEFT == 2)
        {
            suffixNum = 1;
        }
        int groupNum = BYTES_LEFT == 0 ? binaryData.length / 3 : binaryData.length / 3 + 1;
        StringBuilder encodedStr = new StringBuilder();
        int buffer = 0;
        int temp = 0;


        for (int i = 0; i < groupNum; i++)
        {
            for (int j = 0; j < 3; j++)
            {

                if (i * 3 + j >= binaryData.length)
                {
                    buffer += 0;
                }
                else
                {
                    buffer += binaryData[i * 3 + j];
                }
                buffer <<= 8;
            }
            for (int j = 0; j < 4; j++)
            {
                temp = buffer & ENCODE_MASK;
                buffer <<= 6;
                encodedStr.append(INDEX_TABLE.charAt(temp >>> 26));
            }
        }
        for (int i = 0; i < suffixNum; i++)
        {
            encodedStr.delete(encodedStr.length() - 1, encodedStr.length());
        }
        for (int i = 0; i < suffixNum; i++)
        {
            encodedStr.append('=');
        }
        return encodedStr.toString();
    }

    public static byte[] decode(String s)
    {
        ArrayList<Byte> arrayList = new ArrayList<>();
        final int GROUP_SIZE = s.length() / 4;
        char charBuffer;
        int decodeNum = 0;
        int buffer = 0;
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
                buffer += decodeNum;
                buffer <<= 6;
            }
            buffer <<= 2;
            for (int j = 0; j < 3; j++)
            {
                arrayList.add((byte) ((buffer & DECODE_MASK) >>> 24));
                buffer <<= 8;
                if (buffer == 0)
                {
                    break;
                }
            }
        }
        byte[] arr = new byte[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++)
        {
            arr[i] = arrayList.get(i);
        }
        return arr;
    }

    public static void main(String[] args)
    {
        //byte[] a = {1, 2, 3, -7, -9, 110};
        byte[] a = "nvwielouhnviwue".getBytes();

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