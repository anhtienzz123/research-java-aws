package researchjavaaws.sqs;

import java.util.List;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SqsException;

public class SqsService {

    private SqsClient sqsClient;

    private String QUEUE_NAME = "MyTestQueue";

    private String QUEUE_URL;

    public SqsService() {
        sqsClient = SqsClient.builder().credentialsProvider(ProfileCredentialsProvider.create())
                .build();

        GetQueueUrlRequest getQueueRequest =
                GetQueueUrlRequest.builder().queueName(QUEUE_NAME).build();
        QUEUE_URL = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
    }

    public void sendMessage(String message) {
        try {
            CreateQueueRequest request = CreateQueueRequest.builder().queueName(QUEUE_NAME).build();
            sqsClient.createQueue(request);

            GetQueueUrlRequest getQueueRequest =
                    GetQueueUrlRequest.builder().queueName(QUEUE_NAME).build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
            SendMessageRequest sendMsgRequest =
                    SendMessageRequest.builder().queueUrl(queueUrl).messageBody(message).build();

            sqsClient.sendMessage(sendMsgRequest);
        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            e.printStackTrace();
        }
    }

    public List<Message> receiveMessages() {

        System.out.println("\nReceive messages");
        try {
            ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                    .queueUrl(QUEUE_URL).maxNumberOfMessages(10).build();
            return sqsClient.receiveMessage(receiveMessageRequest).messages();

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            e.printStackTrace();
        }
        return null;
    }
}
