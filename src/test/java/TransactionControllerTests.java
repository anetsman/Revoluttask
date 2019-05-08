import com.alex.Application;
import com.alex.model.Transaction;
import com.alex.model.UserAccount;
import com.alex.utils.TestUtils;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;


public class TransactionControllerTests {

    private String url = "http://localhost:8080";

    private TestUtils testUtils = new TestUtils();
    
    private Gson gson = new Gson();
    UserAccount testAccount1;
    UserAccount testAccount2;
    
    @BeforeAll
    public static void setUp() {
        String [] args = {};
        Application.main(args);
    }

    @BeforeEach
    public void before() {
        testAccount1 = gson.fromJson(testUtils.requestToString(new HttpPost(url + "/user/add")), UserAccount.class);
        testAccount2 = gson.fromJson(testUtils.requestToString(new HttpPost(url + "/user/add")), UserAccount.class);
    }

    @AfterEach
    public void after() {
        testUtils.requestToString(new HttpDelete(String.format("%s/user/%s", url, testAccount1.getId())));
        testUtils.requestToString(new HttpDelete(String.format("%s/user/%s", url, testAccount2.getId())));
    }

    @Test
    public void transactionCompletedTest() {
        int deposit = 1000;
        int transactionAmount = 100;

        testUtils.requestToString(new HttpPut(String.format("%s/user/%s/deposit/%s", url, testAccount1.getId(), deposit)));

        String transactionJson = String.format("{'fromAccountId': %s, 'toAccountId': %s, 'amount': %s}",
                testAccount1.getId(),
                testAccount2.getId(),
                transactionAmount);
        StringEntity transactionEntity = new StringEntity(transactionJson, ContentType.APPLICATION_JSON);

        HttpPost post = new HttpPost(url + "/transaction");
        post.setEntity(transactionEntity);
        Transaction transaction = gson.fromJson(testUtils.requestToString(post), Transaction.class);

        testAccount1 = gson.fromJson(testUtils.requestToString(
                new HttpGet(String.format("%s/user/%s", url, testAccount1.getId()))), UserAccount.class);
        testAccount2 = gson.fromJson(testUtils.requestToString(
                new HttpGet(String.format("%s/user/%s", url, testAccount2.getId()))), UserAccount.class);

        Assert.assertEquals(deposit - transactionAmount, testAccount1.getBalance());
        Assert.assertEquals(transaction.getAmount(), testAccount2.getBalance());
    }

    @Test
    public void transactionErrorTest() {
        String transactionJson = String.format("{'fromAccountId': %s, 'toAccountId': %s, 'amount': %s}",
                testAccount1.getId(),
                testAccount2.getId(),
                "blabla");
        StringEntity transactionEntity = new StringEntity(transactionJson, ContentType.APPLICATION_JSON);

        HttpPost post = new HttpPost(url + "/transaction");
        post.setEntity(transactionEntity);
        HttpResponse response = testUtils.request(post);

        Assert.assertEquals(HTTP_BAD_REQUEST, response.getStatusLine().getStatusCode());
    }

}
