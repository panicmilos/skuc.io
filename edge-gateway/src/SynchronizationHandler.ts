import { Configuration, ICanProvideValuesAndStatuses, ICanBeInitializedAndDisposed, ICanSendToCloud } from "./common";

export const FILTER_FUNCTION_NAME = 'finalFilter';

export class SynchronizationHandler implements ICanBeInitializedAndDisposed {
  private timer: any;

  constructor(
    private config: Configuration,
    private infoProvider: ICanProvideValuesAndStatuses,
    private cloudSender: ICanSendToCloud
  ) {}

  public init() {
    this.timer = setInterval(() => {
      this.infoProvider.getTopics().forEach((topic) => {
        const value = this.infoProvider.getValue(topic)?.value ?? {};
        const topicCode = `'${topic}'`;
        const valueCode = `${typeof value === 'object' ? JSON.stringify(value) : `'${value}'`}`;
        const statusCode = `'${this.infoProvider.getStatus(topic)?.value}'`;
        const code = `${this.config.filter};${FILTER_FUNCTION_NAME}(${topicCode}, ${valueCode}, ${statusCode})`;
        if(!eval(code)) return;
        if (!this.config.type || this.config.type === "value") {
          this.cloudSender.sendData(this.infoProvider.getValue(topic));
        } else if (this.config.type === "status") {
          this.cloudSender.sendStatus(this.infoProvider.getStatus(topic));
        }
      });
    }, this.config.frequency * 1000);
  }

  public dispose(): void {
    clearInterval(this.timer);
  }
}
