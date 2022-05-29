package skuc.io.skuciocore.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtils {

    private ArrayUtils() {
      
    }
  
    @SuppressWarnings("unchecked")
    public static <T> List<T> spread(Object... params) {
      var result = new ArrayList<T>();
      for (Object object : params) {
        if(object instanceof List || object instanceof ArrayList) {
          result.addAll((List<T>) object);
        } else {
          result.add((T) object);
        }
      }
      return result;
    }
  
  }
