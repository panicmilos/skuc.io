package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.context.activation.TimePeriodActivator;
import skuc.io.skuciocore.persistence.TimePeriodActivatorRepository;

@Service
public class TimePeriodActivatorService extends CrudService<TimePeriodActivator> {

  private ContextService _contextService;
  private TimePeriodActivatorRepository _timePeriodActivatorRepository;

  @Autowired
  public TimePeriodActivatorService(ContextService contextService, TimePeriodActivatorRepository repository) {
    super(repository);
    _contextService = contextService;
    _timePeriodActivatorRepository = repository;
  }

  public Collection<TimePeriodActivator> getByContext(String contextId) {
    return _timePeriodActivatorRepository.getByContext(contextId);
  }

  @Override
  public TimePeriodActivator create(TimePeriodActivator timePeriodActivator) {
    _contextService.getOrThrow(timePeriodActivator.getContextId().toString());

    return super.create(timePeriodActivator);
  }

  @Override
  public TimePeriodActivator update(TimePeriodActivator timePeriodActivator) {
    var existingTimePeriodActivator = getOrThrow(timePeriodActivator.getId());

    existingTimePeriodActivator.setCronStart(timePeriodActivator.getCronStart());
    existingTimePeriodActivator.setCronEnd(timePeriodActivator.getCronEnd());

    return super.update(existingTimePeriodActivator);
  }

}
