package cen4333.group2;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.Test;

import cen4333.group2.UserInput;
import cen4333.group2.UserInput.YesNo;

public class UserInputTest {

  private void resetPrintStream() {
    resetPrintStream(new OutputStream() {
      @Override
      public void write(int b) throws IOException {}      
    });
  }

  private void resetPrintStream(OutputStream outputStream) { System.setOut(new PrintStream(outputStream)); }
  
  @Test
  public void getInt() {
    resetPrintStream();
    UserInput.close();

    String testInput = "1";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getInt() == 1);

    UserInput.close();

    testInput = "-1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getInt() == -1);

    UserInput.close();

    testInput = "string\n1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getInt() == 1);

    UserInput.close();
  }
  
  @Test
  public void getDouble() {
    resetPrintStream();
    UserInput.close();

    String testInput = "1.0";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 1.0);

    UserInput.close();

    testInput = "1.1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 1.1);

    UserInput.close();

    testInput = "0";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 0);

    UserInput.close();

    testInput = "-1.1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == -1.1);

    UserInput.close();

    testInput = "1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 1);

    UserInput.close();

    testInput = "string\n1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 1);

    UserInput.close();

    testInput = "string\n1.1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getDouble() == 1.1);

    UserInput.close();
  }
  
  @Test
  public void getChar() {
    resetPrintStream();
    UserInput.close();

    String testInput = "1";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getChar() == '1');

    UserInput.close();

    testInput = "-1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getChar() == '-');

    UserInput.close();

    testInput = "string\n1";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getChar() == 's');

    UserInput.close();

    testInput = "String";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getChar() == 'S');

    UserInput.close();
  }

  @Test
  public void getYesNo() {
    resetPrintStream();
    UserInput.close();

    String testInput = "yes";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.YES);

    UserInput.close();

    testInput = "no";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.NO);

    UserInput.close();

    testInput = "y";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.YES);

    UserInput.close();

    testInput = "n";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.NO);

    UserInput.close();

    testInput = "YES";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.YES);

    UserInput.close();

    testInput = "what?\nyes";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getYesNo() == YesNo.YES);

    UserInput.close();
  }

  @Test
  public void getBoolDefault() {
    resetPrintStream();
    UserInput.close();

    String testInput = "true";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool() == true);

    UserInput.close();

    testInput = "false";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool() == false);

    UserInput.close();

    testInput = "TRUE";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool() == true);

    UserInput.close();

    testInput = "what?\ntrue";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool() == true);

    UserInput.close();
  }
  
  @Test
  public void getBoolCustom() {
    resetPrintStream();
    UserInput.close();

    String testInput = "dome";
    InputStream in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == true);

    UserInput.close();

    testInput = "done";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == false);

    UserInput.close();

    testInput = "dom";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == true);

    UserInput.close();

    testInput = "don";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == false);

    UserInput.close();

    testInput = "DOME";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == true);

    UserInput.close();

    testInput = "domer\ndone";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == true);

    UserInput.close();

    testInput = "do\ndome";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Dome", "Done") == true);

    UserInput.close();

    testInput = "do";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Do", "Done") == true);

    UserInput.close();

    testInput = "done";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Do", "Done") == false);

    UserInput.close();

    testInput = "do";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Done", "Do") == false);

    UserInput.close();

    testInput = "done";
    in = new ByteArrayInputStream(testInput.getBytes());
    System.setIn(in);
    assertTrue(UserInput.getBool("Done", "Do") == true);

    UserInput.close();
  }
}
