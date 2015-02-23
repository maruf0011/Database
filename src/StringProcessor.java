/**
 * Created by phenix on 2/13/15.
 */
public class StringProcessor {
    public static int getClassHour(String str) { return (str.charAt(8)-'0')*10 + (str.charAt(9)-'0');}

    public static int getIsLabOrTheory(String str)
    {
        return (str.charAt(10)-'0')*10 + (str.charAt(11)-'0');
    }

    public static int getTeacherCount(String str)
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

    public static int[] findAllTeacher(String str)
    {
        int totalTeacher = getTeacherCount(str);

        int [] teacherList = new int[totalTeacher];

        int startPositionIndex = 12;

        for(int teacherIterator = 0 ; teacherIterator<totalTeacher ; teacherIterator++)
        {
            teacherList[teacherIterator] = ( str.charAt(startPositionIndex + (teacherIterator*2)) -'0')*10 + (str.charAt(startPositionIndex + (teacherIterator*2)+1)-'0');
        }

        return teacherList;
    }
}
