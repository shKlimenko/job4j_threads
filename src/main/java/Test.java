import java.util.Arrays;

public class Test {
    private static int A = 5;
    static final int[] data = {1, 2, 3, 4, 5, 6};

    public static void main(String[] args) {
        data[0] = 0;
        data[3] = 0;
        System.out.println(Arrays.toString(data));
    }
}
