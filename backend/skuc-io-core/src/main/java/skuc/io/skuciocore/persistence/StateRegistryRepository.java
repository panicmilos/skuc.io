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
        newStateRegistry.setObjectId(objectId);
        this.store(newStateRegistry);
        return newStateRegistry;
      }
      return stateRegistry;
    }
  }

  @Override
  public void update(StateRegistry stateRegistry) {
    try (var session = getSession()) {
      var existingStateRegistry = session.load(concreteClass, stateRegistry.getId());

      existingStateRegistry.setUpdatedAt(LocalDateTime.now());
      existingStateRegistry.setRegistry(stateRegistry.getRegistry());

      session.saveChanges();
    }
  }

}
