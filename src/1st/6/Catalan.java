public class Catalan
{

    public static int answers = 0;

    //请实现go函数
    public static void go(Deque from, Deque to, Stack s)
    {
        if (from.size() == 0 && s.empty())//如果都已经到了to队列中
        {
            for (int i = 0; i < to.size(); i++)
            {
                System.out.printf("%2d", to.get(i));
            }
            System.out.println();
            answers++;
        }
        else
        {
            // 从from拿一个元素放入栈
            if (from.size() > 0/* && s.size() < 4*/)
            {
                Deque fromCopy = from.clone();
                Deque toCopy = to.clone();
                Stack sCopy = s.clone();
                int temp = fromCopy.getFirst();
                fromCopy.removeFirst();
                sCopy.push(temp);
                go(fromCopy, toCopy, sCopy);
            }

            // 从栈拿一个元素到结果队列
            if (s.size() > 0)
            {
                Deque fromCopy = from.clone();
                Deque toCopy = to.clone();
                Stack sCopy = s.clone();
                int temp = sCopy.pop();
                toCopy.addLast(temp);
                go(fromCopy, toCopy, sCopy);
            }

        }
    }

    public static void main(String[] args)
    {
        Deque from = new Deque();
        Deque to = new Deque();
        Stack s = new Stack();

        for (int i = 1; i <= 7; i++)
        {
            from.addLast(i);
        }

        go(from, to, s);

        System.out.println(answers);


    }

}
