package researchjavaaws.sqs;

public class SqsMain {

    public static void main(String[] args) {
        SqsService sqsService = new SqsService();
        
        //sqsService.sendMessage("hello word1");
        System.out.println(sqsService.receiveMessages());
    }
}
