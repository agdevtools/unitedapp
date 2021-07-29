package com.football.unitedapp.util;

import com.football.unitedapp.dblogger.DBLoggingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
public class RabbitMQConsumerTests {

    @Mock
    private DBLoggingService mockDbLoggingService;

    @InjectMocks
    @Spy
    private RabbitMQConsumer spyRabbitMQConsumer;

    @BeforeEach
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @CaptureSystemOutput
    void test_givenMessage_thenlogsandpersists(CaptureSystemOutput.OutputCapture capture) {

        spyRabbitMQConsumer.receivedMessage("test");
        String consoleOutput = capture.toString();

        assertTrue(consoleOutput.contains("INFO"));
        assertTrue(consoleOutput.contains("$$$ -> Consumed footiestats Message ->"));
        verify(mockDbLoggingService,times(1)).logToDatabase(any(),any(),any(),any());
        verify(spyRabbitMQConsumer,times(1)).getEndPoint(any());
    }
}