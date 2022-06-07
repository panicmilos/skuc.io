package skuc.io.skucioapp;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClassObjectFilter;
import org.drools.core.time.SessionPseudoClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import skuc.io.skuciocore.models.events.device.Aggregate;
import skuc.io.skuciocore.models.events.device.Value0005Aggregated;
import skuc.io.skuciocore.models.events.device.Value0015Aggregated;
import skuc.io.skuciocore.models.events.device.Value0030Aggregated;
import skuc.io.skuciocore.models.events.device.Value0060Aggregated;
import skuc.io.skuciocore.models.events.device.Value0240Aggregated;
import skuc.io.skuciocore.models.events.device.Value0480Aggregated;
import skuc.io.skuciocore.models.events.device.Value1440Aggregated;
import skuc.io.skuciocore.models.events.device.ValueAggregated;
import skuc.io.skuciocore.models.events.device.ValueReceived;
import skuc.io.skuciocore.models.events.kjar.AggregateParam;


@SpringBootTest
class CEPTest {
  

  private final String DEVICE_ID = "953164df-0432-43e7-b735-a204dfcda3ed";
  private final String DEVICE_TYPE = "Temperature Sensor";
  private final String PARAM_NAME = "temp";

  private KieSession _session;

  @Autowired
  private KieContainer _kieContainer;

  @BeforeEach
  public void init() {
    _session = _kieContainer.newKieSession("TestCepSession");
  }

  private void advanceTime(int forTime) {
    SessionPseudoClock clock = _session.getSessionClock();
    clock.advanceTime(forTime, TimeUnit.SECONDS);
  }

  private void insertValueReceived(float value) {
    _session.insert(new ValueReceived(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, value));
  }

  private void insertValue0005Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0005Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0015Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0015Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0030Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0030Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0060Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0060Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0240Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0240Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertValue0480Aggregated(Double min, Double max, Double sum, Double average, Long count) {
    _session.insert((new Value0480Aggregated(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, new Aggregate(min, max, sum, average, count))));
  }

  private void insertAggregateParam(int resolution) {
    _session.insert(new AggregateParam(DEVICE_ID, DEVICE_TYPE, PARAM_NAME, resolution));
  }

  private void fire() {
    _session.fireAllRules();
  }

  private <T> ArrayList<T> getFactsFromKieSession(Class<T> classType) {
    return new ArrayList<>((Collection<T>) _session.getObjects(new ClassObjectFilter(classType)));
  }

  private void assertValueAggregated(ValueAggregated valueAggregated, Double min, Double max, Double sum, Double average, Long count) {
    assertEquals(min, valueAggregated.getAggregate().getMin());
    assertEquals(max, valueAggregated.getAggregate().getMax());
    assertEquals(sum, valueAggregated.getAggregate().getSum());
    assertEquals(average, valueAggregated.getAggregate().getAverage());
    assertEquals(count, valueAggregated.getAggregate().getCount());
  }

  @Test
  public void Test_Aggregate_0005() {

    advanceTime(1);
    insertValueReceived(12);

    advanceTime(1);
    insertValueReceived(22);

    advanceTime(2);
    insertValueReceived(17);

    insertAggregateParam(5);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0005 = getFactsFromKieSession(Value0005Aggregated.class);
    assertEquals(1, aggregate0005.size());

    assertValueAggregated(aggregate0005.get(0), 12D, 22D, 51D, 17D, 3L);
  }

  @Test
  public void Test_Aggregate_0015() {

    insertValue0005Aggregated(10D, 22D, 44D, 11D, 4L);
    advanceTime(5);

    insertValue0005Aggregated(2D, 33D, 100D, 20D, 5L);
    advanceTime(5);

    insertValue0005Aggregated(1D, 1D, 1D, 1D, 1L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0015 = getFactsFromKieSession(Value0015Aggregated.class);
    assertEquals(1, aggregate0015.size());

    assertValueAggregated(aggregate0015.get(0), 1D, 33D, 145D, 14.5D, 10L);
  }

  @Test
  public void Test_Aggregate_0030() {

    insertValue0015Aggregated(3D, 13D, 24D, 7D, 3L);
    advanceTime(15);

    insertValue0015Aggregated(6D, 11D, 25D, 6.25D, 4L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0030 = getFactsFromKieSession(Value0030Aggregated.class);
    assertEquals(1, aggregate0030.size());

    assertValueAggregated(aggregate0030.get(0), 3D, 13D, 49D, 7D, 7L);
  }

  @Test
  public void Test_Aggregate_0060() {

    insertValue0030Aggregated(7D, 56D, 96D, 16D, 6L);
    advanceTime(30);

    insertValue0030Aggregated(11D, 13D, 24D, 12D, 2L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0060 = getFactsFromKieSession(Value0060Aggregated.class);
    assertEquals(1, aggregate0060.size());

    assertValueAggregated(aggregate0060.get(0), 7D, 56D, 120D, 15D, 8L);
  }

  @Test
  public void Test_Aggregate_0240() {

    insertValue0060Aggregated(1D, 14D, 22D, 5.25D, 4L);
    advanceTime(60);

    insertValue0060Aggregated(3D, 22D, 30D, 10D, 3L);
    advanceTime(60);

    insertValue0060Aggregated(2D, 17D, 19D, 9.5D, 2L);
    advanceTime(60);

    insertValue0060Aggregated(7D, 33D, 60D, 20D, 3L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0240 = getFactsFromKieSession(Value0240Aggregated.class);
    assertEquals(1, aggregate0240.size());

    assertValueAggregated(aggregate0240.get(0), 1D, 33D, 131D, 131D/12L, 12L);
  }

  @Test
  public void Test_Aggregate_0480() {

    insertValue0240Aggregated(1D, 6D, 10D, 2.5D, 4L);
    advanceTime(240);

    insertValue0240Aggregated(2D, 18D, 20D, 10D, 2L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate0480 = getFactsFromKieSession(Value0480Aggregated.class);
    assertEquals(1, aggregate0480.size());

    assertValueAggregated(aggregate0480.get(0), 1D, 18D, 30D, 5D, 6L);
  }

  @Test
  public void Test_Aggregate_1440() {

    insertValue0480Aggregated(7D, 12D, 30D, 10D, 3L);
    advanceTime(480);

    insertValue0480Aggregated(12D, 30D, 42D, 21D, 2L);
    advanceTime(480);


    insertValue0480Aggregated(8D, 16D, 38D, 7.6D, 5L);
    fire();

    var aggregateParams = getFactsFromKieSession(AggregateParam.class);
    assertEquals(0, aggregateParams.size());

    var aggregate1440 = getFactsFromKieSession(Value1440Aggregated.class);
    assertEquals(1, aggregate1440.size());

    assertValueAggregated(aggregate1440.get(0), 7D, 30D, 110D, 11D, 10L);
  }
}
