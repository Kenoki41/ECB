public class Driver {
    public static void main(String[] args) {
        ecbSystem ecb = new ecbSystem(args);
        ecb.readContact();
        ecb.readInstruction();
    }

}
