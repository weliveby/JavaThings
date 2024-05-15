package reflection;

public class TrainPrint {
    //第二调用
    {
        System.out.printf("Empty block initial %s\n", this.getClass());
    }
    //第一调用
    static {
        System.out.printf("Static initial %s\n", TrainPrint.class);
    }
    //第三调用
    public TrainPrint() {
        System.out.printf("Initial %s\n", this.getClass());
    }

    public static void main(String[] args) {
        TrainPrint t = new TrainPrint();
    }

}
