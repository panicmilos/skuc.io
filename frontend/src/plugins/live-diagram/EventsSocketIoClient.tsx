import { BACKEND_API, SOCKETS_API, SocketIoClient } from "./imports";

export class EventsSocketIoClient extends SocketIoClient {
  private initialized: boolean;
  private statusHandler: (e: any) => void = () => {};

  constructor(groupId: string) {
    super({ host: SOCKETS_API, namespace: `${groupId}/events`, path: '', serverHost: BACKEND_API });
    this.initialized = false;
  }

  onInitHook() {
    this.clearEventListeners('event');
    this.addEventListener('event', this.statusHandler);
    this.initialized = true;
  }

  onEvent(statusHandler: (e: any) => void) {
    this.statusHandler = statusHandler;
    this.initialized && this.clearEventListeners('event');
    !this.initialized ? this.init() : this.addEventListener('event', this.statusHandler);
  }
}