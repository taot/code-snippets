package security;

public class EnumCompareTest {

    public static void main(String[] args) {
        Permission p1 = Permission.WRITE;
        Permission p2 = Permission.WRITE;
        System.out.println(p1.compareTo(p2));
    }
}
