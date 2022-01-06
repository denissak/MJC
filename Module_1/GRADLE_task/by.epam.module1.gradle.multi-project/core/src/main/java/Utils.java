public class Utils {
    public static boolean isAllPositiveNumbers(String... str) {
        for (String st : str) {
            if (!StringUtils.isPositiveNumber(st)) {
                return false;
            }
        }
        return true;
    }
}
