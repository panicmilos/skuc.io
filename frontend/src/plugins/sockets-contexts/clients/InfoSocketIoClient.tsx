import { BACKEND_API, SOCKETS_API } from "../imports";
import { SocketIoClient } from "./SocketIoClient";

export class InfoSocketIoClient extends SocketIoClient {
  private initialized: boolean;
  private statusHandler: (e: any) => void = () => {};

  constructor(groupId: string) {
    super({ host: SOCKETS_API, namespace: `${groupId}/infos`, path: '', serverHost: BACKEND_API });
    this.initialized = false;
  }

  onInitHook() {
    this.clearEventListeners('event');
    this.addEventListener('event', this.statusHandler);
    this.initialized = true;
  }

  onInfo(statusHandler: (e: any) => void) {
    this.statusHandler = statusHandler;
    this.initialized && this.clearEventListeners('event');
    !this.initialized ? this.init() : this.addEventListener('event', this.statusHandler);
  }
}