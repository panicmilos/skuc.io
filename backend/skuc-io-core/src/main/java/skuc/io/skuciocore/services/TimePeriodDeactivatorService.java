package skuc.io.skuciocore.services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skuc.io.skuciocore.models.csm.context.deactivation.TimePeriodDeactivator;
import skuc.io.skuciocore.persistence.TimePeriodDeactivatorRepository;

@Service
public class TimePeriodDeactivatorService extends CrudService<TimePeriodDeactivator> {

  private ContextService _contextService;
  private TimePeriodDeactivatorRepository _timePeriodDeactivatorRepository;

  @Autowired
  public TimePeriodDeactivatorService(ContextService contextService, TimePeriodDeactivatorRepository repository) {
    super(repository);
    _contextService = contextService;
    _timePeriodDeactivatorRepository = repository;
  }

  public Collection<TimePeriodDeactivator> getByContext(String contextId) {
    return _timePeriodDeactivatorRepository.getByContext(contextId);
  }

  @Override
  public TimePeriodDeactivator create(TimePeriodDeactivator timePeriodDeactivator) {
    _contextService.getOrThrow(timePeriodDeactivator.getContextId());

    return super.create(timePeriodDeactivator);
  }

  @Override
  public TimePeriodDeactivator update(TimePeriodDeactivator timePeriodDeactivator) {
    var existingTimePeriodDeactivator = getOrThrow(timePeriodDeactivator.getId());

    existingTimePeriodDeactivator.setCronStart(timePeriodDeactivator.getCronStart());
    existingTimePeriodDeactivator.setCronEnd(timePeriodDeactivator.getCronEnd());

    return super.update(existingTimePeriodDeactivator);
  }

}
