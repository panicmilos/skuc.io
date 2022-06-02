package skuc.io.skuciocore.services.events;

import java.util.Collection;

import skuc.io.skuciocore.exceptions.MissingEntityException;
import skuc.io.skuciocore.models.events.BaseEvent;
import skuc.io.skuciocore.persistence.events.CrudEventsRepository;

public class CrudEventsService<T extends BaseEvent> {
  
  protected CrudEventsRepository<T> repository;

  public CrudEventsService(CrudEventsRepository<T> repository) {
    this.repository = repository;
  }

  public Collection<T> get() {
    return repository.get();
  }

  public T get(String id) {
    return repository.get(id);
  }

  public T getOrThrow(String id) {
    var entity = repository.get(id);
    if (entity == null) {
      throw new MissingEntityException(String.format("Entity with id %s is not in the system.", id));
    }

    return entity;
  }

  public T create(T entity) {
    repository.store(entity);

    return entity;
  }

}
