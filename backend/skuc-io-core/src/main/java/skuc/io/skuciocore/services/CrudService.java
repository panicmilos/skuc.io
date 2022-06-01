package skuc.io.skuciocore.services;

import java.util.Collection;

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

  public T update(T entity) {
    repository.update(entity);

    return entity;
  }

  public T delete(String id) {
    var entity = getOrThrow(id);
    repository.delete(entity.getId());

    return entity;
  }

}
