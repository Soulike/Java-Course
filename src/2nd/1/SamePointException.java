public class SamePointException extends Exception
{
    public SamePointException()
    {
        super();
    }

    public SamePointException(String message)
    {
        super(message);
    }

    public SamePointException(String message, Throwable cause)
    {
        super(message, cause);
    }

    protected SamePointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public SamePointException(Throwable cause)
    {
        super(cause);
    }
}
