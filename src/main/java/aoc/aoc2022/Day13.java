package aoc.aoc2022;

import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.stream.Collectors;


public class Day13 extends Puzzle {
    private static final Integer DAY = 13;
    private static final boolean IS_TEST = false;

    @NoArgsConstructor
    private static class Packet implements Comparable<Packet> {
        private List<Object> left;

        public Packet(String packetString) {
            left = generatePacket(packetString);
        }

        private List<Object> generatePacket(String packetString) {
            String justNumbers = packetString.replace(',', ' ').replace('[', ' ').replace(']', ' ');
            Deque<List<Object>> stack = new ArrayDeque<>();
            stack.push(new ArrayList<>());
            for (int i = 1, n = packetString.length() - 1; i < n; i++) {
                switch (packetString.charAt(i)) {
                    case ',':
                        break;
                    case ']':
                        stack.pop();
                        break;
                    case '[':
                        List<Object> newList = new ArrayList<>();
                        stack.peek().add(newList);
                        stack.push(newList);
                        break;
                    default:
                        int end = justNumbers.indexOf(' ', i + 1);
                        stack.peek().add(Integer.parseInt(justNumbers.substring(i, end)));
                        i = end - 1;
                }
            }
            return stack.pop();
        }


        @Override
        public int compareTo(Packet right) {
            return compare(left, right);
        }

        private static int compare(Object left, Object right) {
            // case 1
            if (left instanceof Integer && right instanceof Integer) {
                return ((Integer) left).compareTo((Integer) right);
            }

            Iterator<Object> ll = toList(left).iterator();
            Iterator<Object> rr = toList(right).iterator();
            while (true) {
                boolean hasLeft = ll.hasNext();
                boolean hasRight = rr.hasNext();
                if (!hasLeft && !hasRight) {
                    return 0;
                } else if (!hasLeft && hasRight) {
                    return -1;
                } else if (hasLeft && !hasRight) {
                    return 1;
                } else {
                    int cmp = compare(ll.next(), rr.next());
                    if (cmp != 0) {
                        return cmp;
                    }
                }
            }
        }

        private static List<Object> toList(Object x) {
            @SuppressWarnings("unchecked")
            List<Object> result = x instanceof List ? List.class.cast(x) : Collections.singletonList(x);
            return result;
        }

    }

    public static void main(String[] args) throws Exception {
        //part1();
        part2();
    }

    public static void part1() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str = file.readLine();
            List<List<Object>> pairs = new ArrayList<>();
            while (str != null) {
                Packet packetLeft = new Packet(str);
                str = file.readLine();
                Packet packetRight = new Packet(str);
                str = file.readLine();
                pairs.add(Arrays.asList(packetLeft.left, packetRight.left));
                if (StringUtils.isEmpty(str)) {
                    str = file.readLine();
                }
            }

            int sumOfIndicesOfInOrderPairs = 0;
            for (int i = 0, n = pairs.size(); i < n; i++) {
                if (-1 == compare(pairs.get(i).get(0), pairs.get(i).get(1))) {
                    sumOfIndicesOfInOrderPairs += (i + 1); // 1-based indexing
                }
            }

            int answer = sumOfIndicesOfInOrderPairs;

            if (answer != 5717) {
                throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void part2() throws Exception {
        try (RandomAccessFile file = new RandomAccessFile(SRC_MAIN_RESOURCES + String.format(FILE_NAME, DAY, IS_TEST ? "-test" : ""), "r")) {
            String str = file.readLine();
            List<List<Object>> pairs = new ArrayList<>();
            while (str != null) {
                Packet packetLeft = new Packet(str);
                str = file.readLine();
                Packet packetRight = new Packet(str);
                str = file.readLine();
                pairs.add(Arrays.asList(packetLeft.left, packetRight.left));
                if (StringUtils.isEmpty(str)) {
                    str = file.readLine();
                }
            }

            Packet two = new Packet("[[2]]");
            Packet six = new Packet("[[6]]");
            pairs.add(Arrays.asList(two.left, six.left));

            int sumOfIndicesOfInOrderPairs = 0;
            List<Object> allPairs = pairs.stream().flatMap(List::stream)
                    .sorted(Day13::compare)
                    .collect(Collectors.toCollection(ArrayList::new));
            int productOfIndicesOfTwoAndSixWhenOrdered =
                    (1 + allPairs.indexOf(two.left)) * (1 + allPairs.indexOf(six.left));
            int answer = productOfIndicesOfTwoAndSixWhenOrdered;

            if (answer != 5717) {
                //throw new Exception();
            }
            System.out.println("part1 answer " + answer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int compare(Object left, Object right) {
        // case 1
        if (left instanceof Integer && right instanceof Integer) {
            return ((Integer) left).compareTo((Integer) right);
        }

        Iterator<Object> ll = toList(left).iterator();
        Iterator<Object> rr = toList(right).iterator();
        while (true) {
            boolean hasLeft = ll.hasNext();
            boolean hasRight = rr.hasNext();
            if (!hasLeft && !hasRight) {
                return 0;
            } else if (!hasLeft && hasRight) {
                return -1;
            } else if (hasLeft && !hasRight) {
                return 1;
            } else {
                int cmp = compare(ll.next(), rr.next());
                if (cmp != 0) {
                    return cmp;
                }
            }
        }
    }

    private static List<Object> toList(Object x) {
        @SuppressWarnings("unchecked")
        List<Object> result = x instanceof List ? List.class.cast(x) : Collections.singletonList(x);
        return result;
    }

}
