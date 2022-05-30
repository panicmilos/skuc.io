package skuc.io.functions;

import java.util.List;
import skuc.io.skuciocore.models.csm.configuration.Context;

public class IsContextActive {
  
  public static boolean isContextActive(List<Context> activeContexts, String contextName) {
    for(Context context : activeContexts) {
      if (context.getName().equals(contextName)) {
        return true;
      }
    }
  
    return false;
  }  
}
