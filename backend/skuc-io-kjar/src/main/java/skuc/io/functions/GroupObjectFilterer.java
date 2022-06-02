package skuc.io.functions;

import org.kie.api.runtime.ObjectFilter;

import skuc.io.skuciocore.models.csm.Group;

public class GroupObjectFilterer implements ObjectFilter {

  @Override
  public boolean accept(Object object) {
    
    return object.getClass() == Group.class;
  }
  
    
}
