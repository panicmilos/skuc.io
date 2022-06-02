package skuc.io.skuciocore.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Component;

@Component
public class Bus {
  private HashMap<String, List<Function<Object, Void>>> eventCallbacks;

  public Bus() {
		eventCallbacks = new HashMap<>();
	}

  public void register(String eventName, Function<Object, Void> callback) {
		var callbacks = eventCallbacks.computeIfAbsent(eventName, key -> new ArrayList<Function<Object, Void>>());
		
		eventCallbacks.put(eventName, callbacks);
		
		callbacks.add(callback);
	}
	
	public void fire(String eventName, Object params) {
		var callbacks = eventCallbacks.get(eventName);

		if(callbacks == null) {
			return;
		}

		for(var callback : callbacks) {
			callback.apply(params);
		}
	}
}
