package skuc.io.skuciocore.persistence.events;

import java.util.Collection;

import net.ravendb.client.documents.session.IDocumentSession;
import skuc.io.skuciocore.models.events.BaseEvent;
import skuc.io.skuciocore.persistence.DocumentStoreHolder;

public class CrudEventsRepository<T extends BaseEvent> {

  protected Class<T> concreteClass;

  public CrudEventsRepository(Class<T> concreClass) {
    this.concreteClass = concreClass;
  }

  public Collection<T> get() {
    try (var session = getSession()) {
      return session.query(concreteClass).toList();
    }
  }

  public T get(String id) {
    try (var session = getSession()) {
      return session.load(concreteClass, id);
    }
  }

  protected IDocumentSession getSession() {
    return DocumentStoreHolder.getStore().openSession();
  }

  public void store(T entity) {
    try (var session = getSession()) {
      session.store(entity, entity.getId());
      session.saveChanges();
    }
  }

}
