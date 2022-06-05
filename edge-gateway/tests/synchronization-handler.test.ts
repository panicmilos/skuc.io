import { SynchronizationHandler } from "../src";

describe("SynchronizationHandler", () => {
  it("should create an instance", () => {
    const _ = new SynchronizationHandler(
      {
        frequency: 5,
        filter: "topic === 'test'",
        type: "value",
        id: '1'
      },
      {
        getValue(topic: string) {
          return { timestamp: new Date(), value: "ASDF" };
        },
        getStatus(topic: string) {
          return { timestamp: new Date(), value: "ASDF" };
        },
        getTopics() {
          return [];
        },
      },
      {
        sendData() {},
        sendStatus() {},
      }
    );
  });

  it.each([
    [['value', "function finalFilter(topic, value, status) { return topic === 'test' }", 5, 10], [2, 0, 2, 0]],
    [['status', "function finalFilter(topic, value, status) { return topic === 'test' }", 5, 10], [0, 2, 0, 2]],
    [['value', "function finalFilter(topic, value, status) { return topic === 'test' || topic === 'testing' }", 5, 20], [8, 0, 8, 0]],
    [['status', "function finalFilter(topic, value, status) { return value === 'ASDF' }", 5, 5], [0, 2, 0, 2]],
    [['value', "function finalFilter(topic, value, status) { return status === 'ASDF' }", 5, 5], [2, 0, 2, 0]],
  ])('should call get %p %p times', (args: any[], result: number[]) => {
    const expectedCheckingCalls = 2 * args[3] / args[2];

    jest.useFakeTimers();

    let getValueCallTimes = 0;
    let getStatusCallTimes = 0;
    let sendValueCallTimes = 0;
    let sendStatusCallTimes = 0;
    const sync = new SynchronizationHandler(
      {
        frequency: args[2],
        filter: args[1],
        type: args[0],
        id: '1'
      },
      {
        getValue(topic: string) {
          getValueCallTimes++;
          return { timestamp: new Date(), value: "ASDF" };
        },
        getStatus(topic: string) {
          getStatusCallTimes++;
          return { timestamp: new Date(), value: "ASDF" };
        },
        getTopics() {
          return ["test", "testing"];
        },
      },
      {
        sendData() { sendValueCallTimes++ },
        sendStatus() { sendStatusCallTimes++ },
      }
    );
    sync.init();

    jest.advanceTimersByTime(args[3] * 1000);

    expect(getValueCallTimes).toBe(result[0] + expectedCheckingCalls);
    expect(getStatusCallTimes).toBe(result[1] + expectedCheckingCalls);
    expect(sendValueCallTimes).toBe(result[2]);
    expect(sendStatusCallTimes).toBe(result[3]);

    sync.dispose();
  });
});
