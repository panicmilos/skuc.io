package skuc.io.skuciocore.services;

import java.util.Collection;
import java.util.UUID;

import skuc.io.skuciocore.exceptions.MissingEntityException;
import skuc.io.skuciocore.models.csm.BaseCsm;
import skuc.io.skuciocore.persistence.CrudRepository;

public class CrudService<T extends BaseCsm> {

  protected CrudRepository<T> repository;

  public CrudService(CrudRepository<T> repository) {
    this.repository = repository;
  }

  public Collection<T> get() {
    return repository.get();
  }

  public T get(UUID id) {
    return repository.get(id);
  }

  public T getOrThrow(UUID id) {
    var entity = repository.get(id);
    if (entity == null) {
      throw new MissingEntityException(String.format("Entity with id %s is not in the system.", id));
    }

    return entity;
  }

  public void create(T entity) {
    repository.store(entity);
  }

  public void update(T entity) {
    repository.save();
  }

  public void delete(UUID id) {
    getOrThrow(id);
    repository.delete(id);
  }

}
