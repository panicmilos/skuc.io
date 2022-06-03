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
    var stateRegistry = this.getSession()
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

  @Override
  public void update(StateRegistry stateRegistry) {
    stateRegistry.setUpdatedAt(LocalDateTime.now());
    this.update(stateRegistry);
  }

}
