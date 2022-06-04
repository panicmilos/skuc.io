package skuc.io.skuciocore.persistence;

import java.time.LocalDateTime;
import org.springframework.stereotype.Repository;

import skuc.io.skuciocore.models.csm.StateRegistry;

@Repository
public class StateRegistryRepository extends CrudRepository<StateRegistry> {

  public StateRegistryRepository() {
    super(StateRegistry.class);
  }

  public StateRegistry getStateRegistryFor(String objectId) {
    try (var session = getSession()) {
      var stateRegistry = session
        .query(this.concreteClass)
        .whereEquals("objectId", objectId)
        .firstOrDefault();
      if (stateRegistry == null) {
        var newStateRegistry = new StateRegistry();
        this.store(newStateRegistry);
        return newStateRegistry;
      }
      return stateRegistry;
    }
  }

  @Override
  public void update(StateRegistry stateRegistry) {
    stateRegistry.setUpdatedAt(LocalDateTime.now());
    this.delete(stateRegistry.getId());
    this.store(stateRegistry);
  }

}
