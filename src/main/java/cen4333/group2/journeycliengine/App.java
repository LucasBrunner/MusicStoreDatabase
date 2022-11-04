package cen4333.group2.journeycliengine;

import java.util.HashMap;
import java.util.Map;

import cen4333.group2.errors.AppBaseException;

public class App {
  public enum AppState {
    CLOSE_APP,
  }

  private SetupBase setup;
  
  public App(SetupBase setup) {
    this.setup = setup;
  }

  public App addRecoveryJunction(AppBaseException exceptionType, Junction junction) {
    errorRecoveryJunctions.put(exceptionType.getClass().getName(), junction);
    return this;
  }

  public void run() {
    appLoop();
  }

  private Map<String, Junction> errorRecoveryJunctions = new HashMap<String, Junction>();

  private void appLoop() {
    Junction currentJunction = setup.getDefaultJunction();
    boolean runApp = true;

    while (runApp) {
      try {
        AppState newState = errorSafeLoop(currentJunction);

        switch (newState) {
          case CLOSE_APP:
            runApp = false;
            break;
        
          default:
            break;
        }
      } catch (Exception e) {
        currentJunction = catchAppLevelError(e);
      }
    }
  }

  private AppState errorSafeLoop(Junction entryJunction) throws AppBaseException {
    Junction currentJunction = entryJunction;
    while (true) {
      Path nextPath = currentJunction.selectPath();
      nextPath.setPreviousJunction(currentJunction);

      System.out.println();
      nextPath.run();

      if (nextPath.getClass() == EndAppPath.class) {
        return AppState.CLOSE_APP;
      }

      currentJunction = nextPath.getJunction(setup);
    }
  }

  private Junction catchAppLevelError(Exception e) {
    if (errorRecoveryJunctions.containsKey(e.getClass().getName())) {
      
      ((AppBaseException) e).getDisplayText();
      return errorRecoveryJunctions.get(e.getClass().getName());
    } else {
      System.out.println("An Error occured!\n");
      e.printStackTrace();
      System.out.println("\nReturning to the main junction.");
      return setup.getDefaultJunction();
    }
  }
}
