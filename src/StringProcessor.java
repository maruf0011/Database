/**
 * Created by phenix on 2/13/15.
 */
public class StringProcessor {
    public static int getClassHour(String str) { return (str.charAt(8)-'0')*10 + (str.charAt(9)-'0');}

    public static int getIsLabOrTheory(String str)
    {
        return (str.charAt(10)-'0')*10 + (str.charAt(11)-'0');
    }

    public static int getTeacherId(String str)
    {
        return (str.charAt(4)-'0')*10 + (str.charAt(5)-'0');
    }

    public static int getBatchId(String str)
    {
        return (str.charAt(6)-'0')*10 + (str.charAt(7)-'0');
    }

    public static int getSubjectId(String str)
    {
        return (str.charAt(2)-'0')*10 + (str.charAt(3)-'0');
    }
}
