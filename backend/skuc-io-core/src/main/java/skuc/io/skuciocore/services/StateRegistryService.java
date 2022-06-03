package skuc.io.skuciocore.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.StateRegistry;
import skuc.io.skuciocore.persistence.StateRegistryRepository;

@Service
public class StateRegistryService extends CrudService<StateRegistry> {

  private StateRegistryRepository _stateRegistryRepository;

  @Autowired
  public StateRegistryService(StateRegistryRepository repository) {
    super(repository);
    _stateRegistryRepository = repository;
  }

  public StateRegistry getStateRegistryFor(String objectId) {
    return this._stateRegistryRepository.getStateRegistryFor(objectId);
  }

  public StateRegistry update(StateRegistry stateRegistry) {
    this._stateRegistryRepository.update(stateRegistry);
    return stateRegistry;
  }
}
