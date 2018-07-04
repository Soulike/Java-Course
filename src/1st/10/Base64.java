public class Base64
{

    public static String encode(byte[] binaryData)
    {
        return "";
    }

    public static byte[] decode(String s)
    {
        return new byte[0];
    }

    public static void main(String[] args)
    {
        byte[] a = {1, 2, 3, -7, -9, 110};
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
