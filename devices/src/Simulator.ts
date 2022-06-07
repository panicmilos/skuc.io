import {
  EdgeGateway,
  ICanBeInitializedAndDisposed,
} from "../../edge-gateway/src";

export interface RandomSeqElement {
  probability: number;
  value: any;
  type?: "status" | "value";
}

export class RandomSimulator implements ICanBeInitializedAndDisposed {
  private intervalId: any;
  private sequence: any[] = [];

  constructor(
    private gw: EdgeGateway,
    private topic: string,
    sequence: RandomSeqElement[],
    private interval: number = 500
  ) {
    sequence.forEach((sElem) => {
      this.sequence.push(
        ...Array.from({ length: sElem.probability }).fill(sElem)
      );
    });
  }

  init(): void {
    this.intervalId = setInterval(() => {
      const elem = this.sequence[Math.floor(Math.random() * this.sequence.length)];
      if(elem.type === "status") {
        this.gw.setStatus(this.topic, elem.value);
      } else {
        this.gw.setValue(this.topic, elem.value);
      }
    }, this.interval);
  }

  dispose(): void {
    clearInterval(this.intervalId);
  }
}

export interface SeqElement {
  timeout: number; // seconds
  value: any;
  type?: "status" | "value";
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
      if(elem.type === "status") {
        this.gw.setStatus(this.topic, elem.value);
      } else {
        this.gw.setValue(this.topic, elem.value);
      }
      await new Promise(r => setTimeout(r, elem.timeout * 1000));
    }
    !this.disposed && this.init();
  }

  dispose(): void {
    this.disposed = true;
  }
}
