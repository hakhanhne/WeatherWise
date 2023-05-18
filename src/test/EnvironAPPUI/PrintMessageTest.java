package EnvironAPPUI;

import main.EnviroAPPUI;
import org.junit.Test;

public class PrintMessageTest {

    @Test
    public void testPrintMessage(){
        String[] str = {"", "Hello World", "\tHelloWorld", "123", "@##$@!$@!"};
        for (int i = 0; i < str.length; i++){
            EnviroAPPUI.printMessage(str[i]);
        }
    }
}
