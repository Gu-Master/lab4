import java.util.ArrayList;
import java.util.List;

public class GoldSequenceGenerator {
    public static void main(String[] args) {
        int[] x = {0, 0, 0, 0, 1}; // Порядковый номер 5 в двоичном формате
        int[] y = {1, 1, 0, 1, 0}; // x + 7 в двоичном формате

        int sequenceLength = 31; // Длина последовательности Голда
        int[] goldSequence = generateGoldSequence(x, y, sequenceLength);

        System.out.println("Исходная последовательность:");
        printSequence(goldSequence);

        System.out.println("\nТаблица автокорреляции:");
        calculateAndPrintAutocorrelation(goldSequence);

        System.out.println("\nНовая последовательность Голда:");
        int[] newGoldSequence = generateNewGoldSequence(sequenceLength);
        printSequence(newGoldSequence);

        System.out.println("\nЗначение взаимной корреляции:");
        calculateAndPrintCrossCorrelation(goldSequence, newGoldSequence);
    }

    // Генерация последовательности Голда
    public static int[] generateGoldSequence(int[] x, int[] y, int length) {
        int[] sequence = new int[length];
        for (int i = 0; i < length; i++) {
            int bitX = (x[4] + x[2]) % 2;
            int bitY = (y[4] + y[3] + y[2] + y[0]) % 2;
            sequence[i] = (x[4] + y[4]) % 2;
            shiftRegister(x, bitX);
            shiftRegister(y, bitY);
        }
        return sequence;
    }

    // Сдвиг регистра
    public static void shiftRegister(int[] register, int newBit) {
        System.arraycopy(register, 1, register, 0, register.length - 1);
        register[register.length - 1] = newBit;
    }

    // Вывод последовательности
    public static void printSequence(int[] sequence) {
        for (int bit : sequence) {
            System.out.print(bit + " ");
        }
        System.out.println();
    }

    // Вычисление и вывод автокорреляции
    public static void calculateAndPrintAutocorrelation(int[] sequence) {
        int length = sequence.length;
        List<Double> autocorrelations = new ArrayList<>();

        for (int shift = 0; shift < length; shift++) {
            int[] shiftedSequence = cyclicShift(sequence, shift);
            int matches = 0;
            for (int i = 0; i < length; i++) {
                if (sequence[i] == shiftedSequence[i]) matches++;
            }
            double autocorrelation = 2.0 * matches / length - 1.0;
            autocorrelations.add(autocorrelation);

            // Вывод сдвинутой последовательности и автокорреляции
            System.out.print("Сдвиг " + shift + ": ");
            for (int bit : shiftedSequence) {
                System.out.print(bit + " ");
            }
            System.out.printf("| %.2f\n", autocorrelation);
        }
    }

    // Циклический сдвиг последовательности
    public static int[] cyclicShift(int[] sequence, int shift) {
        int length = sequence.length;
        int[] shifted = new int[length];
        for (int i = 0; i < length; i++) {
            shifted[i] = sequence[(i + shift) % length];
        }
        return shifted;
    }

    // Генерация новой последовательности Голда с x+1 и y-5
    public static int[] generateNewGoldSequence(int length) {
        int[] x = {0, 0, 0, 1, 1}; // x = x + 1
        int[] y = {1, 0, 0, 0, 1}; // y = y - 5

        return generateGoldSequence(x, y, length);
    }

    // Вычисление и вывод взаимной корреляции
    public static void calculateAndPrintCrossCorrelation(int[] seq1, int[] seq2) {
        int length = seq1.length;
        double crossCorrelation = 0;

        for (int i = 0; i < length; i++) {
            if (seq1[i] == seq2[i]) crossCorrelation += 1.0;
            else crossCorrelation -= 1.0;
        }
        crossCorrelation /= length;

        System.out.printf("Взаимная корреляция: %.2f\n", crossCorrelation);
    }
}
