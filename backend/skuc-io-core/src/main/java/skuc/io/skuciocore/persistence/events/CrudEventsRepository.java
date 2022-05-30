package skuc.io.skuciocore.persistence.events;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collection;

import net.ravendb.client.documents.session.IDocumentSession;
import skuc.io.skuciocore.models.events.BaseEvent;
import skuc.io.skuciocore.persistence.DocumentStoreHolder;

public class CrudEventsRepository<T extends BaseEvent> implements Closeable {

  private IDocumentSession session;
  protected Class<T> concreteClass;

  public CrudEventsRepository(Class<T> concreClass) {
    this.concreteClass = concreClass;
  }

  public Collection<T> get() {
    return getSession().query(concreteClass).toList();
  }

  public T get(String id) {
    return getSession().load(concreteClass, id);
  }

  public void save() {
    getSession().saveChanges();
  }

  protected IDocumentSession getSession() {
    if (session == null) {
      session = DocumentStoreHolder.getStore().openSession();
    }

    return session;
  }

  public void store(T entity) {
    getSession().store(entity, entity.getId());
    save();
  }

  @Override
  public void close() throws IOException {
    if (session != null) {
      session.close();
      session = null;
    }
  }
  
}
