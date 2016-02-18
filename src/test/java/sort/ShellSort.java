package sort;

/**
 * 希尔排序算法
 */
public class ShellSort {

    public static void sort(int[] number) {
        int gap = number.length / 2;
        while (gap > 0) {
            for (int k = 0; k < gap; k++) {
                for (int i = k + gap; i < number.length; i += gap) {
                    for (int j = i - gap; j >= k; j -= gap) {
                        if (number[j] > number[j + gap]) {
                            swap(number, j, j + gap);
                        } else {
                            break;
                        }
                    }
                }
            }

            gap /= 2;
        }
    }

    private static void swap(int[] number, int i, int j) {
        int t;
        t = number[i];
        number[i] = number[j];
        number[j] = t;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 4, 55, 67, 34, 465, 2343, 44, 656, 343, 7, 3, 434, 3, 3, 45, 34, 53, 2, 2,
                347, 6, 6, 78, 5678, 67, 8, 35, 345, 354, 3, 756, 7,};
        ShellSort.sort(numbers);
        for (int i = 0; i < numbers.length; i++)
            System.out.print(numbers[i] + ",");
    }
}