import {
  EdgeGateway,
  ICanBeInitializedAndDisposed,
} from "../../edge-gateway/src";

export interface RandomSeqElement {
  probability: number;
  value: any;
}

export class RandomSimulator implements ICanBeInitializedAndDisposed {
  private intervalId: NodeJS.Timer;
  private sequence: any[] = [];

  constructor(
    private gw: EdgeGateway,
    private topic: string,
    sequence: RandomSeqElement[],
    private interval: number = 500
  ) {
    sequence.forEach((sElem) => {
      this.sequence.push(
        ...Array.from({ length: sElem.probability }).fill(sElem.value)
      );
    });
  }

  init(): void {
    this.intervalId = setInterval(() => {
      const value = this.sequence[Math.floor(Math.random() * this.sequence.length)];
      this.gw.setValue(this.topic, value);
    }, this.interval);
  }

  dispose(): void {
    clearInterval(this.intervalId);
  }
}

export interface SeqElement {
  timeout: number; // seconds
  value: any;
}

export class SeqSimulator implements ICanBeInitializedAndDisposed {
  private disposed: boolean = false;

  constructor(
    private gw: EdgeGateway,
    private topic: string,
    private sequence: SeqElement[]
  ) {}

  async init(): Promise<void> {
    for (const elem of this.sequence) {
      this.gw.setValue(this.topic, elem.value);
      await new Promise(r => setTimeout(r, elem.timeout * 1000));
    }
    !this.disposed && this.init();
  }

  dispose(): void {
    this.disposed = true;
  }
}
