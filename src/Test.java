import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();

        int secondsToAdd = 60; // 要增加的秒数
        calendar.add(Calendar.SECOND, secondsToAdd);
        Date endDate = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);

        System.out.println("起始时间：" + formattedStartDate);
        System.out.println("结束时间：" + formattedEndDate);
    }
}
